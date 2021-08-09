package com.codegym.service.likeStatus;

import com.codegym.model.like.LikeStatus;
import com.codegym.service.IGeneralService;

public interface ILikeStatusService extends IGeneralService<LikeStatus> {
    Iterable<LikeStatus> findAllByStatusId(Long id);
    Iterable<LikeStatus> findAllByStatusIdAndIsLikeTrue(Long id);
}
