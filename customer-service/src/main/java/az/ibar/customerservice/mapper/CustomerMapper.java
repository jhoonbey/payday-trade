package az.ibar.customerservice.mapper;

import az.ibar.customerservice.model.dto.CustomerDto;
import az.ibar.customerservice.model.dto.CustomerRegisterDto;
import az.ibar.customerservice.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CustomerMapper {

    public abstract CustomerEntity toCustomerEntity(CustomerRegisterDto dto);

    public abstract CustomerDto toCustomerDto(CustomerEntity entity);
}