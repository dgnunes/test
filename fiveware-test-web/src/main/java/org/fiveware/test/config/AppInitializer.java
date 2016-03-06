package org.fiveware.test.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
 
	private static final String CONFIG_VAADIN_LOCATION = "org.fiveware.test.views";


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
 
        System.out.println("Initializing Application for " + servletContext.getServerInfo());
 
        // Create ApplicationContext
        AnnotationConfigWebApplicationContext ApplicationContext = new AnnotationConfigWebApplicationContext();
        ApplicationContext.setConfigLocation(CONFIG_VAADIN_LOCATION);
 
        // Add the servlet mapping manually and make it initialize automatically
        DispatcherServlet dispatcherServlet = new DispatcherServlet(ApplicationContext);
        
        ServletRegistration.Dynamic servlet = servletContext.addServlet("vaadin-dispatcher", dispatcherServlet);
        
        servlet.addMapping("/");
        servlet.setAsyncSupported(true);
        
    }
}