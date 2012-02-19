package org.tartur.voti;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tartur.voti.data.*;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * A Test class for VotingService
 * User: user
 * Date: 18/02/12
 * Time: 23:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockDataConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VotingServiceTest {
    // candidates
    private final Candidate BOURGUIBA = new Candidate("Habib", "Bourguiba", "1");
    private final Candidate CHIRAC = new Candidate("Jacques", "Chirac", "2");
    private final Candidate CLINTON = new Candidate("Bill", "Clinton", "3");

    // voters
    private final Voter ABDOU = new Voter("Abdallah", "Mistiri", "33");

    @Autowired
    private VotingService sut;
    @Autowired
    private ApplicationContext ctx;

    // Mocks
    private Candidates candidates;
    private Voters voters;
    private Votes votes;

    @Before
    public void setUp() throws Exception {
        candidates = ctx.getBean(Candidates.class);
        voters = ctx.getBean(Voters.class);
        votes = ctx.getBean(Votes.class);
    }

    @Test
    public void can_list_all_candidates() throws Exception {
        when(candidates.getCandidates()).thenReturn(Arrays.asList(BOURGUIBA, CHIRAC, CLINTON));

        Collection<Candidate> allCandidates = sut.getCandidates();

        assertThat(allCandidates).hasSize(3).contains(BOURGUIBA, CLINTON, CHIRAC);
    }

    @Test
    public void can_find_voter_by_id() throws Exception {
        when(voters.findById(ABDOU.getId())).thenReturn(ABDOU);

        Voter actual = sut.findVoter(ABDOU.getId());

        assertThat(actual).isNotNull().isEqualTo(ABDOU);
    }

    @Test(expected = VoterNotRegisteredException.class)
    public void throw_error_when_voter_does_not_exist() throws Exception {
        when(voters.findById(anyString())).thenReturn(null);

        Voter actual = sut.findVoter(ABDOU.getId());
    }

    @Test
    @Rollback(false)
    public void voter_could_vote_for_the_first_time() throws Exception {
        Vote expectedVote = new Vote(BOURGUIBA);
        Voter voter = mock(Voter.class);

        sut.vote(voter, expectedVote);

        verify(voter).vote(expectedVote);
        verify(votes).add(expectedVote);
    }

    @Test(expected = HasAlreadyVotedException.class)
    @Rollback(true)
    public void voter_could_not_vote_twice() throws Exception {
        Voter voter = mock(Voter.class);
        when(voter.vote(any(Vote.class))).thenThrow(new HasAlreadyVotedException());
        Vote expectedVote = new Vote(BOURGUIBA);

        sut.vote(voter, expectedVote);
    }
}
