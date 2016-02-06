package com.playtech.controllers;

import com.playtech.dtos.LogDTO;
import com.playtech.entity.LogEntity;
import com.playtech.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0")
public class MonitoringController {

    private final EntityService service;

    @Autowired
    public MonitoringController(EntityService service) {
        this.service = service;
    }

    @RequestMapping(path = "/resource/totalItems/{type}", method = RequestMethod.GET)
    public Long totalItems(@PathVariable("type") String type) {
        LocalDateTime today = LocalDateTime.now();
        Date out = Date.from(today.minusHours(1).atZone(ZoneId.systemDefault()).toInstant());
        return service.countByCreationTimeGreaterThanOperationType(out, type);
    }

    @RequestMapping("/resource/{pageNumber}/{pageSize}/{type}")
    public List<Map<String, String>> data(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize, @PathVariable("type") String type) {
        LocalDateTime today = LocalDateTime.now();
        Date out = Date.from(today.minusHours(1).atZone(ZoneId.systemDefault()).toInstant());
        return service.findByCreationTimeGreaterThanOperationType(out, type, pageNumber, pageSize).stream().map(o -> o.getFields()).collect(Collectors.toList());
    }

    @RequestMapping("/db")
    public List<LogDTO> db() {
        return service.findAll();
    }


}