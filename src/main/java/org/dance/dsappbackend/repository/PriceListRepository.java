package org.dance.dsappbackend.repository;

import org.dance.dsappbackend.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

}
