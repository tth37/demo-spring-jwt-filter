package demo.filter.customer.enums;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    SUPER_ADMIN,
    CUSTOMER_ADMIN,
    ENROLLMENT_ADMIN,
    LIBRARY_ADMIN;

    public static List<String> toStringList(List<Role> roles) {
        List<String> result = new ArrayList<>();
        for (Role role : roles) {
            result.add(role.toString());
        }
        return result;
    }
}
