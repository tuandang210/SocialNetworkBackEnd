package com.codegym.service.likeComment;

import com.codegym.model.like.LikeComment;
import com.codegym.repository.ILikeCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeCommentService implements ILikeCommentService{
    @Autowired
    private ILikeCommentRepository likeCommentRepository;
    @Override
    public Iterable<LikeComment> findAll() {
        return likeCommentRepository.findAll();
    }

    @Override
    public Optional<LikeComment> findById(Long id) {
        return likeCommentRepository.findById(id);
    }

    @Override
    public LikeComment save(LikeComment likeComment) {
        return likeCommentRepository.save(likeComment);
    }

    @Override
    public void delete(Long id) {
        likeCommentRepository.deleteById(id);
    }


    @Override
    public Iterable<LikeComment> findAllByCommentId(Long id) {
        return likeCommentRepository.findAllByCommentId(id);
    }
}
