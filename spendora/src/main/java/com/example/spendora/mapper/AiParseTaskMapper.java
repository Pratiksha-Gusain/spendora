package com.example.spendora.mapper;

import com.example.spendora.dto.AiTaskDto;
import com.example.spendora.model.AiParsingTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AiParseTaskMapper {
    AiParseTaskMapper INSTANCE = Mappers.getMapper(AiParseTaskMapper.class);
    @Mapping(source = "message", target = "message")
    AiTaskDto toDto(AiParsingTask aiParsingTask, String message);

}
