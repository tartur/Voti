package org.tartur.voti;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tartur.voti.config.BasicConfiguration;

/**
 * Voting application main class
 * User: user
 * Date: 19/02/12
 * Time: 00:33
 * To change this template use File | Settings | File Templates.
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BasicConfiguration.class);
        VotingService votingService = ctx.getBean(VotingService.class);
    }
}
