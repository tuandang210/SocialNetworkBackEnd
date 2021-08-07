package com.codegym.service.imageStatus;

import com.codegym.model.image.ImageStatus;
import com.codegym.service.IGeneralService;

public interface IStatusImageService extends IGeneralService<ImageStatus> {
    Iterable<ImageStatus> findAllByStatus_Id(Long id);
}
