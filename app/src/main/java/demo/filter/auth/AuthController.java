package demo.filter.auth;

import demo.filter.auth.dto.JwtTokenDto;
import demo.filter.auth.dto.LoginDto;
import demo.filter.customer.CustomerService;
import demo.filter.customer.entities.CustomerEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Authentication", description = "Authentication API")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/hello")
    public @ResponseBody String hello(@RequestParam(name = "name") String name,
                                      @RequestAttribute("reqCustomer") RequestCustomer reqCustomer) {
        reqCustomer.authenticate();
        return "Hello World";
    }

    @Operation(summary = "Login with email and password", description = "Login with email and password", security = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login success", content = {
                    @Content(schema = @Schema(implementation = JwtTokenDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Invalid password"),
            @ApiResponse(responseCode = "404", description = "Customer Email Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(
            @Parameter(description = "Login Credentials", required = true)
            @Valid
            @RequestBody LoginDto loginDto) {
        System.out.println("loginDto: " + loginDto.password);
        String email = loginDto.email;
        String password = loginDto.password;

        CustomerEntity customer = customerService.findByEmail(email);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Email Not Found");
        }
        if (!customer.comparePassword(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.token = jwtUtils.generateJwtToken(customer.getId(), customer.getRoles());

        return ResponseEntity.ok(jwtTokenDto);
    }

}