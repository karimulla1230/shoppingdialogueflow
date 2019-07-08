package es.macero.dev.restexample;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/american-greetings")
public class AmericanGreetingController {

    private List<AmericanGreeting> americanGreetings;

    public AmericanGreetingController() {
    	americanGreetings = new ArrayList<>();
    	americanGreetings.add(new AmericanGreeting("Hola!"));
        americanGreetings.add(new AmericanGreeting("Qué tal?!"));
        americanGreetings.add(new AmericanGreeting("Buenas!"));
    }

    @GetMapping("/{id}")
    public AmericanGreeting getAmericanGreetingById(@PathVariable("id") final int id) {
        return americanGreetings.get(id - 1); // list index starts with 0 but we prefer to start on 1
    }

    @GetMapping("/random")
    public AmericanGreeting getRandom() {
        return americanGreetings.get(new Random().nextInt(americanGreetings.size()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createAmericanGreeting(@RequestBody AmericanGreeting americanGreeting) {
    	americanGreetings.add(americanGreeting);
    }
}
