package demo.filter.auth;

import demo.filter.customer.CustomerEntity;
import demo.filter.customer.CustomerRepository;
import demo.filter.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/hello")
    public @ResponseBody String hello(@RequestAttribute("reqCustomer") RequestCustomer reqCustomer) {
        return "Hello World";
    }

    @GetMapping("/helloAuthenticated")
    public @ResponseBody String helloAuthenticated(@RequestAttribute("reqCustomer") RequestCustomer reqCustomer) {
        reqCustomer.authenticate();
        return "Hello World Authenticated";
    }

    @GetMapping("/helloSuperAdmin")
    public @ResponseBody String helloSuperAdmin(@RequestAttribute("reqCustomer") RequestCustomer reqCustomer) {
        reqCustomer.authenticate("SUPER_ADMIN");
        return "Hello World Super Admin";
    }

    @GetMapping("/helloSuperSuperAdmin")
    public @ResponseBody String helloSuperSuperAdmin(@RequestAttribute("reqCustomer") RequestCustomer reqCustomer) {
        reqCustomer.authenticate("SUPER_SUPER_ADMIN");
        return "Hello World Super Super Admin";
    }

    @PostMapping("/login")
    public @ResponseBody String login(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) {
        CustomerEntity customer = customerService.findByEmail(email);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found");
        }
        if (!customer.comparePassword(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
        return jwtUtils.generateJwtToken(customer.getId(), customer.getRoles());
    }

}