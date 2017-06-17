package com.example.vaadinProject;



import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
  private static final long serialVersionUID = 1L;
    private static boolean reverse=false;
    @Override
    protected void init(VaadinRequest request) 
    {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        final ProgressBar bar = new ProgressBar(0.0f);
        layout.addComponent(bar);
              
        Button button = new Button(" :: Start Thread :: ");
        button.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;
            
            class WorkThread extends Thread {

                @Override
                public void run() {
            
                    while (true) {
                      
                        try {
                            sleep(500); 
                        } catch (InterruptedException e) {}

                        
                        UI.getCurrent().access(new Runnable() {
                            @Override
                            public void run() {
                                float current = bar.getValue();
                                if (reverse)
                                {
                                    bar.setValue(current + 0.10f);
                                    if (current >= 1.0f) {
                                    	reverse=false;bar.setValue(0.9f);
                                    	}
                                }
                                else
                                {
                                    bar.setValue(current - 0.10f);
                                    if (current <= 0.0f) {
                                    	reverse=true;bar.setValue(0.1f);
                                    	}
                                }
                            }
                        });
                    }
               
                }
            }
            
            
            @Override
            public void buttonClick(ClickEvent event) 
            {
                 final WorkThread thread = new WorkThread();
                 thread.start();
                 
                 UI.getCurrent().setPollInterval(100);
                
            }

        });
        
        layout.addComponent(button);
        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    
}


 

