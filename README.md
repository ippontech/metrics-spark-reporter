metrics-spark-reporter
=============

## Dropwizard Metrics reporter for Apache Spark Streaming

This is a reporter for the [Metrics library] (https://dropwizard.github.io/metrics/3.1.0/)
of [DropWizard] (http://dropwizard.io/),
similar to the [graphite] (https://dropwizard.github.io/metrics/3.1.0/manual/graphite/#manual-graphite)
or [ganglia] (https://dropwizard.github.io/metrics/3.1.0/manual/ganglia/#manual-ganglia) reporters,
except that it reports to metrics-spark-receiver.

This reporter is using sockets for sending data to the Spark Streaming Receiver.

## Metrics

The library Metrics provides 5 types of measure :
* [Gauge] (https://dropwizard.github.io/metrics/3.1.0/getting-started/#gauges) :
an instantaneous measurement of a value.
* [Counter] (https://dropwizard.github.io/metrics/3.1.0/getting-started/#counters) :
a gauge for an AtomicLong instance.
* [Meter] (https://dropwizard.github.io/metrics/3.1.0/getting-started/#meters) :
a measure of the rate of events over time.
* [Histogram] (https://dropwizard.github.io/metrics/3.1.0/getting-started/#histograms) :
a measure of the statistical distribution of values in a stream of data.
* [Timer] (https://dropwizard.github.io/metrics/3.1.0/getting-started/#timers) :
a measure of both the rate that a particular piece of code is called and the distribution of its duration.

## Configuration

In order to do a Spark reporting, you need to add the dependency :
```
<dependency>
	<groupId>fr.ippon.spark.metrics</groupId>
	<artifactId>metrics-spark-reporter</artifactId>
	<version>1.2</version>
</dependency>
```

And implement the SparkReporter like :
```
SparkReporter sparkReporter = SparkReporter.forRegistry(metricRegistry)
	.convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS)
    .build("localhost", 9999);

sparkReporter.start(10, TimeUnit.SECONDS);
```

## Test

There is two ways to test this Reporter :

* With a sample [spark-jhipster] (https://github.com/ahars/spark-jhipster)
* With Docker (in sample/).


Test sending data with the [JHipster] (http://jhipster.github.io/) sample which report
to a Spark Streaming app implementing
the java custom receiver [spark-jhipster] (https://github.com/ahars/spark-jhipster).

Send data by launching the JHipster sample with the Maven command :
```
$ mvn spring-boot:run
```

Display metrics received by launching one of those two classes
of [metrics-spark](https://github.com/ahars/metrics-spark) :
* `MetricsToConsole` to display data in the console.
* `MetricsToES` to send data to an ElasticSearch server via Spark in order to use Kibana.

