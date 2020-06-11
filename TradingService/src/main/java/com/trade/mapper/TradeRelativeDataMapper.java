package com.trade.mapper;

import com.trade.entity.TradingData;
import com.trade.pojo.TradeRelativeDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface TradeRelativeDataMapper {
    @Mappings({
            @Mapping(target = "priceChange", ignore = true),
            @Mapping(target = "avgChangeGain", ignore = true),
            @Mapping(target = "avgChangeLoss", ignore = true),
            @Mapping(target = "relativeStrength", ignore = true),
            @Mapping(target = "relativeIndex", ignore = true),
    })
    TradeRelativeDataDto toTradeRelativeDataDto(TradingData data);

    List<TradeRelativeDataDto> toTradeRelativeDataDtoList(List<TradingData> dataList);
}
