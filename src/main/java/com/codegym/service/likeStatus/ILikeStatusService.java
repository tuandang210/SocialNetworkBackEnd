package com.codegym.service.likeStatus;

import com.codegym.model.like.LikeStatus;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface ILikeStatusService extends IGeneralService<LikeStatus> {
    Iterable<LikeStatus> findAllByStatusId(Long id);
    Iterable<LikeStatus> findAllByStatusIdAndIsLikeTrue(Long id);
    Optional<LikeStatus> findByAccountIdAndStatusId(Long accountId, Long statusId);

}
