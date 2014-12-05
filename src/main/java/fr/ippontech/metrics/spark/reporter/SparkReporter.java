package fr.ippontech.metrics.spark.reporter;

import com.codahale.metrics.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ippontech.metrics.spark.reporter.measures.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

public class SparkReporter extends ScheduledReporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkReporter.class);

    private String sparkHost;
    private int sparkPort;
    private Socket socket;
    private ObjectMapper mapper;
    private PrintWriter writer;

    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    private SparkReporter(MetricRegistry registry, String sparkHost, int sparkPort, TimeUnit rateUnit,
                          TimeUnit durationUnit, MetricFilter filter) {
        super(registry, "spark-reporter", filter, rateUnit, durationUnit);
        this.sparkHost = sparkHost;
        this.sparkPort = sparkPort;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges,
                       SortedMap<String, Counter> counters,
                       SortedMap<String, Histogram> histograms,
                       SortedMap<String, Meter> meters,
                       SortedMap<String, Timer> timers) {
        try {
            connect();
            doReport(gauges, counters, histograms, meters, timers);
        } catch (IOException ioe1) {
            try {
                connect();
                doReport(gauges, counters, histograms, meters, timers);
            } catch (IOException ioe2) {
                LOGGER.warn("Unable to report to Spark : "+ ioe2.getClass().getCanonicalName());
            }
        }
    }

    private void doReport(SortedMap<String, Gauge> gauges,
                          SortedMap<String, Counter> counters,
                          SortedMap<String, Histogram> histograms,
                          SortedMap<String, Meter> meters,
                          SortedMap<String, Timer> timers) throws IOException {

        if (gauges.isEmpty() && counters.isEmpty() && histograms.isEmpty() &&
            meters.isEmpty() && timers.isEmpty()) {
            return;
        }

        if (!gauges.isEmpty()) {
            for (Map.Entry<String, Gauge> entry : gauges.entrySet()) {
                reportGauge(entry.getKey(), entry.getValue());
            }
        }

        if (!counters.isEmpty()) {
            for (Map.Entry<String, Counter> entry : counters.entrySet()) {
                reportCounter(entry.getKey(), entry.getValue());
            }
        }

        if (!histograms.isEmpty()) {
            for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
                reportHistogram(entry.getKey(), entry.getValue());
            }
        }

        if (!meters.isEmpty()) {
            for (Map.Entry<String, Meter> entry : meters.entrySet()) {
                reportMetered(entry.getKey(), entry.getValue());
            }
        }

        if (!timers.isEmpty()) {
            for (Map.Entry<String, Timer> entry : timers.entrySet()) {
                reportTimer(entry.getKey(), entry.getValue());
            }
        }
    }

    private void connect() throws IOException {
        if (writer != null && writer.checkError()) {
            closeConnection();
        }
        if (socket == null) {
            socket = SocketFactory.getDefault().createSocket(sparkHost, sparkPort);
            writer = new PrintWriter(socket.getOutputStream());
        }
    }

    private void closeConnection() throws IOException {
        writer.close();
        socket.close();
        writer = null;
        socket = null;
    }

    private void reportGauge(String name, Gauge gauge) throws IOException {
        if (this.isANumber(gauge.getValue())) {
            writer.println(mapper.writeValueAsString(new GaugeMeasure(name, gauge)));
        }
    }

    private void reportCounter(String name, Counter counter) throws IOException {
        writer.println(mapper.writeValueAsString(new CounterMeasure(name, counter)));
    }

    private void reportHistogram(String name, Histogram histogram) throws IOException {
        writer.println(mapper.writeValueAsString(new HistogramMeasure(name, histogram)));
    }

    private void reportMetered(String name, Metered meter) throws IOException {
        writer.println(mapper.writeValueAsString(new MeterMeasure(name, meter)));
    }

    private void reportTimer(String name, Timer timer) throws IOException {
        writer.println(mapper.writeValueAsString(new TimerMeasure(name, timer)));
    }

    private boolean isANumber(Object object) {
        if (object instanceof Float || object instanceof  Double ||
            object instanceof Integer || object instanceof Long) {
            return true;
        } else {
            return false;
        }
    }

    public static class Builder {

        private final MetricRegistry registry;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private MetricFilter filter;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
        }

        public Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        public Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        public Builder filter(MetricFilter filter) {
            this.filter = filter;
            return this;
        }

        public SparkReporter build(String sparkHost, int sparkPort) {
            return new SparkReporter(registry, sparkHost, sparkPort, rateUnit, durationUnit, filter);
        }
    }
}
