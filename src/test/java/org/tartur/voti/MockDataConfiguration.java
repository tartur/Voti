package org.tartur.voti;

import org.springframework.context.annotation.Bean;
import org.tartur.voti.data.Candidates;
import org.tartur.voti.data.Voters;
import org.tartur.voti.data.Votes;

import static org.mockito.Mockito.mock;

/**
 * Configuration class that uses mock data instances
 * User: user
 * Date: 19/02/12
 * Time: 02:22
 */
public class MockDataConfiguration {

    @Bean
    public Voters voters() {
        return mock(Voters.class);
    }

    @Bean
    public Candidates candidates() {
        return mock(Candidates.class);
    }

    @Bean
    public Votes votes() {
        return mock(Votes.class);
    }

    @Bean
    public VotingService votingService() {
        return new VotingService();
    }

    @Bean
    public RegistrationService registrationService() {
        return new RegistrationService();
    }
}
