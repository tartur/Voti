package org.tartur.voti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * A Test class for Votes class
 * User: user
 * Date: 18/02/12
 * Time: 22:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestConfiguration.class})
public class VotesTest {
    @Autowired
    private Votes sut;
    @Autowired
    private Votes second;
    private Candidate BOURGUIBA = new Candidate("Hbib", "Bourguiba", "1");

    @Test
    public void votes_is_a_singleton() throws Exception {
        assertThat(sut).isSameAs(second);
    }

    @Test
    public void can_add_a_vote() throws Exception {
        sut.add(new Vote(BOURGUIBA));

        assertThat(sut.voteNumber()).isEqualTo(1);
    }
}
