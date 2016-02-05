package com.playtech.controllers;

import com.playtech.entity.DocEntity;
import com.playtech.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MonitoringController {

    private final EntityService service;

    @Autowired
    public MonitoringController(EntityService service) {
        this.service = service;
    }

    @RequestMapping(path = "/api/resource/totalItems/{type}", method = RequestMethod.GET)
    public Long totalItems(@PathVariable("type") String type) {
        LocalDateTime today = LocalDateTime.now();
        Date out = Date.from(today.minusHours(1).atZone(ZoneId.systemDefault()).toInstant());
        return service.countByCreationTimeGreaterThanOperationType(out, type);
    }

    private List<DocEntity> result = new ArrayList<>();

    @RequestMapping(path = "/generate", method = RequestMethod.POST)
    public void generate() {
        result = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 6; j++) {
                model.put("key" + j, i + "val" + j + UUID.randomUUID());
            }
            DocEntity d = new DocEntity();
            d.setOperationType("deposit");
            d.setCreationTime(new Date());
            d.setProperties(model);
            result.add(d);
        }
        for (int i = 0; i <= 30; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 6; j++) {
                model.put("key" + j, i + "val" + j + UUID.randomUUID());
            }
            DocEntity d = new DocEntity();
            d.setOperationType("transfer");
            d.setCreationTime(new Date());
            d.setProperties(model);
            result.add(d);
        }
        for (int i = 0; i <= 30; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 6; j++) {
                model.put("key" + j, i + "val" + j + UUID.randomUUID());
            }

            DocEntity d = new DocEntity();
            d.setProperties(model);
            d.setOperationType("withdrawal");
            d.setCreationTime(new Date());
            result.add(d);
        }
        service.insert(result);
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }


    @RequestMapping("/api/resource/{pageNumber}/{pageSize}/{type}")
    public List<Map<String, String>> data(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize, @PathVariable("type") String type) {
        LocalDateTime today = LocalDateTime.now();
        Date out = Date.from(today.minusHours(1).atZone(ZoneId.systemDefault()).toInstant());
        return service.findByCreationTimeGreaterThanOperationType(out, type, pageNumber, pageSize).stream().map(o -> {
            Map<String, String> prop = o.getProperties();
            Map<String, String> result = new HashMap<>();
            result.putAll(prop);
            result.put("creationTime", o.getCreationTime().toString());
            result.put("operationType", o.getOperationType());
            return result;
        }).collect(Collectors.toList());
    }

    @RequestMapping("/api/db")
    public List<DocEntity> db() {
        return service.findAll();
    }


}