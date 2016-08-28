package br.com.diegogusava.togus.infrastructure;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class RestConfiguration extends Application {

    public RestConfiguration() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("v1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost(System.getProperty("togus.swagger.host", "localhost:8080"));
        beanConfig.setBasePath(System.getProperty("togus.swagger.basepath", "togus/rest"));
        beanConfig.setResourcePackage("br.com.diegogusava.togus.endpoint");
        beanConfig.setScan(true);
    }

}
