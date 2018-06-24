package tech.susheelkona.billsearch.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import tech.susheelkona.billsearch.controllers.utils.PaginatedResponse;
import tech.susheelkona.billsearch.controllers.utils.PropertyIncluder;
import tech.susheelkona.billsearch.model.legislation.Bill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.susheelkona.billsearch.controllers.utils.Error;
import tech.susheelkona.billsearch.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Susheel Kona
 */
@RestController
@CrossOrigin
@RequestMapping("/bills")
public class BillController {

    @Autowired
    BillService billService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getAll(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "includes[]", defaultValue = "number,title,session,dateIntroduced") String[] include
    ) throws JsonProcessingException {
        try {
//            String ending = request.getRequestURI().substring();
            PaginatedResponse<Bill> paginatedRespone = billService.getAll().getPaginatedRespone(size, page);
            PropertyIncluder includerFilter = new PropertyIncluder(include, paginatedRespone);

            return new ResponseEntity<String>(includerFilter.serialize(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getOne(@PathVariable int id) throws JsonProcessingException {
        Error error;
        try {
            Bill bill = billService.getAll().getData().stream().filter(bill1 -> bill1.getId()==id).collect(Collectors.toList()).get(0);
            return ResponseEntity.ok(bill);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new Error("Not found!"), HttpStatus.NOT_FOUND);
        }
        catch (NullPointerException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** For debug purposes
     *
     */
    @GetMapping("/update")
    private void update(){
        try {
            billService.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getUrlWithoutAddress(String url) throws URISyntaxException {
        URI uri = new URI(url);
    }


}

