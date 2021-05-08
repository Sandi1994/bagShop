package lk.sanchana_bag_shop.asset.invoice.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InvoiceState {
    ACTIVE("Active"),
    STOP("Stop");

    private final String invoiceState;
}
