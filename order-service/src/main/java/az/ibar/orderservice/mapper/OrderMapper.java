package az.ibar.orderservice.mapper;

import az.ibar.orderservice.model.dto.OrderCreateDto;
import az.ibar.orderservice.model.dto.OrderDto;
import az.ibar.orderservice.model.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class OrderMapper {

    public abstract OrderDto toOrderDto(OrderCreateDto dto);

    @Mapping(target = "orderState", constant = "PENDING")
    public abstract OrderEntity toOrderEntity(OrderCreateDto dto);
}