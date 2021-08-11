package com.codegym.service.page;

import com.codegym.model.account.Account;
import com.codegym.model.group.Group;
import com.codegym.model.page.Page;
import com.codegym.repository.IPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageService implements IPageService {
    @Autowired
    private IPageRepository pageRepository;


    @Override
    public Iterable<Page> findAll() {
        return pageRepository.findAll();
    }

    @Override
    public Optional<Page> findById(Long id) {
        return pageRepository.findById(id);
    }

    @Override
    public Page save(Page page) {
        return pageRepository.save(page);
    }

    @Override
    public void delete(Long id) {
        pageRepository.deleteById(id);
    }

    @Override
    public Page findPageByName(String name) {
        return pageRepository.findPageByName(name).get();
    }
}
