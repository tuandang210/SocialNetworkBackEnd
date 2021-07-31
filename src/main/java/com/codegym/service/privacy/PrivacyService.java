package com.codegym.service.privacy;

import com.codegym.model.account.Privacy;
import com.codegym.repository.IPrivacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivacyService implements IPrivacyService {
    @Autowired
    private IPrivacyRepository privacyRepository;

    @Override
    public Iterable<Privacy> findAll() {
        return privacyRepository.findAll();
    }

    @Override
    public Optional<Privacy> findById(Long id) {
        return privacyRepository.findById(id);
    }

    @Override
    public Privacy save(Privacy privacy) {
        return privacyRepository.save(privacy);
    }

    @Override
    public void delete(Long id) {
        privacyRepository.deleteById(id);
    }
}
