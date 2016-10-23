package br.com.diegogusava.togus.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public enum RestClientFactory {

    INSTANCE(getWebTarget());

    ResteasyWebTarget target;

    RestClientFactory(ResteasyWebTarget target) {
        this.target = target;
    }

    public UserRestClient getClient() {
        return target.proxy(UserRestClient.class);
    }

    private static ResteasyWebTarget getWebTarget() {
        ResteasyClientBuilder clientBuilder = new ResteasyClientBuilder();
        clientBuilder.connectionPoolSize(20);
        ResteasyClient client = clientBuilder.build();
        ResteasyWebTarget target = client.target("http://localhost:8080/togus/");
        return target;
    }

}
