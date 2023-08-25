package demo.filter.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class RequestCustomer {
    private final Long id;
    private final List<String> roles;

    public RequestCustomer(Long id, List<String> roles) {
        this.id = id;
        this.roles = roles;
    }

    public void authenticate() {
        if (this.id == 0L) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid/Empty token");
        }
    }

    public void authenticate(String role) {
        this.authenticate();
        if (!this.hasRole(role)) {
            this.throwMissingRoleException(role);
        }
    }

    public boolean hasRole(String role) {
        if (this.roles == null) {
            return false;
        } else {
            return this.roles.contains(role);
        }
    }

    public boolean idEquals(Long id) {
        return this.id.equals(id);
    }

    public void throwMissingRoleException(String role) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Require role: " + role);
    }

    public Long getId() {
        return id;
    }
}
