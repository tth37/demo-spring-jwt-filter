package demo.filter.customer;

import demo.filter.customer.entities.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerEntity findByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
