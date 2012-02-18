package org.tartur.voti;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tartur.voti.config.BasicConfiguration;
import org.tartur.voti.data.*;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

/**
 * A Test class for VotingService
 * User: user
 * Date: 18/02/12
 * Time: 23:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BasicConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VotingServiceTest {
    // candidates
    private final Candidate BOURGUIBA = new Candidate("Habib", "Bourguiba", "1");
    private final Candidate CHIRAC = new Candidate("Jacques", "Chirac", "2");
    private final Candidate CLINTON = new Candidate("Bill", "Clinton", "3");

    // voters
    private final Voter HAMMA = new Voter("Mohammed", "Saleh", "11");
    private final Voter HMED = new Voter("Ahmed", "Karkni", "22");
    private final Voter ABDOU = new Voter("Abdallah", "Mistiri", "33");
    private final Voter GATTOUSSA = new Voter("John", "Martin", "44ff");

    @Autowired
    private VotingService sut;
    @Autowired
    private Candidates candidates;
    @Autowired
    private Voters voters;
    @Autowired
    private Votes votes;

    @Before
    public void setUp() throws Exception {
        candidates.register(BOURGUIBA);
        candidates.register(CHIRAC);
        candidates.register(CLINTON);
        voters.register(HAMMA);
        voters.register(HMED);
        voters.register(ABDOU);
    }

    @Test
    public void can_list_all_candidates() throws Exception {
        Collection<Candidate> allCandidates = sut.getCandidates();
        assertThat(allCandidates).hasSize(3).contains(BOURGUIBA, CLINTON, CHIRAC);
    }

    @Test
    public void can_find_voter_by_id() throws Exception {
        Voter actual = sut.findVoter(ABDOU.getId());

        assertThat(actual).isNotNull().isEqualTo(ABDOU);
    }

    @Test(expected = VoterNotRegisteredException.class)
    public void throw_error_when_voter_does_not_exist() throws Exception {
        Voter actual = sut.findVoter(GATTOUSSA.getId());
    }

    @Test
    @Rollback(false)
    public void voter_could_vote_for_the_first_time() throws Exception {
        assertThat(votes.voteNumber()).isZero();

        Vote expectedVote = new Vote(BOURGUIBA);
        sut.vote(HAMMA, expectedVote);

        assertThat(votes.voteNumber()).isEqualTo(1);
    }

    @Test(expected = HasAlreadyVotedException.class)
    @Rollback(true)
    public void voter_could_not_vote_twice() throws Exception {
        Vote expectedVote = new Vote(BOURGUIBA);
        HAMMA.vote(expectedVote);
        sut.vote(HAMMA, expectedVote);
    }
}
