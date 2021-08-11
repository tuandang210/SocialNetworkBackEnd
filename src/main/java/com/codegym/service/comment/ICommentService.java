package com.codegym.service.comment;

import com.codegym.model.comment.Comment;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ICommentService extends IGeneralService<Comment> {
    Iterable<Comment> findAllByStatusId(Long id);

    Page<Comment> findAllByStatusIdPagination(Long id, Long pageSize);

    Iterable<Comment> findAllByStatus_IdOrderByDateDesc(Long id);
}
