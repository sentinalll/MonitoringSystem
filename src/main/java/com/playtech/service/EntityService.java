package com.playtech.service;

import com.playtech.entity.DocEntity;

import java.util.Date;
import java.util.List;

public interface EntityService {
    List<DocEntity> findAll();

    List<DocEntity> findPaging(int pageNumber, int PAGE_SIZE);

    List<DocEntity> findByCreationTimeGreaterThanOperationType(Date date, String type, int pageNumber, int PAGE_SIZE);

    Long countByCreationTimeGreaterThanOperationType(Date date, String type);

    void insert(DocEntity obj);

    void insert(List<DocEntity> list);

}
