package org.tartur.voti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test class for Voter class
 * User: user
 * Date: 18/02/12
 * Time: 17:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestConfiguration.class})
public class VoterTest {

    public static final int THREAD_NB = Runtime.getRuntime().availableProcessors();

    @Test
    public void new_voter_has_not_voted() throws Exception {
        Voter voter = new Voter("Mohamed", "Saleh", "112231213");
        assertThat(voter.hasVoted()).isFalse();
    }

    @Test
    public void a_voter_can_vote() throws Exception {
        Voter voter = new Voter("Mohamed", "Saleh", "112231213");

        Vote expected = new Vote(new Candidate("Ali", "Saleh", "12"));
        Vote vote = voter.vote(expected);

        assertThat(voter.hasVoted()).isTrue();
        assertThat(vote).isSameAs(expected);
    }

    @Test(expected = HasAlreadyVotedException.class)
    public void a_voter_can_vote_only_once() throws Exception {
        Voter voter = new Voter("Mohamed", "Saleh", "112231213");

        voter.vote(new Vote(new Candidate("Ali", "Saleh", "12")));
        voter.vote(new Vote(new Candidate("Carcouba", "Saleh", "12")));
    }

    @Test
    @Repeat(5)
    public void register_is_threadsafe() throws Exception {
        final Voter voter = new Voter("Mohamed", "Saleh", "112231213");
        final Vote vote = new Vote(new Candidate("C", "C", "ccc"));
        ExecutorService service = Executors.newFixedThreadPool(THREAD_NB);
        Collection<Callable<Vote>> registrations = new ArrayDeque<Callable<Vote>>(THREAD_NB);
        final int waitTimeMillis = (int) (Math.random() * 1000);
        for (int i = 0; i < THREAD_NB; i++) {
            registrations.add(new Callable<Vote>() {
                @Override
                public Vote call() throws Exception {
                    Thread.sleep(waitTimeMillis);
                    return voter.vote(vote);
                }
            });

        }
        List<Future<Vote>> futures = service.invokeAll(registrations);
        List<Vote> votes = new ArrayList<Vote>();
        for (Future<Vote> f : futures) {
            try {
                Vote result = f.get();
                votes.add(result);
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        assertThat(votes).hasSize(1);
    }
}
