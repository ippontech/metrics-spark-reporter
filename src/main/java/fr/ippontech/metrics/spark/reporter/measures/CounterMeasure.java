package fr.ippontech.metrics.spark.reporter.measures;

import com.codahale.metrics.Counter;

public class CounterMeasure extends Measure {

    private Long count;

    public CounterMeasure(String name, Counter counter) {
        super(name, "counter");
        this.count = counter.getCount();
    }

    public Long getCount() {
        return count;
    }
}
