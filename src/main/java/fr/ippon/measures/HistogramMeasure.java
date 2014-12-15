package fr.ippon.measures;

import com.codahale.metrics.Histogram;

public class HistogramMeasure extends SnapshotMeasure {

    private Long count;

    public HistogramMeasure(String name, Histogram histogram) {
        super(name, "histogram", histogram.getSnapshot());
        this.count = histogram.getCount();
    }

    public Long getCount() {
        return count;
    }
}
