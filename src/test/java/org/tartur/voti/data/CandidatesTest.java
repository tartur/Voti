package org.tartur.voti.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tartur.voti.config.BasicConfiguration;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for Canddidates class
 * User: user
 * Date: 16/02/12
 * Time: 00:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BasicConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CandidatesTest {
    public static final int NUM_ELEMENTS = 15;
    private final Candidate TEST_CANDIDATE = new Candidate("Salah", "Testira", "12112121");
    @Autowired
    private Candidates sut;
    @Autowired
    private Candidates second;

    @Test
    public void candidates_is_a_singleton() throws Exception {
        assertThat(sut).isSameAs(second);
    }

    @Test
    public void can_register_a_candidate() throws Exception {
        sut.register(TEST_CANDIDATE);

        assertThat(sut.getCandidates()).isNotNull().containsOnly(TEST_CANDIDATE);
    }

    @Test
    public void can_unregister_a_candidate() throws Exception {
        sut.register(TEST_CANDIDATE);

        sut.unregister(TEST_CANDIDATE);

        assertThat(sut.getCandidates()).isNotNull().isEmpty();
    }

    @Test
    public void should_register_the_same_candidate_once() throws Exception {
        for (int i = 0; i < NUM_ELEMENTS; i++)
            sut.register(TEST_CANDIDATE);

        assertThat(sut.getCandidates()).hasSize(1);
    }

    @Test
    @Repeat(5)
    public void register_is_threadsafe() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(NUM_ELEMENTS);
        Collection<Callable<Void>> registrations = new ArrayDeque<Callable<Void>>(NUM_ELEMENTS);
        final int waitTimeMillis = (int) (Math.random() * 1000);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            registrations.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    Thread.sleep(waitTimeMillis);
                    sut.register(TEST_CANDIDATE);
                    return null;
                }
            });

        }
        List<Future<Void>> futures = service.invokeAll(registrations);
        for (Future<Void> f : futures) {
            f.get();
        }
        assertThat(sut.getCandidates()).hasSize(1);
    }
}
