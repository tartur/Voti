package org.tartur.voti;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * This class instance holds all candidates instances
 * User: user
 * Date: 16/02/12
 * Time: 00:58
 * To change this template use File | Settings | File Templates.
 */
public class Candidates {

    private final Collection<Candidate> candidates;

    public Candidates() {
        candidates = new HashSet<Candidate>();
    }

    public synchronized void register(Candidate candidate) {
        candidates.add(candidate);
    }

    public Collection<Candidate> getCandidates() {
        return Collections.unmodifiableCollection(candidates);
    }

    public synchronized void unregister(Candidate candidate) {
        candidates.remove(candidate);
    }
}
