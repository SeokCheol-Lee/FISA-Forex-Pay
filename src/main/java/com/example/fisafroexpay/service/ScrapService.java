package com.example.fisafroexpay.service;

import com.example.fisafroexpay.dto.Country;
import com.example.fisafroexpay.entity.ExchangeRate;
import com.example.fisafroexpay.repository.ExchangeRateRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

  private final ExchangeRateRepository exchangeRateRepository;

  //매일 자정에 환율 데이터 업데이트
  @Scheduled(cron = "0 0 0 * * ?")
  public void scrap() throws IOException {
    Map<String, Country> countries;
    // 네이버 환율 정보 페이지에서 데이터 가져오기
    Connection conn = Jsoup.connect("https://m.stock.naver.com/marketindex/home/exchangeRate/exchange");
    Document doc = conn.get();
    Element element = doc.selectFirst("#__NEXT_DATA__");
    String str = element.data();

    Gson gson = new Gson();

    // JSON 문자열을 JsonElement 객체로 변환
    JsonElement rootElement = gson.fromJson(str, JsonElement.class);
    JsonObject rootObject = rootElement.getAsJsonObject();

    // 필요한 데이터에 접근
    JsonArray queriesArray = rootObject.getAsJsonObject("props")
        .getAsJsonObject("pageProps")
        .getAsJsonObject("dehydratedState")
        .getAsJsonArray("queries");

    JsonObject resultArray = queriesArray.get(2)
        .getAsJsonObject()
        .getAsJsonObject("state")
        .getAsJsonObject("data")
        .getAsJsonObject("result");

    countries = gson.fromJson(resultArray, new TypeToken<Map<String, Country>>(){}.getType());
    for(Country country : countries.values()) {
      String cName = country.getName().split(" ")[1];
      float calc = country.getCalcPrice();
      if(cName.equals("JPY")){
        cName = "JPY(100)";
        calc = calc * 100f;
      }else if(cName.equals("CNY")){
        cName = "CNH";
      }
      ExchangeRate exchangeRate = country.toEntity(cName, calc);
      exchangeRateRepository.save(exchangeRate);
    }
  }

}
