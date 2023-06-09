package com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.repositories;

import com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.model.Overview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
public interface OverviewRepository extends JpaRepository<Overview,Long> {

    List<Overview> findBySymbol(String symbol);
    List<Overview> findById(long id);
    List<Overview> findBySector(String sector);
    List<Overview> findByName(String name);
    List<Overview> findByExchange(String exchange);
    List<Overview> findByAssetType(String assetType);
    List<Overview> findByCurrency(String currency);
    List<Overview> findByCountry(String country);
    List<Overview> findByMarketCapGreaterThanEqual(long marketCap);
   List<Overview> findByMarketCapLessThanEqual(long marketCap);

   List<Overview> deleteBySymbol(String symbol);
   List<Overview> deleteById(long id);
   List<Overview> deleteBySector(String sector);
   List<Overview> deleteByCurrency(String currency);
   List<Overview> deleteByCountry(String country);
   List<Overview> deleteByName(String name);
   List<Overview> deleteByExchange(String exchange);
   List<Overview> deleteByAssetType(String assetType);
   List<Overview> deleteByMarketCapGreaterThanEqual(long marketCap);
   List<Overview> deleteByMarketCapLessThanEqual(long marketCap);
}
