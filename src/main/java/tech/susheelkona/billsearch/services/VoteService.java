package tech.susheelkona.billsearch.services;

import tech.susheelkona.billsearch.model.legislation.Vote;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

public interface VoteService extends Updatable{
    CachedEntity<Vote> getAll() throws Exception;
}
