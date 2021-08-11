package com.codegym.service.page;

import com.codegym.model.account.Account;
import com.codegym.model.group.Group;
import com.codegym.model.page.Page;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface IPageService extends IGeneralService<Page> {
    Page findPageByName(String name);


}
