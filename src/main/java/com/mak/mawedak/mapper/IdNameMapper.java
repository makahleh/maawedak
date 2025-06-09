package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.IdNameDTO;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IdNameMapper {

    public static <T> IdNameDTO toDto(T entity, Function<T, Long> idExtractor, Function<T, String> nameExtractor) {
        return new IdNameDTO(idExtractor.apply(entity), nameExtractor.apply(entity));
    }

    public static <T> List<IdNameDTO> toDtoList(List<T> entities, Function<T, Long> idExtractor, Function<T, String> nameExtractor) {
        return entities.stream()
                .map(entity -> toDto(entity, idExtractor, nameExtractor))
                .collect(Collectors.toList());
    }
}

