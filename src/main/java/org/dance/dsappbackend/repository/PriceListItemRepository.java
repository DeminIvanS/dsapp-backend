package org.dance.dsappbackend.repository;

import org.dance.dsappbackend.entity.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {
    List<PriceListItem> findByIsActive(Boolean isActive);

}
