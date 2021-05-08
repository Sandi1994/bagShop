package lk.sanchana_bag_shop.asset.supplier.dao;

import lk.sanchana_bag_shop.asset.supplier.entity.Supplier;
import lk.sanchana_bag_shop.asset.supplier_item.entity.enums.ItemSupplierStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository< Supplier, Integer > {
  Supplier findFirstByOrderByIdDesc();

  Supplier findByIdAndItemSupplierStatus(Integer supplierId, ItemSupplierStatus itemSupplierStatus);

  Supplier findByBrn(String brn);
}
