package org.tartur.voti.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tartur.voti.config.BasicConfiguration;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * A Test class for Votes class
 * User: user
 * Date: 18/02/12
 * Time: 22:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BasicConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VotesTest {
    @Autowired
    private Votes sut;
    @Autowired
    private Votes second;

    private final Candidate BOURGUIBA = new Candidate("Habib", "Bourguiba", "1");
    private final Candidate CHIRAC = new Candidate("Jacques", "Chirac", "2");
    private final Candidate CLINTON = new Candidate("Bill", "Clinton", "3");

    @Test
    public void votes_is_a_singleton() throws Exception {
        assertThat(sut).isSameAs(second);
    }

    @Test
    public void can_add_a_vote() throws Exception {
        assertThat(sut.voteNumber()).isZero();

        sut.add(new Vote(BOURGUIBA));

        assertThat(sut.voteNumber()).isEqualTo(1);
    }

    @Test
    public void return_live_statistics_about_votes() throws Exception {
        int bourguibaCounter = 0;
        int chiracCounter = 0;
        int clintonCounter = 0;
        for (int i = 0; i < 100; i++) {
            Candidate candidate = candidateFor(Math.random());
            sut.add(new Vote(candidate));
            if (candidate == BOURGUIBA)
                bourguibaCounter++;
            if (candidate == CHIRAC)
                chiracCounter++;
            if (candidate == CLINTON)
                clintonCounter++;
        }

        assertThat(sut.voteNumber()).isEqualTo(100);
        assertThat(bourguibaCounter + clintonCounter + chiracCounter).isEqualTo(100);
        Map<Candidate, Integer> actual = sut.voteResult();
        assertThat(actual).hasSize(3);
        assertThat(actual.get(BOURGUIBA)).isEqualTo(bourguibaCounter);
        assertThat(actual.get(CLINTON)).isEqualTo(clintonCounter);
        assertThat(actual.get(CHIRAC)).isEqualTo(chiracCounter);
    }

    private Candidate candidateFor(double value) {
        Candidate c = null;
        int modulated = (int) (value * 100) % 3;
        switch (modulated) {
            case 0:
                c = BOURGUIBA;
                break;
            case 1:
                c = CLINTON;
                break;
            case 2:
                c = CHIRAC;
                break;
        }
        return c;
    }
}
