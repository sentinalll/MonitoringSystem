package com.playtech.service;

import com.playtech.entity.DocEntity;
import com.playtech.repository.DocEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MongoDBEntityServiceImpl implements EntityService {
    private final DocEntityRepository repository;

    @Autowired
    MongoDBEntityServiceImpl(DocEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DocEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<DocEntity> findPaging(int pageNumber, int PAGE_SIZE) {
        return repository.findAll(new PageRequest(pageNumber, PAGE_SIZE)).getContent();
    }

    @Override
    public List<DocEntity> findByCreationTimeGreaterThanOperationType(Date date, String type, int pageNumber, int PAGE_SIZE) {
        return repository.findByCreationTimeGreaterThanAndOperationType(date, type, new PageRequest(pageNumber, PAGE_SIZE));

    }

    @Override
    public Long countByCreationTimeGreaterThanOperationType(Date date, String type) {
        return repository.countByCreationTimeGreaterThanAndOperationType(date,type);
    }

    @Override
    public void insert(DocEntity obj) {
        repository.insert(obj);
    }

    @Override
    public void insert(List<DocEntity> list) {
        repository.insert(list);
    }


}
