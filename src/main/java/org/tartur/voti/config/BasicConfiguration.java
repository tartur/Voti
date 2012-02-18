package org.tartur.voti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tartur.voti.VotingService;
import org.tartur.voti.data.Candidates;
import org.tartur.voti.data.Voters;
import org.tartur.voti.data.Votes;

/**
 * Main configuration file
 * User: user
 * Date: 19/02/12
 * Time: 00:30
 */
@Configuration
public class BasicConfiguration {

    @Bean
    public Voters voters() {
        return new Voters();
    }

    @Bean
    public Candidates candidates() {
        return new Candidates();
    }

    @Bean
    public Votes votes() {
        return new Votes();
    }

    @Bean
    public VotingService votingService() {
        return new VotingService();
    }
}
