package org.tartur.voti;

import java.util.Collection;
import java.util.HashSet;

/**
 * This class instances holds voters instances
 * User: user
 * Date: 18/02/12
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public class Voters {

    private final Collection<Voter> voters = new HashSet<Voter>();

    public boolean isRegistered(Voter voter) {
        return voters.contains(voter);
    }

    public void register(Voter voter) {
        voters.add(voter);
    }

    public void unregister(Voter voter) {
        voters.remove(voter);
    }
}
