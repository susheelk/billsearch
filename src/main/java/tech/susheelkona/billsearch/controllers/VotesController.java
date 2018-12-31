package tech.susheelkona.billsearch.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.susheelkona.billsearch.controllers.utils.Error;
import tech.susheelkona.billsearch.controllers.utils.PropertyIncluder;
import tech.susheelkona.billsearch.controllers.utils.filters.Filter;
import tech.susheelkona.billsearch.controllers.utils.filters.VotesFilter;
import tech.susheelkona.billsearch.model.legislation.Ballot;
import tech.susheelkona.billsearch.model.legislation.Vote;
import tech.susheelkona.billsearch.services.VoteService;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Susheel Kona
 */
@RestController
@CrossOrigin
@RequestMapping("/votes")
public class VotesController {

    @Autowired
    VoteService voteService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getAll(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "include", defaultValue = "all", required = false) String[] include,
            @RequestParam(value = "mps", defaultValue = "[]", required = false) String[] mps
    ) throws JsonProcessingException {

        try {
            Filter<Vote> voteFilter = new VotesFilter(request);
            CachedEntity<Vote> cachedData = voteService.getAll();
            cachedData.filter(voteFilter);

            if(mps.length != 0){
                List<Vote> votes = new ArrayList<>();
                List<String> mpNames = Arrays.asList(mps);
                mpNames.forEach(str -> str.replace("+", " "));
                for(Vote vote: cachedData.getData()){
                    List<Ballot> ballots = voteService.getBallotForVote(vote.getId());
                    List<Ballot> neededBallots = new ArrayList<>();
                    for (Ballot ballot: ballots) {
                        if (mpNames.contains(ballot.getName())){
                            neededBallots.add(ballot);
                        }
                    }
                    vote.setBallots(neededBallots);
                    if (neededBallots.size() == mpNames.size()){
                        votes.add(vote);
                    }
                }
                cachedData.setData(votes);
            }

            String[] exclude = {};
            PropertyIncluder includerFilter = new PropertyIncluder(include, exclude, cachedData.getPaginatedRespone(size, page));

            return new ResponseEntity<String>(includerFilter.serialize(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error(e.getStackTrace().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getOne(@PathVariable int id) throws JsonProcessingException {
        FilterProvider filters = new SimpleFilterProvider().addFilter("includer", SimpleBeanPropertyFilter.serializeAllExcept(Collections.emptySet()));
        try {
            return ResponseEntity.ok(objectMapper.writer(filters).writeValueAsString(voteService.getById(id)));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
