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

    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllOverviews(){
        try{
            long allOverviewsCount = overviewRepository.count();
            if(allOverviewsCount == 0)
                return ResponseEntity.ok("No Overview to delete");
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


