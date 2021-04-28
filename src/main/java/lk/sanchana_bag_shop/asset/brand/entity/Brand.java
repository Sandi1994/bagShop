package lk.sanchana_bag_shop.asset.brand.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.sanchana_bag_shop.asset.category.entity.Category;
import lk.sanchana_bag_shop.asset.item.entity.Item;
import lk.sanchana_bag_shop.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Brand")
public class Brand extends AuditEntity {
    @Size(min = 1, message = "This name length should be more than one character")
    private String name;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "brand")
    private List<Item> items;

}
