package com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.controllers;

import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.model.Overview;
import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.repositories.OverviewRepository;
import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.utills.ApiErrorHanding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;
    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @Autowired
    private OverviewRepository overviewRepository;
//http://localhost:4000/api/overview/test
    @GetMapping("/test")
    public ResponseEntity<?> test(RestTemplate restTemplate) {
        try {
            String url =BASE_URL + "&symbol=IBM&apikey=demo";
            Overview alphaVantageResponse  = restTemplate.getForObject(url, Overview.class);
            return ResponseEntity.ok(alphaVantageResponse);
        } catch(IllegalArgumentException e) {
            return ApiErrorHanding.customApiError("Error in test Overview. check URL used for AV request", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return ApiErrorHanding.genericApiError(e);
        }
    }
    //test upload to database
    @PostMapping("/test")
    public ResponseEntity<?> UploadOverview(RestTemplate restTemplate) {
        try {
            String url =BASE_URL + "&symbol=IBM&apikey=demo";
            Overview alphaVantageResponse  = restTemplate.getForObject(url, Overview.class);
            if(alphaVantageResponse == null){
                return ApiErrorHanding.customApiError("Did not receive response from AV", HttpStatus.INTERNAL_SERVER_ERROR);
            }else if(alphaVantageResponse.getSymbol() == null){
                return ApiErrorHanding.customApiError("No data retrieved from AV " ,HttpStatus.NOT_FOUND);
            }
            Overview savedOverview = overviewRepository.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        }catch(DataIntegrityViolationException e){
            return ApiErrorHanding.customApiError("Can not upload duplicate stock data",HttpStatus.BAD_REQUEST);
        }catch(IllegalArgumentException e) {
            return ApiErrorHanding.customApiError("Error in test Overview. check URL used for AV request", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return ApiErrorHanding.genericApiError(e);
        }
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;
            Overview alphaVantageResponse  = restTemplate.getForObject(url,  Overview.class);
            if(alphaVantageResponse == null){
                return ApiErrorHanding.customApiError("Did not receive response from AV", HttpStatus.INTERNAL_SERVER_ERROR);
            }else if(alphaVantageResponse.getSymbol() == null){
                return ApiErrorHanding.customApiError("Invalid stock symbol: " + symbol,HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(alphaVantageResponse.toString());
        } catch (Exception e) {
            return ApiErrorHanding.genericApiError(e);
        }
    }

    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;
            Overview alphaVantageResponse  = restTemplate.getForObject(url,  Overview.class);

            if(alphaVantageResponse == null){
                return ApiErrorHanding.customApiError("Did not receive response from AV", HttpStatus.INTERNAL_SERVER_ERROR);
            }else if(alphaVantageResponse.getSymbol() == null){
                return ApiErrorHanding.customApiError("Invalid stock symbol: " + symbol,HttpStatus.NOT_FOUND);
            }
            Overview savedOverview = overviewRepository.save(alphaVantageResponse);
            return ResponseEntity.ok(savedOverview);

        }catch(DataIntegrityViolationException e){
            return ApiErrorHanding.customApiError("Can not upload duplicate stock data",HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return ApiErrorHanding.genericApiError(e);
        }
    }
}

