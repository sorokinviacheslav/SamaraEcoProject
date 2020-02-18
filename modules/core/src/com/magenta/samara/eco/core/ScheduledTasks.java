package com.magenta.samara.eco.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component(ScheduledTasks.NAME)
@EnableScheduling
public class ScheduledTasks {

    public static final String NAME = "eco_ScheduledTasks";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 50000)
    public void reportCurrentTime() {
        //do nothing
    }
}