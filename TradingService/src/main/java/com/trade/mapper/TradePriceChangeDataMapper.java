package com.trade.mapper;

import com.trade.pojo.PriceChangeDataDto;
import com.trade.pojo.TradeDataBaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface TradePriceChangeDataMapper {
    @Mappings({
            @Mapping(target = "priceChangeValue", ignore = true)
    })
    PriceChangeDataDto toTradePriceChangeDataDto(TradeDataBaseDto data);

}
