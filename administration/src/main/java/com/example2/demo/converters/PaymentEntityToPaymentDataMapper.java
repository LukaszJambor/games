package com.example2.demo.converters;

import com.example2.demo.data.PaymentData;
import model.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//import model.PaymentEntity;

@Mapper(componentModel = "spring")
public interface PaymentEntityToPaymentDataMapper {

    @Mapping(source = "game.name", target = "gameName")
    PaymentData toDto(PaymentEntity paymentEntity);
}
