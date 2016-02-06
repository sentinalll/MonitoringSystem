package com.playtech.service;

import com.playtech.dtos.LogDTO;
import com.playtech.entity.LogEntity;

import java.util.Date;
import java.util.List;

public interface EntityService {
    List<LogDTO> findAll();

    List<LogDTO> findPaging(int pageNumber, int PAGE_SIZE);

    List<LogDTO> findByCreationTimeGreaterThanOperationType(Date date, String type, int pageNumber, int PAGE_SIZE);

    Long countByCreationTimeGreaterThanOperationType(Date date, String type);

    void insert(LogDTO obj);

    void insert(List<LogDTO> list);

}
