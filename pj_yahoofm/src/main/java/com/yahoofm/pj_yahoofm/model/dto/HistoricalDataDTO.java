package com.yahoofm.pj_yahoofm.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoricalDataDTO {
    private LocalDate date;
    private double open;
    private double high;
    private double low;
    public double close;
    private long volume;
}
