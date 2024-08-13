package com.example.fisafroexpay.service;

import com.example.fisafroexpay.dto.ExchangeRateDTO;
import com.example.fisafroexpay.entity.ExchangeRate;
import com.example.fisafroexpay.repository.ExchangeRateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    private static final Logger logger = Logger.getLogger(ExchangeRateService.class.getName());
    private static final String authkey = "7zNlvx0a71eFW32VdPuw3RIPgGOKk45p";
    public static String date = "20240813"; // getCurrentDate();
    private static final String API_URL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="+authkey+"&searchdate="+date+"&data=AP01";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate restTemplate = new RestTemplate();

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }
    /**
     * API에서 환율 데이터를 가져와 JSON 문자열로 반환합니다.
     *
     * @return 환율 데이터 JSON 문자열
     */
    public static String fetchExchangeRateData() {
        try {
            String response = restTemplate.getForObject(API_URL, String.class);
            if (response != null) {
                return response;
            } else {
                logger.log(Level.SEVERE, "No data received from the API.");
                throw new RuntimeException("No data received from the API.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while fetching data", e);
            throw new RuntimeException("Exception occurred while fetching data", e);
        }
    }

    /**
     * JSON 문자열을 파싱하여 ExchangeRateDTO 객체의 리스트로 변환합니다.
     *
     * @param jsonString JSON 형식의 문자열
     * @return ExchangeRateDTO 객체의 리스트
     */
    public static List<ExchangeRateDTO> parseExchangeRateData(String jsonString) {
        try {
            return objectMapper.readValue(jsonString,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ExchangeRateDTO.class));
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "Error processing JSON", e);
            throw new RuntimeException("Error processing JSON", e);
        }
    }

    /**
     * 파싱한 문자열을 mysql에 적재하는 코드
     **/
    public void saveExchangeRateDTO(List<ExchangeRateDTO> exchangeRateDTOs) {
        List<ExchangeRate> exchangeRates = exchangeRateDTOs.stream()
                .map(dto -> {
                    ExchangeRate rate = new ExchangeRate();
                    rate.setBaseExchangeRate((BigDecimal.valueOf(Double.parseDouble(dto.getDealBasR().replace(",", "")))));
                    rate.setBaseCurrency(dto.getCurUnit());
                    rate.setTargetCurrency(dto.getCurNm());
                    return rate;
                })
                .collect(Collectors.toList());
        exchangeRateRepository.saveAll(exchangeRates);
    }
    /**
     * 전체 프로세스를 처리하는 메서드입니다.
     *
     * @return ExchangeRateDTO 객체의 리스트
     */
//    public List<ExchangeRateDTO> getExchangeRates() {
//        String jsonData = fetchExchangeRateData();
//        return parseExchangeRateData(jsonData);
//    }
    /**
     *  현재 날짜에 자동 매핑되는 코드입니다
     **/
//    private static String getCurrentDate() {
//        LocalDate today = LocalDate.now(); // 현재 날짜
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식
//        return today.format(formatter); // 날짜를 문자열로 포맷
//    }
}
