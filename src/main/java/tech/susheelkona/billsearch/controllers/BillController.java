package tech.susheelkona.billsearch.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.susheelkona.billsearch.model.legislation.Bill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.susheelkona.billsearch.controllers.utils.Error;
import tech.susheelkona.billsearch.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author Susheel Kona
 */
@RestController
@CrossOrigin
public class BillController {

    @Autowired
    BillService billService;

    @GetMapping("/bills")
    private String getAll() throws JsonProcessingException {

        List<String> list = Arrays.asList("a", "b", "c");

        return new ObjectMapper().writeValueAsString(list);

    }

    @RequestMapping(value = "/bills/{id}", produces = "application/json")
    private ResponseEntity<?> getOne(@PathVariable String id) throws JsonProcessingException {
        try {
            Bill bill = billService.getAll().get(0);
//            throw new Exception("test");
            return ResponseEntity.ok(bill);
        } catch (NullPointerException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bills/update")
    private void update(){
        try {
            billService.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

