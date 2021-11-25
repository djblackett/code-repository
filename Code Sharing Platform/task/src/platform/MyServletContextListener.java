package platform;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println(
                "Callback triggered - ContextListener.");
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Triggers when context initializes
    }



}
