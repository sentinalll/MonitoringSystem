package com.playtech.controllers;

import com.playtech.dtos.LogDTO;
import com.playtech.entity.LogEntity;
import com.playtech.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.playtech.utils.Utils.*;

@RestController
public class GenerateValueController {

    private static final Logger logger = LoggerFactory.getLogger(GenerateValueController.class);

    private final EntityService service;

    @Autowired
    public GenerateValueController(EntityService service) {
        this.service = service;
    }

    private List<LogDTO> result = new ArrayList<>();

    @RequestMapping(path = "/generate", method = RequestMethod.POST)
    public void generate() {
        result = new ArrayList<>();
        long count = getRandom();
        for (int i = 0; i <= count; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 56; j++) {
                model.put("key" + j, i + "val" + j + Math.random());
            }
            LogDTO d = new LogDTO();
            d.getFields().put(OPERATION_TYPE, "deposit");
            d.getFields().put(CREATION_TIME, format.format(new Date()));
            d.getFields().putAll(model);
            result.add(d);
        }
        logger.debug("Insert DEPOSIT count:"+count);
        count = getRandom();
        for (int i = 0; i <= count; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 56; j++) {
                model.put("key" + j, i + "val" + j + Math.random());
            }
            LogDTO d = new LogDTO();
            d.getFields().put(OPERATION_TYPE, "transfer");
            d.getFields().put(CREATION_TIME, format.format(new Date()));
            d.getFields().putAll(model);
            result.add(d);
        }
        logger.debug("Insert TRANSFER count:"+count);
        count = getRandom();
        for (int i = 0; i <= count; i++) {
            Map<String, String> model = new HashMap<>();
            for (int j = 0; j < 56; j++) {
                model.put("key" + j, i + "val" + j + Math.random());
            }

            LogDTO d = new LogDTO();
            d.getFields().put(OPERATION_TYPE, "withdrawal");
            d.getFields().put(CREATION_TIME, format.format(new Date()));
            d.getFields().putAll(model);
            result.add(d);
        }
        logger.debug("Insert WITHDRAWAL count:"+count);
        service.insert(result);
    }

    private long getRandom() {
        return Math.round(Math.random() * 100);
    }


}
