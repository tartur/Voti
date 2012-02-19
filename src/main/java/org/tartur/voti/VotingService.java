package org.tartur.voti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.tartur.voti.data.*;

import java.util.Collection;

/**
 * A service class that handles voting operations.
 * This service is the sole to be used after all registrations.
 * User: user
 * Date: 18/02/12
 * Time: 23:36
 */
public class VotingService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Candidates candidates;
    @Autowired
    private Voters voters;
    @Autowired
    private Votes votes;


    public Collection<Candidate> getCandidates() {
        return candidates.getCandidates();
    }

    public Voter findVoter(String id) throws VoterNotRegisteredException {
        logger.info("Fetch voter for id#{}", id);
        Voter found = voters.findById(id);
        if (found == null)
            throw new VoterNotRegisteredException();
        return found;
    }

    @Transactional
    public void vote(final Voter voter, final Vote vote) throws HasAlreadyVotedException {
        logger.info("New vote ...");
        voter.vote(vote);
        votes.add(vote);
    }
}
