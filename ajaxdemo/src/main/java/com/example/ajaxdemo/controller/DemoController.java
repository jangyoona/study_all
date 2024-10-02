package com.example.ajaxdemo.controller;

import com.example.ajaxdemo.dto.StockQuote;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping(path = "/key-economy-indicator", produces = "application/json;charset=utf-8")
    public String loadKeyEconomyIndicator() {

        String apiKey = "XIXDXQPRTA4PLPFTS37V";
        String respType = "json";
        String lang = "kr";
        int from = 1,   to = 100;
        String code1 = "731Y001",   code2 = "0000001";
        String cycle = "D";
        String fromDate = "20240813",   toDate = "20240814";

        String apiUrl = String.format("https://ecos.bok.or.kr/api/StatisticSearch/%s/%s/%s/%d/%d/%s/%s/%s/%s/%s",
                                       apiKey, respType, lang, from, to, code1, cycle, fromDate, toDate, code2);
        String data = get(apiUrl, "GET", null);

        return data;
    }

    @RequestMapping(path = "/historical-stock-quotes", produces = "application/json;charset=utf-8")
    public String loadStockDataBySymbol(@RequestParam(defaultValue = "GOOG") String symbol, Model model) { // symbol 버튼으로 만들어서 종목 고르게 하면 꿀잼일듯
//        String apiKey = "IR73JL7R00PQQZUT";
//        String apiUrl = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&month=%s&symbol=%s&apikey=%s", month,event, apiKey);

        String apiKey = "AySNu9YaBXRgiGqh4fiZjHFaujzek88U";
        String from = "2024-01-03";
        String to = "2024-08-15";
        String day = "week";
        String event = "NVDA";
        String apiUrl = String.format("https://api.polygon.io/v2/aggs/ticker/%s/range/1/%s/%s/%s?apiKey=%s",
                                       event, day, from, to, apiKey);
        String data = get(apiUrl,"GET", null);

        model.addAttribute("event", event);
        return data;
    }

    @RequestMapping(path = "/historical-stock-quotes2", produces = "application/json;charset=utf-8")
    public List<StockQuote> loadStockDataByCode(@RequestParam(defaultValue = "005930") String code) { // symbol 버튼으로 만들어서 종목 고르게 하면 꿀잼일듯

        ArrayList<StockQuote> quotes = new ArrayList<>();
        String pageUrl = "https://finance.naver.com/item/sise_day.naver?code=%s&page=%d";

        for(int i=1; i< 4; i++) {
            Connection conn = Jsoup.connect(String.format(pageUrl, code, i));
            try {
                Document doc = conn.get();
                // System.out.println(doc.html());

                for (Element e : doc.select("table.type2 tr")) {
                    Elements tds = e.select("td");
                    if (tds.size() == 7) {
                        StockQuote quote = new StockQuote();
                        quote.setDate(tds.get(0).text());
                        quote.setClose(Integer.parseInt(tds.get(1).text().replace(",", "")));
                        String[] v = tds.get(2).text().split(" ");
                        if(v.length == 1) {
                            quote.setIncrease(Integer.parseInt(v[0].replace("보합", "").replace(",", "")));
                        } else {
                            quote.setIncrease(Integer.parseInt(v[1].replace(",", "")));
                            if(v[0].equals("하락")) {
                                quote.setIncrease(quote.getIncrease() * -1);
                            }
                        }
                        quote.setOpen(Integer.parseInt(tds.get(3).text().replace(",", "")));
                        quote.setHigh(Integer.parseInt(tds.get(4).text().replace(",", "")));
                        quote.setLow(Integer.parseInt(tds.get(5).text().replace(",", "")));
                        quote.setVolume(Integer.parseInt(tds.get(6).text().replace(",", "")));
                        quotes.add(quote);

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 크롤링을 하면서 for문을 사용할 경우 꼭! sleep을 걸어주자.(계속해서 요청을 보내면 해당 사이트에서 나를 막아버림)
            try { Thread.sleep(1000); } catch (Exception ex) {}
        }
        return quotes;
    }

    @RequestMapping(value = "/upload-image", produces = "plain/text;charset=utf-8")
    public String uploadImage(MultipartFile imageFile, HttpServletRequest req) {

        System.out.println(imageFile.getOriginalFilename());
        System.out.println(imageFile.getSize());

        return "success";
    }



    private String get(String apiUrl, String requestMethod, Map<String, String> requestHeaders){

        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod(requestMethod); // get or post
            if (requestHeaders != null) {
                for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue()); // 헤더 정보 담기
                }
            }

            int responseCode = con.getResponseCode(); // 요청 보내기 + 응답수신
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to request and response", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            // 웹 요청을 수행하는 객체 생성 (주소창에 www.naver.com 엔터 치잖아. 그거 코드로 표현)
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid API URL format. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("Fail to connect. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder(1024); // StringBuilder 기본값이 16개문자밖에 안됨 => 1024로 늘림 부족하면 점점 늘리면됨

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("Fail to read API response.", e);
        }
    }

}
