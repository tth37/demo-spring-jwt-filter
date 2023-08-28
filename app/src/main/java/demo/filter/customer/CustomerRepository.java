package demo.filter.customer;

import demo.filter.customer.entities.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>, PagingAndSortingRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);

    Page<CustomerEntity> findByNameContaining(String name, Pageable pageable);
}