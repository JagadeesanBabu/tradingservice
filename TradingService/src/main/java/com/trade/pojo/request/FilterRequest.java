package com.trade.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest {
    private String sortValue;
    private Sort.Direction sortOrder;
    private Integer from;
    private Integer size;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;


    public Integer getFrom() {
        return Objects.isNull(from) ? 0 : from;
    }

    public Integer getSize() {
        return Objects.isNull(size) ? 108 : size;
    }

    public String getSortValue() { return Objects.isNull(sortValue) ? "tradeDate" : sortValue; }

    public Sort.Direction getSortOrder() {
        return Objects.isNull(sortOrder) ? Sort.Direction.ASC : sortOrder;
    }

}
