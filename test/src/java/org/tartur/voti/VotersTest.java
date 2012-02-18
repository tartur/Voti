package org.tartur.voti;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Unit Tests for Voters class
 * User: user
 * Date: 16/02/12
 * Time: 00:54
 * To change this template use File | Settings | File Templates.
 */
public class VotersTest {
    private final Voter TEST_VOTER = new Voter("Salah", "Testira", "12112121");
    private Voters sut;

    @Before
    public void setUp() throws Exception {
        sut = new Voters();
    }

    @Test
    public void can_register_a_voter() throws Exception {
        assertThat(sut.isRegistered(TEST_VOTER)).isFalse();

        sut.register(TEST_VOTER);

        assertThat(sut.isRegistered(TEST_VOTER)).as("Registered voter").isTrue();
    }

    @Test
    public void can_unregister_a_voter() throws Exception {
        sut.register(TEST_VOTER);
        assertThat(sut.isRegistered(TEST_VOTER)).isTrue();

        sut.unregister(TEST_VOTER);

        assertThat(sut.isRegistered(TEST_VOTER)).as("Unregistered voter").isFalse();
    }

}
