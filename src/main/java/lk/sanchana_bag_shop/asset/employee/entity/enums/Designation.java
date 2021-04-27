package lk.sanchana_bag_shop.asset.employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
  ADMIN("Admin"),
  MANAGER("Manager"),
  ACCOUNT_MANAGER("Account Manager"),
  CASHIER("Cashier"),
  STOCK_KEEPER("Stock Keeper");

  private final String designation;


}
