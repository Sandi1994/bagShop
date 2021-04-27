package lk.sanchana_bag_shop.asset.customer.service;


import lk.sanchana_bag_shop.asset.common_asset.model.enums.LiveDead;
import lk.sanchana_bag_shop.asset.customer.dao.CustomerDao;
import lk.sanchana_bag_shop.asset.customer.entity.Customer;
import lk.sanchana_bag_shop.util.interfaces.AbstractService;
import lk.sanchana_bag_shop.util.service.EmailService;
import lk.sanchana_bag_shop.util.service.TwilioMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = "customer" )
public class CustomerService implements AbstractService<Customer, Integer> {
    private final CustomerDao customerDao;
    private final EmailService emailService;
    private final TwilioMessageService twilioMessageService;

    @Autowired
    public CustomerService(CustomerDao customerDao, EmailService emailService, TwilioMessageService twilioMessageService) {
        this.customerDao = customerDao;
        this.emailService = emailService;
        this.twilioMessageService = twilioMessageService;
    }

    public List<Customer> findAll() {
        return customerDao.findAll().stream()
            .filter(x -> LiveDead.ACTIVE.equals(x.getLiveDead()))
            .collect(Collectors.toList());
    }

    public Customer findById(Integer id) {
        return customerDao.getOne(id);
    }

    public Customer persist(Customer customer) {
        //email service
        Customer customer1 = customerDao.save(customer);
        if(customer1.getEmail()!=null){
            String message = "Dear "+customer.getName()+
                    "\n Code    :"+customer.getCode()+
                    "\n Customer name   :"+customer.getName()+
                    "\n Mobile  :"+customer.getMobile()+
                    "\n Address :"+customer.getAddress()+
                    "\n NIC     :"+customer.getNic();
            emailService.sendEmail(customer1.getEmail(), "Welcome to Sanchan Bag Shop", message);
        }
//        mobile service
        if (customer.getMobile() != null) {
            try {
                String mobileNumber = customer.getMobile().substring(1, 10);
                twilioMessageService.sendSMS("+94" + mobileNumber, "Successfully registered in " +
                        "Sanchana Bag Shop \nPlease Check Your Email Form Further Details");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ( customer.getId() == null ) {
            customer.setLiveDead(LiveDead.ACTIVE);
        }
        return customer1;
    }

    public boolean delete(Integer id) {
        Customer customer = customerDao.getOne(id);
        customer.setLiveDead(LiveDead.STOP);
        customerDao.save(customer);
        return false;
    }

    public List<Customer> search(Customer customer) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Customer> customerExample = Example.of(customer, matcher);
        return customerDao.findAll(customerExample);
    }

    public Customer lastCustomer(){
        return customerDao.findFirstByOrderByIdDesc();
    }
}
