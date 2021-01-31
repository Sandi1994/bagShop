package lk.sanchana_bag_shop.asset.item.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import lk.sanchana_bag_shop.asset.brand.entity.Brand;
import lk.sanchana_bag_shop.asset.category.entity.Category;
import lk.sanchana_bag_shop.asset.color.entity.ItemColor;
import lk.sanchana_bag_shop.asset.common_asset.model.enums.LiveDead;
import lk.sanchana_bag_shop.asset.item.entity.enums.ItemSize;
import lk.sanchana_bag_shop.asset.item.entity.enums.ItemStatus;
import lk.sanchana_bag_shop.asset.ledger.entity.Ledger;
import lk.sanchana_bag_shop.asset.purchase_order_item.entity.PurchaseOrderItem;
import lk.sanchana_bag_shop.asset.supplier_item.entity.SupplierItem;
import lk.sanchana_bag_shop.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Item")
public class Item extends AuditEntity {

    @Size(min = 5, message = "Your name cannot be accepted")
    private String name;

    private String rop;

    @Column( unique = true )
    private String code;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal sellPrice;

    @Enumerated( EnumType.STRING )
    private ItemStatus itemStatus;

    @Enumerated( EnumType.STRING)
    private ItemSize itemSize;

    @Enumerated( EnumType.STRING)
    private LiveDead liveDead;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;

    @ManyToOne
    private ItemColor itemColor;

    @OneToMany( mappedBy = "item" )
    private List< SupplierItem > supplierItems;

    @OneToMany( mappedBy = "item" )
    @JsonBackReference
    private List< Ledger > ledgers;

    @OneToMany( mappedBy = "item" )
    private List< PurchaseOrderItem > purchaseOrderItems;
}