package com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Overview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false,unique = true)
    private long Id;

    @JsonProperty("Symbol")
    @Column(name = "symbol",nullable = false,unique = true)
    private String symbol;

    @JsonProperty("AssetType")
    @Column(name = "asset_type",nullable = false)
    private String assetType;

    @JsonProperty("Name")
    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @JsonProperty("Exchange")
    @Column(name = "exchange",nullable = false)
    private String exchange;

    @JsonProperty("Currency")
    @Column(name = "currency",nullable = false)
    private String currency;

    @JsonProperty("Country")
    @Column(name = "country",nullable = false)
    private String country;

    @JsonProperty("Sector")
    @Column(name = "sector",nullable = false)
    private String sector;

    @JsonProperty("Industry")
    @Column(name = "industry",nullable = false)
    private String industry;

    @JsonProperty("MarketCapitalization")
    @Column(name = "market_Cap",nullable = false)
    private long marketCap;
//"52WeekLow": "111.84",
    @JsonProperty("52WeekLow")
    @Column(name = "year_low",nullable = false)
    private float yearLow;
//DividendDate": "2022-06-10",
    @JsonProperty("DividendDate")
    @Column(name = "dividend_date",nullable = false)
    private String dividendDate;

    public long getId() {
        return Id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public float getYearLow() {
        return yearLow;
    }

    public String getDividendDate() {
        return dividendDate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"Id\":" + Id +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"assetType\":\"" + assetType + '"' +
                ", \"name\":\"" + name + '"' +
                ", \"exchange\":\"" + exchange + '"' +
                ", \"currency\":\"" + currency + '"' +
                ", \"country\":\"" + country + '"' +
                ", \"sector\":\"" + sector + '"' +
                ", \"industry\":\"" + industry + '"' +
                ", \"marketCap\":" + marketCap +
                ", \"yearLow\":" + yearLow +
                ", \"dividendDate\":\"" + dividendDate + '"' +
                '}';
    }
}
