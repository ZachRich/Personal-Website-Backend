package dev.zacharyrich.personalwebsite.rockclimbing.scheduler;

import dev.zacharyrich.personalwebsite.rockclimbing.service.TickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RssScheduler {
    @Autowired private TickService tickService;

    @Scheduled(fixedRate = 21600000) // Run 4 times a day
    public void scheduleTask() {
        tickService.fetchAndSaveRssData();
    }

}
