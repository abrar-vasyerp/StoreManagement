package org.abrar.storemanagement.repository;

import org.abrar.storemanagement.entity.StockMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMasterRepository extends JpaRepository<StockMaster,Long> {
}
