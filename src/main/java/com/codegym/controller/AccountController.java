package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.model.account.Role;
import com.codegym.model.dto.ChangePasswordAccount;
import com.codegym.model.dto.ResponseMessage;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.AccountRelation;
import com.codegym.model.friend.FriendStatus;
import com.codegym.service.account.IAccountService;
import com.codegym.service.accountRelation.IAccountRelationService;
import com.codegym.service.friendStatus.IFriendStatusService;
import com.codegym.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IFriendStatusService friendStatusService;

    @Autowired
    private IAccountRelationService accountRelationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Account>> getAll() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Iterable<Account>> getAllUser() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Account>> findById(@PathVariable Long id) {
        Optional<Account> accountOptional = accountService.findById(id);
        if (!accountOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountOptional, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNew(@Valid @RequestBody Account account) {
        if (accountService.existsAccountByUsername(account.getUsername())) {
            return new ResponseEntity<>("This username already exists!", HttpStatus.BAD_REQUEST);
        }
        if (accountService.existsAccountByEmail(account.getEmail())) {
            return new ResponseEntity<>("This email already exists!", HttpStatus.BAD_REQUEST);
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(2L, "ROLE_USER"));
        account.setRoles(roleSet);

        Account saved = accountService.save(account);
        createDefaultRelation(saved);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account account1 = accountService.findById(account.getId()).get();
        account1.setFullName(account.getFullName());
        account1.setAddress(account.getAddress());
        account1.setPhone(account.getPhone());
        account1.setFavorite(account.getFavorite());
        account1.setAvatar(account.getAvatar());
        return new ResponseEntity<>(accountService.saveVer(account1), HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> blockAccount(@PathVariable Long id) {
        Account account = accountService.findById(id).get();
        account.setActive(false);
        return new ResponseEntity<>(accountService.saveVer(account), HttpStatus.OK);
    }

    @GetMapping("/page/{offset}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Account>> pagination(@PathVariable int offset) {
        return new ResponseEntity<>(accountService.PaginationAccount(offset), HttpStatus.OK);
    }

    private void createDefaultRelation(Account account) {
        Iterable<Account> accounts = accountService.findAll();

        Role roleUser = new Role(2L, "ROLE_USER");
        FriendStatus guestRelation = friendStatusService.findByStatus(EFriendStatus.GUEST).get();

        for (Account existedAccount : accounts) {
            if (!existedAccount.equals(account) && existedAccount.getRoles().contains(roleUser)) {
                accountRelationService.save(new AccountRelation(existedAccount, account, guestRelation));
            }
        }
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordAccount account, @PathVariable Long id) {
        Account account1 = accountService.findById(id).get();
        if (account1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!passwordEncoder.matches(account.getOldPassword(), account1.getPassword())){
            return new ResponseEntity<>(new ResponseMessage("Wrong password"), HttpStatus.BAD_REQUEST);
        }
        if(account.getNewPassword().equals(account.getOldPassword())){
            return new ResponseEntity<>(new ResponseMessage("Password was not changed."), HttpStatus.BAD_REQUEST);
        }
        if (!account.getConfirmPassword().equals(account.getNewPassword())) {
            return new ResponseEntity<>(new ResponseMessage("Password not match."), HttpStatus.BAD_REQUEST);
        }
        account1.setPassword(account.getNewPassword());
        return new ResponseEntity<>(accountService.save(account1), HttpStatus.OK);
    }
}
