package org.tartur.voti;

/**
 * A test class for RegistrationService class
 * User: user
 * Date: 19/02/12
 * Time: 02:07
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tartur.voti.data.Candidate;
import org.tartur.voti.data.Candidates;
import org.tartur.voti.data.Voter;
import org.tartur.voti.data.Voters;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockDataConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RegistrationServiceTest {
    // Mocks
    private Voters voters;
    private Candidates candidates;
    // System Under Test
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private RegistrationService sut;

    @Before
    public void setUp() throws Exception {
        voters = ctx.getBean(Voters.class);
        candidates = ctx.getBean(Candidates.class);
    }

    @Test
    public void register_new_candidate() throws Exception {
        Candidate expected = new Candidate("Habib", "Bourguiba", "1");
        sut.registerCandidate(expected);

        verify(candidates).register(expected);
        verifyZeroInteractions(voters);
    }

    @Test
    public void register_new_voter() throws Exception {
        Voter expected = new Voter("Hamma", "Hammemi", "2");
        sut.registerVoter(expected);

        verify(voters).register(expected);
        verifyZeroInteractions(candidates);
    }
}
