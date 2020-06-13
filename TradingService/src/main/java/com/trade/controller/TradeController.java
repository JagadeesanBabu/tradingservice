package com.trade.controller;

import com.trade.pojo.TradeDataBaseDto;
import com.trade.pojo.TradeMovAvgConvDivgDto;
import com.trade.pojo.TradeRelativeDataDto;
import com.trade.pojo.request.FilterRequest;
import com.trade.service.TradeChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
@Slf4j
public class TradeController {

    private final TradeChartService tradeChartService;

    @GetMapping("/trades/byBasic")
    @ResponseBody
    public ResponseEntity<List<TradeDataBaseDto>> getTradeDataByIndexByBasic(@RequestParam(value = "index", required = false, defaultValue = "BANKNIFTY") String index,
                                                                     FilterRequest filterRequest) {
        return ResponseEntity.ok(tradeChartService.getDailyTradeClosedPriceByIndex(index, filterRequest));
    }

    @GetMapping("/trades/byRelativeStrength")
    @ResponseBody
    public ResponseEntity<List<TradeRelativeDataDto>> getTradeDataByIndexByRsi(@RequestParam(value = "index", required = false, defaultValue = "BANKNIFTY") String index,
                                                                        @RequestParam(value = "relativeIndexFactor", required = false, defaultValue = "14") Integer relativeIndexFactor,
                                                                        FilterRequest filterRequest) {
        return ResponseEntity.ok(tradeChartService.getDailyRelativeTradeDataByIndex(index, filterRequest, relativeIndexFactor));
    }


    @GetMapping("/trades/byMACD")
    @ResponseBody
    public ResponseEntity<List<TradeMovAvgConvDivgDto>> getTradeDataByMacd(@RequestParam(value = "index", required = false, defaultValue = "BANKNIFTY") String index,
                                                                                 FilterRequest filterRequest) {
        return ResponseEntity.ok(tradeChartService.getDailyTradeDataByMacd(index, filterRequest));
    }
}
