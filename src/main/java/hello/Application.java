package hello;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@Autowired CustomerRepository customerRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cust")
	public Iterable<Customer> find(@QuerydslPredicate(root = Customer.class) Predicate predicate, Pageable pageable,
			@RequestParam MultiValueMap<String, String> parameters) {

		System.out.println();
		System.out.println(predicate);
		System.out.println();

		return customerRepository.findAll(predicate, pageable);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}
