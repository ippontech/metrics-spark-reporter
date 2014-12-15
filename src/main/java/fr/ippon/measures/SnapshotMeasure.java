package fr.ippon.measures;

import com.codahale.metrics.Snapshot;

public abstract class SnapshotMeasure extends Measure {

    private Long max;
    private Long min;
    private Double mean;
    private Double median;
    private Double p75;
    private Double p95;
    private Double p98;
    private Double p99;
    private Double p999;
    private Double stdDev;


    public SnapshotMeasure(String name, String metric, Snapshot snapshot) {
        super(name, metric);
        this.max = snapshot.getMax();
        this.min = snapshot.getMin();
        this.mean = snapshot.getMean();
        this.median = snapshot.getMedian();
        this.p75 = snapshot.get75thPercentile();
        this.p95 = snapshot.get95thPercentile();
        this.p98 = snapshot.get98thPercentile();
        this.p99 = snapshot.get99thPercentile();
        this.p999 = snapshot.get999thPercentile();
        this.stdDev = snapshot.getStdDev();
    }

    public Long getMax() {
        return max;
    }

    public Long getMin() {
        return min;
    }

    public Double getMean() {
        return mean;
    }

    public Double getMedian() {
        return median;
    }

    public Double getP75() {
        return p75;
    }

    public Double getP95() {
        return p95;
    }

    public Double getP98() {
        return p98;
    }

    public Double getP99() {
        return p99;
    }

    public Double getP999() {
        return p999;
    }

    public Double getStdDev() {
        return stdDev;
    }
}
