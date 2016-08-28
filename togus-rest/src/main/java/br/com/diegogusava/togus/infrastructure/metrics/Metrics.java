package br.com.diegogusava.togus.infrastructure.metrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

import java.util.Map;

public class Metrics {

    static final MetricRegistry METRICS;
    static final HealthCheckRegistry HEALTH_CHECK_REGISTRY;

    static {
        METRICS = new MetricRegistry();
        HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();
        registerAll("gc", new GarbageCollectorMetricSet(), METRICS);
        registerAll("memory", new MemoryUsageGaugeSet(), METRICS);
        registerAll("threads", new ThreadStatesGaugeSet(), METRICS);
    }

    public MetricRegistry getMetrics() {
        return METRICS;
    }

    private static void registerAll(String prefix, MetricSet metricSet, MetricRegistry registry) {
        for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
            if (entry.getValue() instanceof MetricSet) {
                registerAll(prefix + "." + entry.getKey(), (MetricSet) entry.getValue(), registry);
            } else {
                registry.register(prefix + "." + entry.getKey(), entry.getValue());
            }
        }
    }

}