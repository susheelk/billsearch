package tech.susheelkona.billsearch.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tech.susheelkona.billsearch.model.Person;
import tech.susheelkona.billsearch.model.legislation.Ballot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileService {

    @Autowired
    public ObjectMapper objectMapper;

    public boolean ballotExists(int id){
        return new File(getFileName(id)).exists();
    }

    public List<Ballot> getBallotFromDisk(int id) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(getFileName(id))));
        Ballot[] arr = objectMapper.readValue(json, Ballot[].class);
        return Arrays.asList(arr);
    }

    public void persistBallot(int id, List<Ballot> list) throws IOException {
        File file = new File(getFileName(id));
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName(id)));
        String json = objectMapper.writeValueAsString(list);
        writer.write(json);
        writer.close();
    }

    private String getFileName(int id) {
        return "data\\ballots\\vote_"+id+".json";
    }
}
