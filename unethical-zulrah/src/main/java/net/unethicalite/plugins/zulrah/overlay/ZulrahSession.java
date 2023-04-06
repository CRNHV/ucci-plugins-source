package net.unethicalite.plugins.zulrah.overlay;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
public class ZulrahSession
{
    private int kills = 0;
    private String currentTask;
    private Date lastKillDate;
    private Instant startTime;

    public void addKill()
    {
        if (lastKillDate == null)
        {
            kills += 1;
            lastKillDate = Date.from(Instant.now());
            return;
        }

        Date recentKillDate = Date.from(Instant.now());
        long diff = recentKillDate.getTime() - lastKillDate.getTime();

        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        if (seconds > 30)
        {
            lastKillDate = Date.from(Instant.now());
            kills += 1;
        }
    }

    public void startTimer()
    {
        startTime = Instant.now();
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
