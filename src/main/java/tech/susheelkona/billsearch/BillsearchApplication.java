package tech.susheelkona.billsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import tech.susheelkona.billsearch.services.BillService;
import tech.susheelkona.billsearch.services.FileService;
import tech.susheelkona.billsearch.services.MpService;
import tech.susheelkona.billsearch.services.VoteService;
import tech.susheelkona.billsearch.services.implementations.LegisinfoBillService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.susheelkona.billsearch.services.implementations.LegisinfoMpService;
import tech.susheelkona.billsearch.services.implementations.LegisinfoVoteService;

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
    public VoteService voteService(){
	    return new LegisinfoVoteService();
    }

    @Bean
    public FileService fileService(){
	    return new FileService();
    }

    @Bean
    public MpService mpService(){
	    return new LegisinfoMpService();
    }

    @Bean
    public ObjectMapper mapper() {
	    return new ObjectMapper(){{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            setDateFormat(format);
        }};
    }


}
