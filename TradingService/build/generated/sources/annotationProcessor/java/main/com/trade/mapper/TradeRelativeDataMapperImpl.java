package com.trade.mapper;

import com.trade.entity.TradingData;
import com.trade.pojo.TradeRelativeDataDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-11T15:03:35+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class TradeRelativeDataMapperImpl implements TradeRelativeDataMapper {

    @Override
    public TradeRelativeDataDto toTradeRelativeDataDto(TradingData data) {
        if ( data == null ) {
            return null;
        }

        TradeRelativeDataDto tradeRelativeDataDto = new TradeRelativeDataDto();

        tradeRelativeDataDto.setTradeDate( data.getTradeDate() );
        tradeRelativeDataDto.setOpenPrice( data.getOpenPrice() );
        tradeRelativeDataDto.setClosedPrice( data.getClosedPrice() );
        tradeRelativeDataDto.setHighPrice( data.getHighPrice() );
        tradeRelativeDataDto.setLowPrice( data.getLowPrice() );
        tradeRelativeDataDto.setNoOfSharesTraded( data.getNoOfSharesTraded() );
        tradeRelativeDataDto.setTurnover( data.getTurnover() );
        tradeRelativeDataDto.setIndex( data.getIndex() );

        return tradeRelativeDataDto;
    }

    @Override
    public List<TradeRelativeDataDto> toTradeRelativeDataDtoList(List<TradingData> dataList) {
        if ( dataList == null ) {
            return null;
        }

        List<TradeRelativeDataDto> list = new ArrayList<TradeRelativeDataDto>( dataList.size() );
        for ( TradingData tradingData : dataList ) {
            list.add( toTradeRelativeDataDto( tradingData ) );
        }

        return list;
    }
}
