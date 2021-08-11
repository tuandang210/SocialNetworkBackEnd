package com.codegym.service.memberpage;

import com.codegym.model.account.Account;
import com.codegym.model.page.MemberPage;
import com.codegym.model.page.Page;
import com.codegym.repository.IMemberPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService implements IMemberService {
    @Autowired
    private IMemberPageRepository memberPageRepository;

    @Override
    public Iterable<MemberPage> findAll() {
        return memberPageRepository.findAll();
    }

    @Override
    public Optional<MemberPage> findById(Long id) {
        return memberPageRepository.findById(id);
    }

    @Override
    public MemberPage save(MemberPage memberPage) {
        return memberPageRepository.save(memberPage);
    }

    @Override
    public void delete(Long id) {
        memberPageRepository.deleteById(id);
    }

    @Override
    public Optional<MemberPage> findMemberPageByAccount_Id(Long id) {
        return memberPageRepository.findMemberPageByAccount_Id(id);
    }

    @Override
    public Iterable<MemberPage> findAllByPageId(Long pageId) {
        return memberPageRepository.findAllByPageId(pageId);
    }

    @Override
    public Iterable<MemberPage> findAllByAccountId(Long accountId) {
        return memberPageRepository.findAllByAccountId(accountId);
    }

}
