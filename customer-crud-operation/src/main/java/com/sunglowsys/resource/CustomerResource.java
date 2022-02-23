package com.sunglowsys.resource;

import com.sunglowsys.domain.Customer;
import com.sunglowsys.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ModelAndView home() {
        log.debug("REST Request to getAll customers");
        Page<Customer> page = customerService.findAll(PageRequest.of(0,20));
        return new ModelAndView("index","customers", page.getContent());
    }

    @GetMapping("/customers")
    public ModelAndView createCustomerForm(){
        log.debug("REST request to load Customer form:");
        return new ModelAndView("add-customer","customer", new Customer());
    }

    @PostMapping("/customers")
    public String saveCustomers(@ModelAttribute("customer") Customer customer){
        log.debug("REST Request to save Customers: {} ", customer);
        customerService.save(customer);
        return "redirect:/";
    }


    @GetMapping("/customers/{id}")
    public ModelAndView updateCustomer(@PathVariable Long id){
        log.debug("REST Request to update customer: {}",id);
        return new ModelAndView("add-customer","customer", customerService.findById(id));
    }


    @GetMapping("/customers/create")
    public String createCustomerForm(@ModelAttribute Customer customer) {
        log.debug("REST Request to find Customer ById: {} ", customer);
        customerService.save(customer);
        return "redirect:/";
    }

    @DeleteMapping("/customer/delete{id}")
    public String deleteCustomerById(@PathVariable Long id) {
        log.debug("REST Request to delete customer: {}",id);
       customerService.delete(id);
        return "redirect:/";
    }
}
