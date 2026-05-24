package com.example.spendora.mapper;

import com.example.spendora.dto.TransactionDto;
import com.example.spendora.dto.TransactionRequestDto;
import com.example.spendora.model.*;
import com.example.spendora.service.ai.AiParseResult;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    // 1. Tell MapStruct how to turn a Category object into a String
    default String map(Category category) {
        return (category != null) ? category.getName() : null;
    }

    // 2. Tell MapStruct how to turn a PaymentMode object into a String
    default String map(PaymentMode paymentMode) {
        return (paymentMode != null) ? paymentMode.getName() : null;
    }
    TransactionDto transactionDtoToTransactionDto(Transaction transaction);
    List<TransactionDto> transactionDtosToTransactionDtos(List<Transaction> transaction);
    TransactionRequestDto fromAiParseResult(AiParseResult aiP);

    @Mapping(target = "appUser", source = "appUserId", qualifiedByName = "idToAppUser")
    @Mapping(target = "paymentMode", source = "dto.paymentModeId", qualifiedByName = "idToPaymentMode")
    //@Mapping(target = "account", source = "dto.accountId", qualifiedByName = "idToAccount")
    @Mapping(target = "category", source = "dto.categoryId", qualifiedByName = "idToCategory")
    //@Mapping(target = "amount", source = "dto", qualifiedByName = "mapAmount")

    @Mapping( target ="account", ignore = true)
    @Mapping( target ="amount", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void transactionFromRequestDto(TransactionRequestDto dto, @MappingTarget Transaction entity, String appUserId, String transderId, boolean isSourceAccount);

    @AfterMapping
    default void mapAmountAndAccount(TransactionRequestDto dto, @MappingTarget Transaction entity, String appUserId, boolean isSourceAccount){
        final var type = TransactionType.valueOf(dto.type());
        if(type == TransactionType.TRANSFER){
            if(isSourceAccount){
                entity.setAccount(Account.ofId(dto.toAccountId()));
                entity.setAmount( -dto.amount());
            }
        }
        entity.setAccount(Account.ofId(dto.accountId()));
        entity.setAmount(type == TransactionType.EXPENSE ? -dto.amount() : dto.amount());
        return;
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
    @Named("idToCategory")
    default Category idToCategory(Long id){
        return id != null ? Category.ofId(id) : null;
    }
}
