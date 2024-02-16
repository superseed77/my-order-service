package org.formation.repository;

import java.util.List;

import org.formation.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findByUserId(int userId);

}
