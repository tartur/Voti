package org.tartur.voti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import java.util.LinkedList;

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
}
