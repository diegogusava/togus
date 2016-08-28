package br.com.diegogusava.togus.infrastructure.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

public class MyHealthCheckServletContextListener extends HealthCheckServlet.ContextListener {

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return Metrics.HEALTH_CHECK_REGISTRY;
    }

}