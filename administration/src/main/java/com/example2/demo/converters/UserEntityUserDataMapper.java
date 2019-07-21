package com.example2.demo.converters;

import com.example2.demo.data.UserData;
import com.example2.demo.model.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserEntityUserDataMapper {

    UserEntity toEntity(UserData userData);
}
