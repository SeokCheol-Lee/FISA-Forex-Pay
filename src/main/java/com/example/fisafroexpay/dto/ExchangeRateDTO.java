package com.example.fisafroexpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

//@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateDTO {

    @JsonProperty("result")
    private int result;

    @JsonProperty("cur_unit")
    private String curUnit;

    @JsonProperty("ttb")
    private String ttb;

    @JsonProperty("tts")
    private String tts;

    @JsonProperty("deal_bas_r")
    private String dealBasR;

    @JsonProperty("bkpr")
    private String bkpr;

    @JsonProperty("yy_efee_r")
    private String yyEfeeR;

    @JsonProperty("ten_dd_efee_r")
    private String tenDdEfeeR;

    @JsonProperty("kftc_bkpr")
    private String kftcBkpr;

    @JsonProperty("kftc_deal_bas_r")
    private String kftcDealBasR;

    @JsonProperty("cur_nm")
    private String curNm;
}