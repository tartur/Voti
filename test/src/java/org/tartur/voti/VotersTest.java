package org.tartur.voti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Unit Tests for Voters class
 * User: user
 * Date: 16/02/12
 * Time: 00:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VotersTest {
    public static final int NUM_ELEMENTS = 15;
    private final Voter TEST_VOTER = new Voter("Salah", "Testira", "12112121");
    @Autowired
    private Voters sut;

    @Test
    public void voters_is_a_singleton() throws Exception {
        assertThat(sut).isSameAs(second);
    }

    @Autowired
    private Voters second;

    @Test
    public void can_register_a_voter() throws Exception {
        assertThat(sut.isRegistered(TEST_VOTER)).isFalse();

        sut.register(TEST_VOTER);

        assertThat(sut.isRegistered(TEST_VOTER)).as("Registered voter").isTrue();
        assertThat(sut.votersNumber()).isEqualTo(1);
    }

    @Test
    public void should_register_the_same_voter_once() throws Exception {
        for (int i = 0; i < NUM_ELEMENTS; i++)
            sut.register(TEST_VOTER);

        assertThat(sut.votersNumber()).isEqualTo(1);
    }


    @Test
    public void can_unregister_a_voter() throws Exception {
        sut.register(TEST_VOTER);
        assertThat(sut.isRegistered(TEST_VOTER)).isTrue();

        sut.unregister(TEST_VOTER);

        assertThat(sut.isRegistered(TEST_VOTER)).as("Unregistered voter").isFalse();
        assertThat(sut.votersNumber()).isZero();
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
                    sut.register(TEST_VOTER);
                    return null;
                }
            });

        }
        List<Future<Void>> futures = service.invokeAll(registrations);
        for (Future<Void> f : futures) {
            f.get();
        }
        assertThat(sut.votersNumber()).isEqualTo(1);
    }

    @Test
    public void can_find_voter_by_nationalId() throws Exception {
        sut.register(new Voter("Saleh", "Ali", "123"));
        sut.register(new Voter("Mohamed", "Boughnim", "1234"));
        Voter expected = new Voter("Nejib", "Khattab", "12345");
        sut.register(expected);
        sut.register(new Voter("Faouzi", "Rouissi", "123456"));

        Voter voter = sut.findById("12345");

        assertThat(voter).isEqualTo(expected);
    }
}
