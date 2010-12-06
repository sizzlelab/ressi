package servicemonitor.timer;

import java.util.concurrent.Semaphore;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.inject.Inject;

/**
 *
 * @author ktuomain
 */
@Singleton
@Startup
public class DbUpdate {

    @EJB
    IncrementalUpdate iu;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialize() {
        ScheduleExpression expression = new ScheduleExpression();
        expression.second("*/5").minute("*").hour("*");
        timerService.createCalendarTimer(expression);
    }
    private Semaphore semaphore = new Semaphore(1);

    private void incrementalUpdate() {
        //IncrementalUpdate iu = new IncrementalUpdate();
        if (iu.init()) {
            iu.run();
            iu.close();
        }
    }

    @Timeout
    public void execute() {
        long startTime = System.currentTimeMillis();

        //Start update only if there isn't previous update process running
        if (semaphore.tryAcquire()) {
            System.out.println("Starting incremental update: " + startTime);
            incrementalUpdate();
            semaphore.release();
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("Incremental update took " + elapsed + " ms");
        } else {
            System.out.println("Skipping db update because update process is already running");
        }
    }
}
