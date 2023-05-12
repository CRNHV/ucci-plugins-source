package net.devious.plugins.template.overlay;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

public class BotSession
{
    @Getter
    @Setter
    private String currentTask;
    private Instant startTime;

    public void startTimer()
    {
        startTime = Instant.now();
    }

    public String getElapsedTime()
    {
        Duration time = Duration.between(startTime, Instant.now());
        return convertSecondsToDHMmSs(time.toSeconds());
    }

    private String convertSecondsToDHMmSs(long seconds)
    {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        long d = seconds / (24 * 60 * 60);
        return String.format("%d:%02d:%02d:%02d", d, h, m, s);
    }
}
