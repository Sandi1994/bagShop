package lk.sanchana_bag_shop.asset.purchase_order_item.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.sanchana_bag_shop.asset.common_asset.model.enums.LiveDead;
import lk.sanchana_bag_shop.asset.item.entity.Item;
import lk.sanchana_bag_shop.asset.purchase_order.entity.PurchaseOrder;
import lk.sanchana_bag_shop.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "PurchaseOrderItem" )
public class PurchaseOrderItem extends AuditEntity {

    @Column( nullable = false )
    private String quantity;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal buyingPrice;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal lineTotal;

    @Enumerated( EnumType.STRING)
    private LiveDead liveDead;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    private Item item;

}
