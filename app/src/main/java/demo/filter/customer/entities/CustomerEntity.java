package demo.filter.customer.entities;

import com.alibaba.fastjson.JSON;
import com.password4j.Hash;
import com.password4j.Password;
import demo.filter.customer.dto.CustomerDto;
import demo.filter.customer.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    /**
     * Encrypted using BCryptPasswordEncoder
     */
    private String password;

    /**
     * JSON format, String array of roles
     */
    private String roles;

    public CustomerEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        Hash hash = Password.hash(password).withBcrypt();
        this.password = hash.getResult();
    }

    public boolean comparePassword(String password) {
        return Password.check(password, this.password).withBcrypt();
    }

    private List<Role> getRolesJSON() {
        List<Role> res = JSON.parseArray(roles, Role.class);
        if (res == null) {
            return new ArrayList<>();
        }
        return res;
    }

    private void setRolesJSON(List<Role> roles) {
        this.roles = JSON.toJSONString(Role.toStringList(roles));
    }

    public void addRole(Role role) {
        List<Role> roles = getRolesJSON();
        roles.add(role);
        setRolesJSON(roles);
    }

    public void removeRole(Role role) {
        List<Role> roles = getRolesJSON();
        roles.remove(role);
        setRolesJSON(roles);
    }

    public List<Role> getRoles() {
        return getRolesJSON();
    }

    public CustomerDto toDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.id = this.id;
        customerDto.name = this.name;
        customerDto.email = this.email;
        customerDto.roles = this.getRoles();
        return customerDto;
    }
}