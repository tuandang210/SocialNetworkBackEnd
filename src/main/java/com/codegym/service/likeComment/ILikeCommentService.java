package com.codegym.service.likeComment;

import com.codegym.model.like.LikeComment;
import com.codegym.service.IGeneralService;

public interface ILikeCommentService extends IGeneralService<LikeComment> {
    Iterable<LikeComment> findAllByCommentId(Long id);
}
