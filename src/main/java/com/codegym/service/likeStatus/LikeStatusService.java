package com.codegym.service.likeStatus;

import com.codegym.model.like.LikeStatus;
import com.codegym.repository.ILikeStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LikeStatusService implements ILikeStatusService{
    @Autowired
    private ILikeStatusRepository likeStatusRepository;
    @Override
    public Iterable<LikeStatus> findAll() {
        return likeStatusRepository.findAll();
    }

    @Override
    public Optional<LikeStatus> findById(Long id) {
        return likeStatusRepository.findById(id);
    }

    @Override
    public LikeStatus save(LikeStatus likeStatus) {
        return likeStatusRepository.save(likeStatus);
    }

    @Override
    public void delete(Long id) {
        likeStatusRepository.deleteById(id);
    }

    @Override
    public Iterable<LikeStatus> findAllByStatusId(Long id) {
        return likeStatusRepository.findAllByStatusId(id);
    }

    @Override
    public Iterable<LikeStatus> findAllByStatusIdAndIsLikeTrue(Long id) {
        return likeStatusRepository.findAllByStatusIdAndIsLikeTrue(id);
    }

    @Override
    public Optional<LikeStatus> findByAccountIdAndStatusId(Long accountId, Long statusId) {
        return likeStatusRepository.findByAccountIdAndStatusId(accountId, statusId);
    }
}
