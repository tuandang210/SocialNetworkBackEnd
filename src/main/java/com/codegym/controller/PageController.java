package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.model.image.ImageStatus;
import com.codegym.model.page.MemberPage;
import com.codegym.model.page.Page;
import com.codegym.model.status.Status;
import com.codegym.service.imageStatus.IStatusImageService;
import com.codegym.service.memberpage.IMemberService;
import com.codegym.service.page.IPageService;
import com.codegym.service.status.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/pages")
public class PageController {

    @Autowired
    private IPageService pageService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IStatusService statusService;
    @Autowired
    private IStatusImageService statusImageService;

    @PostMapping("/{id}")
    public ResponseEntity<Page> create(@PathVariable Long id, @RequestBody Page page) {
        pageService.save(page);
        Page page1 = pageService.findPageByName(page.getName());
        Account account = new Account();
        account.setId(id);
        MemberPage memberPage = new MemberPage(account, page1, 0);
        memberService.save(memberPage);
        return new ResponseEntity<>(page, HttpStatus.CREATED);
    }

    @DeleteMapping("/{pageId}")
    public ResponseEntity<Page> delete(@PathVariable Long pageId) {
        List<Status> statuses = (List<Status>) statusService.findAllByPageId(pageId);
        for (Status status : statuses) {
            statusService.delete(status.getId());
            for (ImageStatus imageStatus : status.getImageStatuses()) {
                statusImageService.delete(imageStatus.getId());
            }
        }
        List<MemberPage> memberPages = (List<MemberPage>) memberService.findAllByPageId(pageId);
        for (MemberPage memberPage : memberPages) {
            memberService.delete(memberPage.getId());
        }
        pageService.delete(pageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{pageId}")
    public ResponseEntity<Optional<Page>> getPageByPageId(@PathVariable Long pageId){
        return new ResponseEntity<>(pageService.findById(pageId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Page>> getAllPageByAccountId(@RequestParam Long accountId) {
        Iterable<MemberPage> memberPages = memberService.findAllByAccountId(accountId);
        List<Page> pages = new ArrayList<>();
        for (MemberPage memberPage : memberPages) {
            Page page = pageService.findById(memberPage.getPage().getId()).get();
            pages.add(page);
        }
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }
}
