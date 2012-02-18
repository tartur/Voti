package org.tartur.voti.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

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
@Scope("singleton")
public class Candidates {
    private final Logger logger = LoggerFactory.getLogger(Candidates.class);
    private final Collection<Candidate> candidates;

    public Candidates() {
        candidates = new HashSet<Candidate>();
    }

    public synchronized void register(Candidate candidate) {
        if (candidates.add(candidate)) {
            logger.info("New candidate registered : {}", candidate);
        }
    }

    public Collection<Candidate> getCandidates() {
        return Collections.unmodifiableCollection(candidates);
    }

    public synchronized void unregister(Candidate candidate) {
        if (candidates.remove(candidate)) {
            logger.info("Candidate {} was unregistered", candidate);
        }
    }
}
