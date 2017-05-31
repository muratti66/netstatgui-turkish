/**
 * NetstatGUI - Periodical Netstat Checker
 * The class illustrates how to write comments used 
 * to generate JavaDoc documentation
 *
 * @author Murat B.
 * @url https://github.com/muratti66/netstatgui-turkish
 * @version 1.00, 28 May 2017
 */
package com.muratti66.netstatgui;

import java.util.logging.Level;
import java.util.logging.Logger;
/*
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
*/

/**
 * Main class - first run
 */
public class Main {
   
    public static Logger logger = Logger.
            getLogger("com.javacodegeeks.snippets.core");
    //private static String logFile = "netstatgui.log";
    
    /**
     * This method - first run and initialize all
     * <br> Note : Args not using..
     * @param args String[]
     */
    public static void main(String[] args) {
        /*
        try {
            FileHandler handler = new FileHandler(logFile, append);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (Exception e) {
            logger.log( Level.INFO, e.toString(), e );
        }
        */
        SystemOps.connPoolStart();
        try {
            GUI.start();
        } catch (Exception e) {
            logger.log( Level.INFO, e.toString(), e );
        }
    }
}
