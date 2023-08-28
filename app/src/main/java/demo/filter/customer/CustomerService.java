package demo.filter.customer;

import demo.filter.customer.entities.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<CustomerEntity> findAll(int page, int size) {
        return customerRepository.findAll(PageRequest.of(page, size));
    }
}
