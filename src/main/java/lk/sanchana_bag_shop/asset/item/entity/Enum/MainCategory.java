package lk.sanchana_bag_shop.asset.item.entity.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MainCategory {
    F("Ladies"),
    M("Gents"),
    K("Kids");

    private final String mainCategory;
}
