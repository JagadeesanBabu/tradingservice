package com.trade.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
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
    private Integer relativeIndexFactor;

    public Integer getFrom() {
        return Objects.isNull(from) ? 0 : from;
    }

    public Integer getSize() {
        return Objects.isNull(size) ? 10 : size;
    }

    public Integer getRelativeIndexFactor() { return Objects.isNull(relativeIndexFactor) ? 13 : relativeIndexFactor; }

    public String getSortValue() { return Objects.isNull(sortValue) ? "tradeDate" : sortValue; }

    public Sort.Direction getSortOrder() {
        return Objects.isNull(sortOrder) ? Sort.Direction.DESC : sortOrder;
    }

}
