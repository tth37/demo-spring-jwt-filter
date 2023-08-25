package demo.filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name,
    @RequestAttribute(value = "foo") String foo

    ) {
        System.out.println("foo: " + foo);
        return String.format("Hello %s!", name);
    }
}
