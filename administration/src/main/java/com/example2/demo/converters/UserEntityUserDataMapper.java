package com.example2.demo.converters;

import com.example2.demo.data.UserData;
import model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityUserDataMapper {

    UserEntity toEntity(UserData userData);
}
