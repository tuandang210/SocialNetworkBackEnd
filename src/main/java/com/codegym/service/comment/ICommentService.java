package com.codegym.service.comment;

import com.codegym.model.comment.Comment;
import com.codegym.service.IGeneralService;

public interface ICommentService extends IGeneralService<Comment> {
    Iterable<Comment> findAllByStatusId(Long id);
}
