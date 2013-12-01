/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;


/*
 * WebappContextListener
 * Created on: Oct 26, 2012 5:27:14 PM
 *
 * Copyright 2012 EnginSoft S.p.A.
 * All rights reserved
 */
import database.DbHelper;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
 */
public class WebContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl");
        try 
        {
            DbHelper manager = new DbHelper(dburl);
            sce.getServletContext().setAttribute("dbmanager",manager);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        // Il database Derby deve essere "spento" tentando di
        try
        {
            DbHelper.close();
        }
        catch(Exception ex)
        {
            Logger.getLogger(WebContextListener.class.getName()).severe(ex.toString());
        }
    }
}
