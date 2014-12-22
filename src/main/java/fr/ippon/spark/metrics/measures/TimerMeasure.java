package fr.ippon.spark.metrics.measures;

import com.codahale.metrics.Timer;

public class TimerMeasure extends SnapshotMeasure {

    private Long count;
    private Double m1Rate;
    private Double m5Rate;
    private Double m15Rate;
    private Double meanRate;

    public TimerMeasure(String name, Timer timer) {
        super(name, "timer", timer.getSnapshot());
        this.count = timer.getCount();
        this.m1Rate = timer.getOneMinuteRate();
        this.m5Rate = timer.getFiveMinuteRate();
        this.m15Rate = timer.getFifteenMinuteRate();
        this.meanRate = timer.getMeanRate();
    }

    public Long getCount() {
        return count;
    }

    public Double getM1Rate() {
        return m1Rate;
    }

    public Double getM5Rate() {
        return m5Rate;
    }

    public Double getM15Rate() {
        return m15Rate;
    }

    public Double getMeanRate() {
        return meanRate;
    }
}
