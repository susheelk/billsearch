package tech.susheelkona.billsearch.services;

import tech.susheelkona.billsearch.model.legislation.Ballot;
import tech.susheelkona.billsearch.model.legislation.Vote;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import java.util.List;

public interface VoteService extends Updatable{
    CachedEntity<Vote> getAll() throws Exception;
    Vote getById(int id) throws Exception;
    List<Ballot> getBallotForVote(int id) throws Exception;
}
