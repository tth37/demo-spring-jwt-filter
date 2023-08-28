package demo.filter.customer;

import demo.filter.auth.RequestCustomer;
import demo.filter.customer.dto.CustomerDto;
import demo.filter.customer.entities.CustomerEntity;
import demo.filter.utils.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Customer", description = "Customer Management API")
@SecurityRequirement(name = "bearer-token")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Get Customer By Token", description = "Get Customer By JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Customer By Token Success", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Invalid/Empty token"),
            @ApiResponse(responseCode = "404", description = "Customer Not Found")
    })
    @GetMapping("/getCustomerByToken")
    public ResponseEntity<CustomerDto> getCustomerByToken(@RequestAttribute("reqCustomer") RequestCustomer reqCustomer) {
        reqCustomer.authenticate();
        CustomerEntity customerEntity = customerService.findById(reqCustomer.getId());
        if (customerEntity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer Not Found"
            );
        }
        CustomerDto customerDto = customerEntity.toDto();
        return ResponseEntity.ok(customerDto);
    }


    @GetMapping("/getAllCustomers")
    public ResponseEntity<PageDto<CustomerDto>> getAllCustomers(
            @RequestAttribute("reqCustomer") RequestCustomer reqCustomer,
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size
    ) {
        reqCustomer.authenticate();
        Page<CustomerEntity> customerEntities = customerService.findAll(page, size);
        PageDto<CustomerDto> customerDtoPageDto = new PageDto<>(customerEntities).forEach(CustomerEntity::toDto);
        return ResponseEntity.ok(customerDtoPageDto);
    }
}