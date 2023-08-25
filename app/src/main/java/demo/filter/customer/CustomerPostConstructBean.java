package demo.filter.customer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerPostConstructBean {
    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void init() {
        if (customerRepository.count() == 0) {
            CustomerEntity n = new CustomerEntity();
            n.setName("admin");
            n.setEmail("admin@localhost");
            n.setPassword("admin");
            n.addRole("SUPER_ADMIN");
            customerRepository.save(n);
        }
    }
}
