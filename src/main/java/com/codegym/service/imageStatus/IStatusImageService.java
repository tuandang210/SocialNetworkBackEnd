package com.codegym.service.imageStatus;

import com.codegym.model.image.ImageStatus;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface IStatusImageService extends IGeneralService<ImageStatus> {
    Optional<ImageStatus> findByUrl(String url);

}
