package org.tartur.voti;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Unit Test Spring configuration class
 * User: user
 * Date: 18/02/12
 * Time: 22:20
 */
@Configuration
public class UnitTestConfiguration {

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
}
