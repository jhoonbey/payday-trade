package az.ibar.accountservice.mapper;

import az.ibar.accountservice.model.dto.AccountResponseDto;
import az.ibar.accountservice.model.dto.AccountStockDto;
import az.ibar.accountservice.model.entity.AccountEntity;
import az.ibar.accountservice.model.entity.AccountStockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AccountMapper {

    public abstract AccountResponseDto toAccountResponseDto(AccountEntity account);

    public abstract AccountStockEntity toAccountStockEntity(AccountStockDto dto);

    @Mapping(target = "accountNumber", source = "account.accountNumber")
    public abstract AccountStockDto toAccountStockDto(AccountStockEntity accountStockEntity);

    public abstract List<AccountStockDto> toAccountStockDtoList(List<AccountStockEntity> accountStockEntities);
}