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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
import static com.muratti66.netstatgui.Main.logger;

/**
 * This includes class system methods
 */
public class SystemOps {
    private static boolean exit = false;
    private static String cmdOutLine = "";
    private static String appName, connRoute, devicE, fD, line, par, pID, 
            pid, protO, sizeOf, statuS, tempParse, tempParseTwo, typE, 
            unq, useR;
    private static String[] parSplit, parts;
    private static ArrayList connFirstTempPool, connPool, connTempPool, output;
    private static HashMap temp;
    private static BufferedReader br, reader, readerTwo;
    private static InputStream is;
    private static InputStreamReader isr;
    
    final private static String[] mNetCmd = {"/bin/bash", "-c", 
        "/usr/sbin/lsof -nP | grep TCP |grep -v \"LISTEN\""};
    final private static String[] lNetCmd = {"/bin/bash", "-c", 
        "/usr/bin/lsof -nP | grep TCP |grep -v \"LISTEN\""};
    final private static String[] msNetCmd = {"netstat", "-ont"};
    private static long timeInterval = 1000;
    private static final Logger LOGGER = Logger.
            getLogger(Main.class.getName());
    public static ArrayList ipPool = new ArrayList();
    public static HashMap procPool = new HashMap();
    final public static String osType = System.getProperty("os.name");

    /**
     * Command Execute Wrapper
     * @param   command   The commands are given in fragments (String[])
     * @return Arraylist
     */
    private static ArrayList execCMD(String[] command) {
        ArrayList out = new ArrayList();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            readerTwo = new BufferedReader(new InputStreamReader(
                    p.getErrorStream()));
            while ((cmdOutLine = reader.readLine())!= null) {
                if (cmdOutLine != null && cmdOutLine.length() > 0) {
                    out.add(cmdOutLine);
                }
            }
            cmdOutLine = "";
            while ((cmdOutLine = readerTwo.readLine())!= null) {
                if (cmdOutLine != null && cmdOutLine.length() > 0) {
                    out.add(cmdOutLine);
                }
            }
        } catch (Exception e) {
            logger.log( Level.INFO, e.toString(), e );
        }
        return out;
    }
    /**
     * Connection pool start; interface pool initialize, 
     * proccess pool initialize (os is windows) and connection pool 
     * initialize starter
     */
    public static void connPoolStart() {
        ipPool = getAllIP();
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    procPoolUpdate();
                    connPoolUpdate();
                    try {
                        Thread.sleep(timeInterval);
                    } catch (Exception e) {
                        logger.log( Level.INFO, e.toString(), e );
                    }
                    if (exit == true) {
                        break;
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    /**
     * Restart pool thread if check interval changes
     */
    public static void connPoolRestart() {
        exit = true;
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            logger.log( Level.INFO, e.toString(), e );
        }
        exit = false;
        connPoolStart();
    }
    /**
     * Connection pool create and initialize proccess
     */
    private static void connPoolUpdate() {
        String[] cmd = null;
        if (osType.contains("Windows")) {
            cmd = msNetCmd;
        } else if (osType.contains("Linux")) {
            cmd = lNetCmd;
        } else if (osType.contains("Mac")) {
            cmd = mNetCmd;
        }
        // If platform not supported
        if (cmd == null) {
            System.exit(9);
        }
        // Push background task
        connTempPool = new ArrayList();
        connFirstTempPool = new ArrayList();
        if (osType.contains("Windows")) {
            output = execCMD(cmd);
            output.remove(0); 
            output.remove(0);
            Collections.sort(output);
            output.removeAll(Collections.singleton(null));
            for (int i = 0; i < output.size(); i++) {
                par = output.get(i).toString();
                parSplit = par.split("\\s+");
                protO = parSplit[1]; devicE = parSplit[6]; 
                connRoute = parSplit[2] + "->" + parSplit[3];
                pID = parSplit[5]; statuS = parSplit[4]; 
                if (!statuS.contains("ESTABL")) { continue; }
                temp = new HashMap<String, String>();
                temp.put("appName", "NONE"); temp.put("pID", pID);
                temp.put("useR", "NONE"); temp.put("fD", "NONE");
                temp.put("typE", "NONE"); temp.put("devicE", devicE);
                temp.put("sizeOf", "NONE"); temp.put("protO", protO);
                temp.put("connRoute", connRoute); temp.put("statuS", statuS);
                tempParse = connRoute.toString().
                    split("->")[1].toString();
                tempParseTwo = tempParse.split(":")[0];
                if (ipPool.contains(tempParseTwo)) {
                    connFirstTempPool.add(temp);
                } else {
                    connTempPool.add(temp);
                }
            }
        } else {
            output = execCMD(cmd);
            Collections.sort(output);
            output.removeAll(Collections.singleton(null));
            for (int i = 0; i < output.size(); i++) {
                String par = output.get(i).toString();
                String[] parSplit = par.split("\\s+");
                appName = parSplit[0]; pID = parSplit[1];  
                useR = parSplit[2]; fD = parSplit[3];  
                typE = parSplit[4];  devicE = parSplit[5]; 
                sizeOf = parSplit[6]; protO = parSplit[7]; 
                connRoute = parSplit[8]; statuS = parSplit[9].
                        replace("(", "").replace(")", "");
                temp = new HashMap<String, String>();
                temp.put("appName", appName);  temp.put("pID", pID);
                temp.put("useR", useR); temp.put("fD", fD);
                temp.put("typE", typE); temp.put("devicE", devicE);
                temp.put("sizeOf", sizeOf); temp.put("protO", protO);
                temp.put("connRoute", connRoute); temp.put("statuS", statuS);
                tempParse = connRoute.toString().
                    split("->")[1].toString();
                tempParseTwo = tempParse.split(":")[0];
                if (ipPool.contains(tempParseTwo)) {
                    connFirstTempPool.add(temp);
                } else {
                    connTempPool.add(temp);
                }
            }
        }
        connPool = new ArrayList(connFirstTempPool);
        connPool.addAll(connTempPool);
    }
    /**
     * Connection pool getter
     * @return ArrayList
     */
    public static ArrayList connPoolReturner() {
        return connPool;
    }
    /** 
     * Interval updater
     * @param dynInt selected seconds set to variable in class
     */
    public static void setInterval(int dynInt) {
        timeInterval = dynInt;
    }
    /**
     * Get all interface ip addresses from os
     * (passed localhost and ipv6)
     * @return ArrayList
     */
    public static ArrayList getAllIP() {
        ArrayList<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> iterNetwork;
            Enumeration<InetAddress> iterAddress;
            NetworkInterface network;
            InetAddress address;
            iterNetwork = NetworkInterface.getNetworkInterfaces();
            while (iterNetwork.hasMoreElements()) {
                network = iterNetwork.nextElement();
                if (!network.isUp()) { continue; }
                if (network.isLoopback()) { continue; }
                iterAddress = network.getInetAddresses();
                while (iterAddress.hasMoreElements()) {
                    address = iterAddress.nextElement();
                    if (address.isAnyLocalAddress()) { continue; }
                    if (address.isLoopbackAddress()) { continue; }
                    if (address.isMulticastAddress()) { continue; }
                    if (address instanceof Inet6Address) { continue; }
                    ipList.add(address.getHostAddress());
                }
            }
        } catch (Exception e) {
            logger.log( Level.INFO, e.toString(), e );
        } finally {
            return ipList;
        }
    }
    /**
     * Get proccess list from tasklist.exe if os is windows.
     */
    public static void procPoolUpdate() {
        HashMap tempProcPool = new HashMap<String, String>();
        if (osType.contains("Windows")) {
            try {
                ProcessBuilder process = new ProcessBuilder("tasklist.exe", 
                        "/fo", "csv", "/nh");
                final Process start = process.start();
                is = start.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                  parts = line.split(",");
                  unq = parts[0].substring(1).replaceFirst(".$", "");
                  pid = parts[1].substring(1).replaceFirst(".$", "");
                  tempProcPool.put(pid, unq);
                }
                procPool = tempProcPool;
                
            } catch (Exception e) {
                logger.log( Level.INFO, e.toString(), e );
            }
        }
    }
}
