package br.com.diegogusava.togus.infrastructure.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

public class EndpointRequestFilter implements ContainerRequestFilter {

    private String method;
    private String clazz;
    private String httpVerb;
    private String httpPath;
    private MetricRegistry metrics;

    public EndpointRequestFilter(String method, String clazz, String httpVerb, String httpPath, MetricRegistry metrics) {
        this.method = method;
        this.clazz = clazz;
        this.httpVerb = httpVerb;
        this.httpPath = httpPath;
        this.metrics = metrics;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String result = String.format("%s.%s -> %s %s", clazz, method, httpVerb, httpPath);
        Timer responses = metrics.timer(result);
        final Timer.Context context = responses.time();
        containerRequestContext.setProperty("timer", context);
    }

}
