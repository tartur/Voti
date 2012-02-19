package org.tartur.voti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tartur.voti.data.Candidate;
import org.tartur.voti.data.Candidates;
import org.tartur.voti.data.Voter;
import org.tartur.voti.data.Voters;

/**
 * A service class that handles all registration operations.
 * User: user
 * Date: 19/02/12
 * Time: 02:01
 */
public class RegistrationService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Voters voters;
    @Autowired
    private Candidates candidates;

    public void registerCandidate(Candidate candidate) {
        candidates.register(candidate);
        logger.info("New candidate registered {}", candidate);
    }

    public void registerVoter(Voter voter) {
        voters.register(voter);
        logger.info("New voter registered {}", voter);
    }
}
