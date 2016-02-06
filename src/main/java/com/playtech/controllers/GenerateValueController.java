package com.playtech.controllers;

import com.playtech.dtos.LogDTO;
import com.playtech.entity.LogEntity;
import com.playtech.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import static com.playtech.utils.Utils.*;
@RestController
public class GenerateValueController {

    private final EntityService service;

    @Autowired
    public GenerateValueController(EntityService service) {
        this.service = service;
    }

    private List<LogDTO> result = new ArrayList<>();

    @RequestMapping(path = "/generate", method = RequestMethod.POST)
    public void generate() {
        result = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 6; j++) {
                model.put("key" + j, i + "val" + j + UUID.randomUUID());
            }
            LogDTO d = new LogDTO();
            d.getFields().put(OPERATION_TYPE,"deposit");
            d.getFields().put(CREATION_TIME,format.format(new Date()));
            d.getFields().putAll(model);
            result.add(d);
        }
        for (int i = 0; i <= 30; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 6; j++) {
                model.put("key" + j, i + "val" + j + UUID.randomUUID());
            }
            LogDTO d = new LogDTO();
            d.getFields().put(OPERATION_TYPE, "transfer");
            d.getFields().put(CREATION_TIME, format.format(new Date()));
            d.getFields().putAll(model);
            result.add(d);
        }
        for (int i = 0; i <= 30; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 6; j++) {
                model.put("key" + j, i + "val" + j + UUID.randomUUID());
            }

            LogDTO d = new LogDTO();
            d.getFields().put(OPERATION_TYPE, "withdrawal");
            d.getFields().put(CREATION_TIME, format.format(new Date()));
            d.getFields().putAll(model);
            result.add(d);
        }
        service.insert(result);
    }


}
