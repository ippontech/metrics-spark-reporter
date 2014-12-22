package fr.ippon.spark.metrics.measures;

import com.codahale.metrics.Metered;

public class MeterMeasure extends Measure {

    private Long count;
    private Double m1Rate;
    private Double m5Rate;
    private Double m15Rate;
    private Double meanRate;

    public MeterMeasure(String name, Metered meter) {
        super(name, "meter");
        this.count = meter.getCount();
        this.m1Rate = meter.getOneMinuteRate();
        this.m5Rate = meter.getFiveMinuteRate();
        this.m15Rate = meter.getFifteenMinuteRate();
        this.meanRate = meter.getMeanRate();
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
