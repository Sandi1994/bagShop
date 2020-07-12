package lk.sanchana.bagShop.asset.supplier.dao;



import lk.sanchana.bagShop.asset.supplier.entity.SupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierItemDao extends JpaRepository<SupplierItem, Integer> {
}
