package tech.susheelkona.billsearch;

import org.springframework.scheduling.annotation.EnableScheduling;
import tech.susheelkona.billsearch.services.BillService;
import tech.susheelkona.billsearch.services.implementations.LegisinfoBillService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableScheduling
public class BillsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillsearchApplication.class, args);
    }

    @Bean
    public BillService billService(){
	    return new LegisinfoBillService();
    }

}
