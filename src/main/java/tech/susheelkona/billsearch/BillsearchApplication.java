package tech.susheelkona.billsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import tech.susheelkona.billsearch.services.BillService;
import tech.susheelkona.billsearch.services.implementations.LegisinfoBillService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.jws.Oneway;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    @Bean
    public ObjectMapper mapper() {
	    return new ObjectMapper(){{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            setDateFormat(format);
        }};
    }


}
