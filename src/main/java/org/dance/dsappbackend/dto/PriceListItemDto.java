package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.PriceListItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceListItemDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private boolean isActive;
    private LocalDateTime createdAt;

    public PriceListItemDto() {
    }


    public static PriceListItemDto from(PriceListItem priceListItem){
        PriceListItemDto dto = new PriceListItemDto();
        dto.id = priceListItem.getId();
        dto.name = priceListItem.getName();
        dto.price = priceListItem.getPrice();
        dto.isActive = priceListItem.isActive();
        dto.createdAt = priceListItem.getCreatedAt();
        return dto;
    }

    public PriceListItem toEntity(){
        PriceListItem priceListItem = new PriceListItem();
        priceListItem.setId(this.id);
        priceListItem.setName(this.name);
        priceListItem.setPrice(this.price);
        priceListItem.setActive(this.isActive);
        priceListItem.setCreatedAt(this.createdAt);
        return priceListItem;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
