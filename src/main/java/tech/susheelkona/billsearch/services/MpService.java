package tech.susheelkona.billsearch.services;

import tech.susheelkona.billsearch.model.CabinetMember;
import tech.susheelkona.billsearch.model.Person;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import java.util.List;

public interface MpService extends Updatable{
    CachedEntity<CabinetMember> getCabinetMembers();
}
