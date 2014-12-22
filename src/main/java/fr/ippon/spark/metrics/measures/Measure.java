package fr.ippon.spark.metrics.measures;

import org.joda.time.DateTime;

import java.io.Serializable;

public abstract class Measure implements Serializable {

    private String name;
    private String metric;
    private String timestamp;

    public Measure(String name, String metric) {
        this.name = name;
        this.metric = metric;
        this.timestamp = DateTime.now().toString();
    }

    public String getName() {
        return name;
    }

    public String getMetric() {
        return metric;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
