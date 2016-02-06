package com.playtech.service;

import com.playtech.dtos.LogDTO;
import com.playtech.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.playtech.utils.Utils.*;

@Service
public class MongoDBEntityServiceImpl implements EntityService {
    private final LogRepository repository;

    @Autowired
    MongoDBEntityServiceImpl(LogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LogDTO> findAll() {
        return toDtos(repository.findAll());
    }

    @Override
    public List<LogDTO> findPaging(int pageNumber, int PAGE_SIZE) {
        return toDtos(repository.findAll(new PageRequest(pageNumber, PAGE_SIZE)).getContent());
    }

    @Override
    public List<LogDTO> findByCreationTimeGreaterThanOperationType(Date date, String type, int pageNumber, int PAGE_SIZE) {
        return toDtos(repository.findByCreationTimeGreaterThanAndOperationType(date, type, new PageRequest(pageNumber, PAGE_SIZE)));

    }

    @Override
    public Long countByCreationTimeGreaterThanOperationType(Date date, String type) {
        return repository.countByCreationTimeGreaterThanAndOperationType(date, type);
    }

    @Override
    public void insert(LogDTO obj) {
        repository.insert(toEntity(obj));
    }

    @Override
    public void insert(List<LogDTO> list) {
        repository.insert(toEntities(list));
    }


}
