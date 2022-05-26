package com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.repositories;

import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.model.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OverviewRepository extends CrudRepository<Overview,Long> {

   Optional<Overview> findBySymbol(String symbol);

   List<Overview> findByExchange(String exchange);

   Optional<Overview> findByName(String name);

   List<Overview> findByAssetType(String assetType);

   List<Overview> findByCurrency(String currency);

   List<Overview> findByCountry(String country);

   List<Overview> findBySector(String sector);




}
