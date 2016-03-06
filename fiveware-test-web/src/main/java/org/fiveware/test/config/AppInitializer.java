package org.fiveware.test.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
 
	private static final String CONFIG_MVC_LOCATION = "org.fiveware.test.config";

/*
 * Inicializaor da Aplicação SpringMVC sem o Vaadin	
 */
	
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
 
        System.out.println("Initializing Application for " + servletContext.getServerInfo());
 
        // Create ApplicationContext
        AnnotationConfigWebApplicationContext mvcApplicationContext = new AnnotationConfigWebApplicationContext();
        mvcApplicationContext.setConfigLocation(CONFIG_MVC_LOCATION);
 
        // Add the servlet mapping manually and make it initialize automatically
        DispatcherServlet dispatcherServletMVC = new DispatcherServlet(mvcApplicationContext);
        
        ServletRegistration.Dynamic servletMVC = servletContext.addServlet("mvc-dispatcher", dispatcherServletMVC);
        
        servletMVC.addMapping("/");
        servletMVC.setAsyncSupported(true);
       
    }
}