package org.tartur.voti.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import java.util.Collection;
import java.util.HashSet;

/**
 * This class instances holds voters instances
 * User: user
 * Date: 18/02/12
 * Time: 16:30
 */
@Scope("singleton")
public class Voters {
    private final Logger logger = LoggerFactory.getLogger(Voters.class);
    private final Collection<Voter> voters = new HashSet<Voter>();

    public boolean isRegistered(Voter voter) {
        return voters.contains(voter);
    }

    public synchronized void register(Voter voter) {
        if (voters.add(voter)) {
            logger.info("New voter registered : {}", voter);
        }
    }

    public synchronized void unregister(Voter voter) {
        if (voters.remove(voter)) {
            logger.info("Voter {} was unregistered", voter);
        }
    }

    public int votersNumber() {
        return voters.size();
    }

    public Voter findById(String id) {
        Voter found = null;
        for (Voter v : voters) {
            if (id.equals(v.getId())) {
                found = v;
                break;
            }
        }
        return found;
    }
}
