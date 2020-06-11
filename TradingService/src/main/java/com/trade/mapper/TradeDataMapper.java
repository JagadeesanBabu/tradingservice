package com.trade.mapper;

import com.trade.entity.TradingData;
import com.trade.pojo.TradeDataBaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface TradeDataMapper {

    TradeDataBaseDto toTradeDataDto(TradingData data);

    List<TradeDataBaseDto> toTradeDataDtoList(List<TradingData> dataList);

}
