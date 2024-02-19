package com.jipe.vivaupe.config;

import com.amazon.ask.servlet.ServletConstants;
import com.jipe.vivaupe.servlet.VivaUPEServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

@Configuration
public class WebConfig {

    @Bean
    public ServletRegistrationBean<HttpServlet> alexaServlet() {

        this.carregarPropiedades();

        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new VivaUPEServlet());
        servRegBean.addUrlMappings("/viva-upe/*");
        servRegBean.setLoadOnStartup(1);

        return servRegBean;
    }

    private void carregarPropiedades() {
        System.setProperty(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY, "3600000");
        System.setProperty(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY, "true");
        System.setProperty("javax.net.ssl.keyStore", "javax.net.ssl.keyStore");
        System.setProperty("javax.net.ssl.keyStorePassword", "javax.net.ssl.keyStorePassword");
    }

}