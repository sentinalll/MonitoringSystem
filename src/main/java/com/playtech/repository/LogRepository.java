package com.playtech.repository;

import com.playtech.entity.LogEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;


public interface LogRepository extends MongoRepository<LogEntity, String> {
    List<LogEntity> findByCreationTimeGreaterThanAndOperationType(Date date, String type, Pageable var1);
    Long countByCreationTimeGreaterThanAndOperationType(Date date, String type);
}
