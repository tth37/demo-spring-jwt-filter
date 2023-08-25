package demo.filter.customer.dto;

import demo.filter.customer.enums.Role;

import java.util.List;

public class CustomerDto {
    public Long id;
    public String name;
    public String email;
    public List<Role> roles;
}
