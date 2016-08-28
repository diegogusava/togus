package br.com.diegogusava.togus.infrastructure.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

public class MyMetricsServletContextListener extends MetricsServlet.ContextListener {

    @Override
    protected MetricRegistry getMetricRegistry() {
        return Metrics.METRICS;
    }

}