package com.example2.demo.converters;

import com.example2.demo.data.PaymentData;
import com.example2.demo.model.PaymentEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//import com.example2.demo.model.PaymentEntity;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PaymentEntityToPaymentDataMapper {

    @Mapping(source = "game.name", target = "gameName")
    PaymentData toDto(PaymentEntity paymentEntity);
}
