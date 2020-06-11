package com.trade.mapper;

import com.trade.entity.TradingData;
import com.trade.pojo.TradeDataBaseDto;
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
public class TradeDataMapperImpl implements TradeDataMapper {

    @Override
    public TradeDataBaseDto toTradeDataDto(TradingData data) {
        if ( data == null ) {
            return null;
        }

        TradeDataBaseDto tradeDataBaseDto = new TradeDataBaseDto();

        tradeDataBaseDto.setTradeDate( data.getTradeDate() );
        tradeDataBaseDto.setOpenPrice( data.getOpenPrice() );
        tradeDataBaseDto.setClosedPrice( data.getClosedPrice() );
        tradeDataBaseDto.setHighPrice( data.getHighPrice() );
        tradeDataBaseDto.setLowPrice( data.getLowPrice() );
        tradeDataBaseDto.setNoOfSharesTraded( data.getNoOfSharesTraded() );
        tradeDataBaseDto.setTurnover( data.getTurnover() );
        tradeDataBaseDto.setIndex( data.getIndex() );

        return tradeDataBaseDto;
    }

    @Override
    public List<TradeDataBaseDto> toTradeDataDtoList(List<TradingData> dataList) {
        if ( dataList == null ) {
            return null;
        }

        List<TradeDataBaseDto> list = new ArrayList<TradeDataBaseDto>( dataList.size() );
        for ( TradingData tradingData : dataList ) {
            list.add( toTradeDataDto( tradingData ) );
        }

        return list;
    }
}
