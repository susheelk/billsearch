package tech.susheelkona.billsearch.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.susheelkona.billsearch.controllers.utils.PropertyIncluder;
import tech.susheelkona.billsearch.services.MpService;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Susheel Kona
 */
@RestController
@RequestMapping("/cabinet")
@CrossOrigin
public class MpsController {

    @Autowired
    private MpService mpService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getCabinet(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) int size,
            @RequestParam(value = "include", defaultValue = "all", required = false) String[] include
    ) throws JsonProcessingException {
        try {
            CachedEntity cachedData = mpService.getCabinetMembers();
            PropertyIncluder includerFilter = new PropertyIncluder(include, cachedData.getPaginatedRespone(size, page));
            return new ResponseEntity<>(includerFilter.serialize(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
