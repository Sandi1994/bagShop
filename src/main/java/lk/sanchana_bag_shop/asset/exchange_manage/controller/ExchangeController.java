package lk.sanchana_bag_shop.asset.exchange_manage.controller;

import lk.sanchana_bag_shop.asset.common_asset.model.enums.LiveDead;
import lk.sanchana_bag_shop.asset.exchange_manage.entity.ExchangeInvoiceLedger;
import lk.sanchana_bag_shop.asset.exchange_manage.service.ExchangeInvoiceLedgerService;
import lk.sanchana_bag_shop.asset.invoice.entity.Invoice;
import lk.sanchana_bag_shop.asset.invoice.service.InvoiceService;
import lk.sanchana_bag_shop.asset.invoice_ledger.entity.InvoiceLedger;
import lk.sanchana_bag_shop.asset.invoice_ledger.service.InvoiceLedgerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping( "/exchange" )
public class ExchangeController {

  private final InvoiceLedgerService invoiceLedgerService;
  private final InvoiceService invoiceService;
  private final ExchangeInvoiceLedgerService exchangeInvoiceLedgerService;

  public ExchangeController(InvoiceLedgerService invoiceLedgerService, InvoiceService invoiceService,
                            ExchangeInvoiceLedgerService exchangeInvoiceLedgerService) {
    this.invoiceLedgerService = invoiceLedgerService;
    this.invoiceService = invoiceService;
    this.exchangeInvoiceLedgerService = exchangeInvoiceLedgerService;
  }

  @GetMapping( "/form" )
  public String form() {

    return "exchange/exchange";
  }

  @PostMapping( value = "/search" )
  public String addInvoiceDetails(@ModelAttribute Invoice invoice, Model model) {

    if ( invoice.getCode() != null ) {

      List< Invoice > invoices = invoiceService.search(invoice);

      if ( invoices.size() == 1 ) {
        Invoice invoiceDb = invoiceService.findById(invoices.get(0).getId());
        model.addAttribute("invoiceDetail", invoiceDb);
        model.addAttribute("customerDetail", invoiceDb.getCustomer());
        model.addAttribute("invoice", invoiceDb);
        model.addAttribute("invoiceLedgerStatuses", LiveDead.values());
        return "exchange/exchange-form";
      }

    }

    model.addAttribute("messageShow", true);
    model.addAttribute("message", "There is not invoice in the system according to the provided details \n Could " +
        "you please search again !!");
    return "exchange/exchange";
  }

  @PostMapping( value = "/searchSave" )
  public String saveSearchInvoice(@ModelAttribute Invoice invoice, Model model) {

    for ( InvoiceLedger invoiceLedger : invoice.getInvoiceLedgers() ) {
      if ( invoiceLedger.getLiveDead().equals(LiveDead.STOP) ) {
        ExchangeInvoiceLedger exchangeInvoiceLedger = new ExchangeInvoiceLedger();
        InvoiceLedger invoiceLedgerDb = invoiceLedgerService.findById(invoiceLedger.getId());

        exchangeInvoiceLedger.setInvoiceLedger(invoiceLedgerDb);
        exchangeInvoiceLedger.setCount(Integer.parseInt(invoiceLedger.getQuantity()));
        exchangeInvoiceLedgerService.persist(exchangeInvoiceLedger);

        int counter = Integer.parseInt(invoiceLedgerDb.getQuantity()) - Integer.parseInt(invoiceLedger.getQuantity());
        invoiceLedgerDb.setQuantity(String.valueOf(counter));
        invoiceLedgerService.persist(invoiceLedgerDb);

      }
    }


    return "redirect:/home";
  }


}
