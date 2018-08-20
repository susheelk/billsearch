package tech.susheelkona.billsearch.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new File(getBallotFileName(id)).exists();
    }

    public List<Ballot> getBallotsFromDisk(int id) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(getBallotFileName(id))));
        Ballot[] arr = objectMapper.readValue(json, Ballot[].class);
        return Arrays.asList(arr);
    }

    public void persistBallots(int id, List<Ballot> list) throws IOException {
        File file = new File(getBallotFileName(id));
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(getBallotFileName(id)));
        String json = objectMapper.writeValueAsString(list);
        writer.write(json);
        writer.close();
    }

    public boolean voteDescriptionExists(int id){
        return new File(getVoteDescriptionFileName(id)).exists();
    }

    public String getVoteDescriptionFromDisk(int id) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getVoteDescriptionFileName(id))));
    }

    public void persistVoteDescription(int id, String description) throws IOException {
        File file = new File(getVoteDescriptionFileName(id));
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(getVoteDescriptionFileName(id)));
        writer.write(description);
        writer.close();
    }

    private String getBallotFileName(int id) {
        return "data/ballots/vote_"+id+".json";
    }

    private String getVoteDescriptionFileName(int id) {
        return "data/vote_descriptions/vote_"+id+".txt";
    }
}
