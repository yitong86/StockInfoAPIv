package com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.controllers;

import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.model.Overview;
import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.repositories.OverviewRepository;
import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.utills.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;
    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @Autowired
    private OverviewRepository overviewRepository;


    @GetMapping("/{symbol}")
    public ResponseEntity<?> getOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;
            Overview alphaVantageResponse  = restTemplate.getForObject(url,  Overview.class);
            if(alphaVantageResponse == null){
                ApiError.throwErr(500,"Did not receive response from AV");

            }else if(alphaVantageResponse.getSymbol() == null){
                ApiError.throwErr(404,"Invalid stock symbol: ");

            }
            return ResponseEntity.ok(alphaVantageResponse.toString());
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;
            Overview alphaVantageResponse  = restTemplate.getForObject(url,  Overview.class);

            if(alphaVantageResponse == null){
                ApiError.throwErr(500,"Did not receive response from AV");

            }else if(alphaVantageResponse.getSymbol() == null){
                ApiError.throwErr(404,"Invalid stock symbol: " + symbol);

            }
            Overview savedOverview = overviewRepository.save(alphaVantageResponse);
            return ResponseEntity.ok(savedOverview);

        }catch(HttpClientErrorException e){
             return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());

         }catch(DataIntegrityViolationException e){
            return ApiError.customApiError("Can not upload duplicate stock data",400);
        }catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }
//get all overviews from SQL data
    @GetMapping("/all")
    private ResponseEntity<?> getAllOverviews(){
        try{
            Iterable<Overview> allOverviews = overviewRepository.findAll();
            return ResponseEntity.ok(allOverviews);
        }catch(HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        } catch(Exception e){
                return ApiError.genericApiError(e);
        }
    }
    @GetMapping("/id/{id}")
    private ResponseEntity<?> getOverviewsById(@PathVariable String id){
        try{
            Optional<Overview> foundOverviews = overviewRepository.findById(Long.parseLong(id));
            if(foundOverviews.isEmpty()){
                ApiError.throwErr(404,id + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverviews);
        }catch(HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(NumberFormatException e) {
            return ApiError.customApiError("Invalid ID must be a number" + id,400);
        } catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/symbol/{symbol}")
    private ResponseEntity<?> getOverviewsBySymbol(@PathVariable String symbol){
        try{
            Optional<Overview> foundOverview = overviewRepository.findBySymbol(symbol);
            if(foundOverview .isEmpty()){
                ApiError.throwErr(404,symbol + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/exchange/{exchange}")
    private ResponseEntity<?> getOverviewsByExchange(@PathVariable String exchange){
        try{
            List<Overview> foundOverview = overviewRepository.findByExchange(exchange);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404,exchange + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/name/{name}")
    private ResponseEntity<?> getOverviewsByName(@PathVariable String name){
        try{
           Optional<Overview> foundOverview = overviewRepository.findByName(name);
            if(foundOverview .isEmpty()){
                ApiError.throwErr(404,name + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }
    @GetMapping("/assetType/{assetType}")
    private ResponseEntity<?> getOverviewsByAssetType(@PathVariable String assetType){
        try{
            List<Overview> foundOverview = overviewRepository.findByAssetType(assetType);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404,assetType + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }
    @GetMapping("/currency/{currency}")
    private ResponseEntity<?> getOverviewsByCurrency(@PathVariable String currency){
        try{
            List<Overview> foundOverview = overviewRepository.findByCurrency(currency);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404,currency + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }
    @GetMapping("/country/{country}")
    private ResponseEntity<?> getOverviewsByCountry(@PathVariable String country){
        try{
            List<Overview> foundOverview = overviewRepository.findByCountry(country);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404,country + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }
    @GetMapping("/sector/{sector}")
    private ResponseEntity<?> getOverviewsBySector(@PathVariable String sector){
        try{
            List<Overview> foundOverview = overviewRepository.findBySector(sector);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404,sector + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllOverviews(){
        try{
            long allOverviewsCount = overviewRepository.count();
            if(allOverviewsCount == 0)
                return ResponseEntity.ok("No Overview to delete.");
            overviewRepository.deleteAll();

            return ResponseEntity.ok("Deleted Overviews: " + allOverviewsCount);
        }catch(HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        } catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }
    @DeleteMapping("/id/{id}")
    private ResponseEntity<?> deleteById(@PathVariable String id){
        try{
            //can cause numberformateException
            long overviewId = Long.parseLong(id);

            Optional<Overview> foundOverviews = overviewRepository.findById(Long.parseLong(id));
            if(foundOverviews.isEmpty()){
                ApiError.throwErr(404,id + "did not match any overview");

            }
            overviewRepository.deleteById(overviewId);

            return ResponseEntity.ok(foundOverviews);

        }catch(HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(NumberFormatException e) {
            return ApiError.customApiError("ID must be a number" + id,400);
        } catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

}


