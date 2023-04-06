package net.devious.plugins.zeahrc.overlay;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Getter
public class ZeahRcSession
{
    private String currentTask;
    private Instant startTime;
    @Setter
    public int DarkAltarTrips = 0;


    public void startTimer()
    {
        startTime = Instant.now();
    }

    public String getAltarTrips()
    {
        return String.valueOf(DarkAltarTrips);
    }

    public String getElapsedTime()
    {
        Duration time = Duration.between(startTime, Instant.now());
        return convertSecondsToHMmSs(time.toSeconds());
    }

    public void setCurrentTask(String task)
    {
        currentTask = task;
    }

    private String convertSecondsToHMmSs(long seconds)
    {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }
}
