package org.tartur.voti.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * An instance of this class is a repository for votes
 * User: user
 * Date: 18/02/12
 * Time: 21:02
 */
@Scope("singleton")
public class Votes {
    private final Logger logger = LoggerFactory.getLogger(Votes.class);

    private final LinkedList<Vote> votes;

    public Votes() {
        votes = new LinkedList<Vote>();
    }

    public void add(Vote vote) {
        votes.add(vote);
        logger.trace("New vote added : {}", vote);
    }

    public int voteNumber() {
        return votes.size();
    }

    public Map<Candidate, Integer> voteResult() {
        Map<Candidate, Integer> result = new HashMap<Candidate, Integer>();
        for (Vote v : votes) {
            Integer last = result.get(v.getCandidate());
            result.put(v.getCandidate(), last == null ? 1 : last + 1);
        }
        return result;
    }
}
