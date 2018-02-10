package tech.susheelkona.billsearch.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.springframework.http.MediaType;
import tech.susheelkona.billsearch.controllers.utils.PaginatedResponse;
import tech.susheelkona.billsearch.model.legislation.Bill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.susheelkona.billsearch.controllers.utils.Error;
import tech.susheelkona.billsearch.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

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

    @GetMapping(value = "/bills", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getAll(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "25", required = false) int size
    ) throws JsonProcessingException {

        try {
            return new ResponseEntity<PaginatedResponse<Bill>>(billService.getAll().getPaginatedRespone(size, page), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/bills/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getOne(@PathVariable String id) throws JsonProcessingException {
        try {
            Bill bill = billService.getAll().getData().get(0);
            return ResponseEntity.ok(bill);
        } catch (NullPointerException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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

