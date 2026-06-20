package com.example.spendora.mapper;

import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.model.*;
import com.example.spendora.service.ai.AiParseResult;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    // 1. Tell MapStruct how to turn a Category object into a String
    default String map(SystemCategory category) {
        return (category != null) ? category.getName() : null;
    }

    // 2. Tell MapStruct how to turn a PaymentMode object into a String
    default String map(PaymentMode paymentMode) {
        return (paymentMode != null) ? paymentMode.getName() : null;
    }
    @Mapping(target = "transactionId", source = "id")
    TransactionDto transactionDtoToTransactionDto(Transaction transaction);

    @Named("convertStringToDate")
    default LocalDate convertStringToDate(String transactionDate) {
        return LocalDate.parse(transactionDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    List<TransactionDto> transactionDtosToTransactionDtos(List<Transaction> transaction);
    TransactionRequestDto fromAiParseResult(AiParseResult aiParseResult);
    @Mapping(target = "transactionDate", source = "result.date")
    @Mapping(target = "paymentModeId", source = "paymentModeId")
    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "categoryId", source = "categoryId")

    TransactionRequestDto fromAiParseTask(AiParseResult result, Long paymentModeId, Long accountId, Long categoryId);

    @Mapping(target = "appUser", source = "appUserId", qualifiedByName = "idToAppUser")
    @Mapping(target = "paymentMode", source = "dto.paymentModeId", qualifiedByName = "idToPaymentMode")
    //@Mapping(target = "account", source = "dto.accountId", qualifiedByName = "idToAccount")
    @Mapping(target = "category", source = "dto.categoryId", qualifiedByName = "idToCategory")
    //@Mapping(target = "amount", source = "dto", qualifiedByName = "mapAmount")

    @Mapping( target ="account", ignore = true)
    @Mapping( target ="amount", ignore = true)
    @Mapping(target = "transferId", source = "transferId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "transactionDate", source = "dto.transactionDate", qualifiedByName = "convertStringToDate")

    void transactionFromRequestDto(TransactionRequestDto dto, @MappingTarget Transaction entity, String appUserId, String transferId, boolean isSourceAccount);

    @AfterMapping
    default void mapCategory(TransactionRequestDto dto, @MappingTarget Transaction entity) {
        if (dto.categoryId() != null) {
            if (dto.categoryId() < 0) {
                entity.setUserCategory(UserCategory.ofId(Math.abs(dto.categoryId())));
                entity.setSystemCategory(null);
            } else {
                entity.setSystemCategory(SystemCategory.ofId(dto.categoryId()));
                entity.setUserCategory(null);
            }
        } else {
            entity.setSystemCategory(null);
            entity.setUserCategory(null);
        }
    }
    @AfterMapping
    default void mapAmountAndAccount(TransactionRequestDto dto, @MappingTarget Transaction entity, String appUserId, boolean isSourceAccount){
        final var type = TransactionType.valueOf(dto.type());
        if (type == TransactionType.TRANSFER) {
            if (isSourceAccount) {
                entity.setAccount(Account.ofId(dto.accountId()));
                entity.setAmount(-dto.amount());
            } else {
                entity.setAccount(Account.ofId(dto.toAccountId()));
                entity.setAmount(dto.amount());
            }

            return;
        }
        entity.setAccount(Account.ofId(dto.accountId()));
        entity.setAmount(type == TransactionType.EXPENSE ? -dto.amount() : dto.amount());
    }

    @Named("idToAppUser")
    default AppUser idToAppUser(String id){
        return id != null ? AppUser.ofId(id) : null;
    }
    @Named("idToPaymentMode")
    default PaymentMode idToPaymentMode(Long id){
        return id != null ? PaymentMode.ofId(id) : null;
    }
    @Named("idToAccount")
    default Account idToAccount(Long id){
        return id != null ? Account.ofId(id) : null;
    }
    @Named("idToSystemCategory")
    default SystemCategory idToSystemCategory(Long id) {
        return id != null ? SystemCategory.ofId(id) : null;
    }
    @Named("idToUserCategory")
    default UserCategory idToUserCategory(Long id) {
        return id != null ? UserCategory.ofId(id) : null;
    }

}
