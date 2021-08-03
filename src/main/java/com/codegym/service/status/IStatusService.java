package com.codegym.service.status;

import com.codegym.model.status.Status;
import com.codegym.service.IGeneralService;
import org.springframework.stereotype.Service;

@Service
public interface IStatusService extends IGeneralService<Status> {
    Iterable<Status> findAllByAccountId(Long id);
}
