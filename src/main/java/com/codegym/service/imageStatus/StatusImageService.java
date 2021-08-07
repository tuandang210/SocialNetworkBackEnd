package com.codegym.service.imageStatus;

import com.codegym.model.image.ImageStatus;
import com.codegym.repository.IStatusImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusImageService implements IStatusImageService {
    @Autowired
    private IStatusImageRepository statusImageRepository;

    @Override
    public Iterable<ImageStatus> findAll() {
        return statusImageRepository.findAll();
    }

    @Override
    public Optional<ImageStatus> findById(Long id) {
        return statusImageRepository.findById(id);
    }

    @Override
    public ImageStatus save(ImageStatus imageStatus) {
        return statusImageRepository.save(imageStatus);
    }

    @Override
    public void delete(Long id) {
        statusImageRepository.deleteById(id);
    }

    @Override
    public Optional<ImageStatus> findByUrl(String url) {
        return statusImageRepository.findByUrl(url);
    }
}
