package org.tartur.voti;

import org.joda.time.DateTime;

/**
 * Each instance is vote made by a voter
 * User: user
 * Date: 18/02/12
 * Time: 17:46
 */
public class Vote {
    private final Candidate candidate;
    private final DateTime time;

    public Vote(Candidate candidate) {
        this.candidate = candidate;
        this.time = new DateTime();
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public DateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "candidate=" + candidate +
                ", time=" + time +
                '}';
    }
}
