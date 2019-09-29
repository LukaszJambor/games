package com.example2.demo.converters;

import com.example2.demo.data.LendData;
import com.example2.demo.model.LendEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LendEntityToLendDataMapper {

    @Mapping(target = "title", source = "game.name")
    @Mapping(target = "lendStartDate", source = "lendStartDate")
    @Mapping(target = "lendEndDate", source = "lendEndDate")
    @Mapping(target = "gameId", source = "game.id")
    LendData toDto(LendEntity lendEntity);
}