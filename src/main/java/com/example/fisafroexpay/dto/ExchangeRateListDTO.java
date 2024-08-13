package com.example.fisafroexpay.dto;

import lombok.*;

import java.util.List;

//@Data

@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateListDTO {

    @Getter
    @Setter
    private List<ExchangeRateDTO> ExchangeRateDTO;
}