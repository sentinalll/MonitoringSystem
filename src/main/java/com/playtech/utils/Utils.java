package com.playtech.utils;

import com.playtech.dtos.LogDTO;
import com.playtech.entity.LogEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;


public class Utils {
    public static DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSSZ");
    public static final String OPERATION_TYPE = "operationType";
    public static final String CREATION_TIME = "creationTime";


    public static List<LogDTO> toDtos(List<LogEntity> list) {
        return list.stream().map(Utils::toDto).collect(Collectors.toList());
    }

    public static List<LogEntity> toEntities(List<LogDTO> list) {
        return list.stream().map(Utils::toEntity).collect(Collectors.toList());
    }

    public static LogDTO toDto(LogEntity entity) {
        LogDTO dto = new LogDTO();
        dto.getFields().putAll(entity.getProperties());
        dto.getFields().put(OPERATION_TYPE, entity.getOperationType());
        dto.getFields().put(CREATION_TIME, format.format(entity.getCreationTime()));
        return dto;
    }

    public static LogEntity toEntity(LogDTO dto) {
        LogEntity entity = new LogEntity();
        entity.setOperationType(dto.getFields().get(OPERATION_TYPE));
        try {
            entity.setCreationTime(format.parse(dto.getFields().get(CREATION_TIME)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dto.getFields().remove(OPERATION_TYPE);
        dto.getFields().remove(CREATION_TIME);
        entity.setProperties(dto.getFields());
        return entity;
    }

}
