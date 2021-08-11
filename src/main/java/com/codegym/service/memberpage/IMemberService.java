package com.codegym.service.memberpage;

import com.codegym.model.account.Account;
import com.codegym.model.group.Group;
import com.codegym.model.page.MemberPage;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface IMemberService extends IGeneralService<MemberPage> {
    Optional<MemberPage> findMemberPageByAccount_Id(Long id);
    Iterable<MemberPage> findAllByPageId(Long pageId);
    Iterable<MemberPage> findAllByAccountId(Long accountId);
}
