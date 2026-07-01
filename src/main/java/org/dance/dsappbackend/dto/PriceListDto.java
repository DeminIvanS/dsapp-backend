package org.dance.dsappbackend.dto;

import jakarta.persistence.*;
import org.dance.dsappbackend.entity.PriceList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PriceListDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDate validFrom;
    private LocalDate validTo;
    private LocalDateTime createdAt;

    public PriceListDto() {
    }


    public static PriceListDto from(PriceList priceList){
        PriceListDto dto = new PriceListDto();
        dto.id = priceList.getId();
        dto.name = priceList.getName();
        dto.price = priceList.getPrice();
        dto.validFrom = priceList.getValidFrom();
        dto.validTo = priceList.getValidTo();
        dto.createdAt = priceList.getCreatedAt();
        return dto;
    }
    public PriceList toEntity(){
        PriceList priceList = new PriceList();
        priceList.setId(this.id);
        priceList.setName(this.name);
        priceList.setPrice(this.price);
        priceList.setValidFrom(this.validFrom);
        priceList.setValidTo(this.validTo);
        priceList.setCreatedAt(this.createdAt);
        return priceList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
