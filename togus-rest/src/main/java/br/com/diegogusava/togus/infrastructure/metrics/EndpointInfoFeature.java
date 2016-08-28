package br.com.diegogusava.togus.infrastructure.metrics;

import com.codahale.metrics.MetricRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class EndpointInfoFeature implements DynamicFeature {

    private MetricRegistry metricRegistry = Metrics.METRICS;

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        String clazz = resourceInfo.getResourceClass().getName();
        String method = resourceInfo.getResourceMethod().getName();

        Path methodPath = resourceInfo.getResourceMethod().getAnnotation(Path.class);
        Path clazzPath = resourceInfo.getResourceMethod().getDeclaringClass().getAnnotation(Path.class);

        GET httpMethodGet = resourceInfo.getResourceMethod().getAnnotation(GET.class);
        POST httpMethodPost = resourceInfo.getResourceMethod().getAnnotation(POST.class);
        PUT httpMethodPut = resourceInfo.getResourceMethod().getAnnotation(PUT.class);

        String verb = httpMethodGet != null ? "GET"
                : httpMethodPost != null ? "POST"
                : httpMethodPut != null ? "PUT"
                : "DELETE";

        String path = methodPath != null ? clazzPath.value() + methodPath.value()
                : clazzPath.value();

        EndpointRequestFilter filter = new EndpointRequestFilter(method, clazz, verb, path, metricRegistry);
        featureContext.register(filter);
    }


}
