package com.trade.scheduler;

import com.trade.jpa.TradeDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TradeScheduler {
    private final TradeDataRepository tradeDataRepository;

//    //For now we will schedule every 1 minute to fetch data
//    @Scheduled(fixedRate = 6000)
//    public void scheduleTradeDataCollector() {
//        log.info("Scheduler to poll service to gather data");
//    }

}
