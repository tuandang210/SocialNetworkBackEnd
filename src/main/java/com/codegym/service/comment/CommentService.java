package com.codegym.service.comment;

import com.codegym.model.comment.Comment;
import com.codegym.model.status.Status;
import com.codegym.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Iterable<Comment> findAllByStatusId(Long id) {
        Iterable<Comment> comments =  commentRepository.findAllByStatusId(id);
        getHoursPage1(comments);
        return comments;
    }

    @Override
    public Page<Comment> findAllByStatusIdPagination(Long id, Long pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize.intValue());
        Page<Comment> page = commentRepository.findAllByStatus_IdOrderByDateDesc(id, pageable);
        getHoursPage(page);
        return page;
    }

    @Override
    public Iterable<Comment> findAllByStatus_IdOrderByDateDesc(Long id) {
        Iterable<Comment> comments =  commentRepository.findAllByStatus_IdOrderByDateDesc(id);
        getHoursPage1(comments);
        return comments;
    }


    public Page<Comment> getHoursPage(Page<Comment> comments) {
        for (Comment x : comments.getContent()) {
            Long date = (new Date().getTime() - x.getDate().getTime()) / 1000 / 60;
            x.setTime(date + "");
        }
        return comments;
    }

    public Iterable<Comment> getHoursPage1(Iterable<Comment> comments) {
        for (Comment x : comments) {
            Long date = (new Date().getTime() - x.getDate().getTime()) / 1000 / 60;
            x.setTime(date + "");
        }
        return comments;
    }
}
