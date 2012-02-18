package org.tartur.voti.data;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class that models a voter
 * User: user
 * Date: 18/02/12
 * Time: 16:24
 */
public class Voter extends Person {
    private AtomicBoolean voted;

    public Voter(String name, String lastName, String nationalId) {
        super(name, lastName, nationalId);
        voted = new AtomicBoolean(false);
    }

    public boolean hasVoted() {
        return voted.get();
    }

    public Vote vote(Vote vote) throws HasAlreadyVotedException {
        if (!voted.compareAndSet(false, true))
            throw new HasAlreadyVotedException();
        return vote;
    }
}
