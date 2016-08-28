package br.com.diegogusava.togus.infrastructure.metrics;

import com.codahale.metrics.Timer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class EndpointResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        final Timer.Context context = (Timer.Context) containerRequestContext.getProperty("timer");
        if (context != null) {
            context.stop();
        }
    }

}
