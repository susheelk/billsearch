package tech.susheelkona.billsearch.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.susheelkona.billsearch.controllers.utils.Error;
import tech.susheelkona.billsearch.controllers.utils.PropertyIncluder;
import tech.susheelkona.billsearch.controllers.utils.filters.BillFilter;
import tech.susheelkona.billsearch.controllers.utils.filters.Filter;
import tech.susheelkona.billsearch.model.NewsItem;
import tech.susheelkona.billsearch.model.legislation.Bill;
import tech.susheelkona.billsearch.services.NewsService;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Susheel Kona
 */
@RestController
@CrossOrigin
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getAll(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "include", defaultValue = "number,title,session,dateIntroduced,law,url", required = false) String[] include
    ) throws JsonProcessingException {
        try {
            CachedEntity<NewsItem> cachedData = newsService.getNewsItems();
            PropertyIncluder includerFilter = new PropertyIncluder(include, cachedData.getPaginatedRespone(size, page));
            return new ResponseEntity<String>(includerFilter.serialize(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error(e.getStackTrace().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
