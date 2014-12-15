package fr.ippon.measures;

import com.codahale.metrics.Gauge;

public class GaugeMeasure extends Measure {

    private Object value;

    public GaugeMeasure(String name, Gauge gauge) {
        super(name, "gauge");
        this.value = gauge.getValue();
    }

    public Object getValue() {
        return value;
    }
}
