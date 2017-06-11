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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.Timer;
import static com.muratti66.netstatgui.SystemOps.ipPool;

/**
 * Jframe extended GUI
 */
public class GUI extends javax.swing.JFrame {
    
    private static ArrayList pool;
    private static int selectionItems = 1000;
    private static HashMap pidMap, tempMap;
    private static String trContent = "";
    private static String appName, temppID, tempParse, tempParse2;
    /**
     * Creates new form GUI
     */
    private GUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        refreshBox = new javax.swing.JComboBox<>();
        netstatScrool = new javax.swing.JScrollPane();
        netstatOutput = new javax.swing.JLabel();
        footer = new javax.swing.JLabel();
        header2 = new javax.swing.JLabel();
        header1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistem Bağlantıları");
        setAlwaysOnTop(true);

        refreshBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "5", "10", "30", "60" }));
        refreshBox.setSelectedIndex(1);
        refreshBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        refreshBox.setName("Time Selection"); // NOI18N
        refreshBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBoxActionPerformed(evt);
            }
        });

        netstatScrool.setBorder(null);
        netstatScrool.setName(""); // NOI18N

        netstatOutput.setBackground(new java.awt.Color(255, 255, 255));
        netstatOutput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ActionListener timerListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                netstatOutput.setText(compileNetstat());
            }
        };

        Timer timer = new Timer(500, timerListener);
        timer.setInitialDelay(0);
        timer.start();
        netstatOutput.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        netstatOutput.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        netstatOutput.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        netstatOutput.setOpaque(true);
        netstatScrool.setViewportView(netstatOutput);

        footer.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        footer.setText("Güncelleme (Saniye)");

        header2.setFont(new java.awt.Font("Verdana", 2, 10)); // NOI18N
        header2.setText("written by Muratti66");

        header1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        header1.setText("Netstat Bağlantı Listesi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(netstatScrool))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(header1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 533, Short.MAX_VALUE)
                        .addComponent(footer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(header2)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(footer)
                    .addComponent(header1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(netstatScrool, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(header2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
 * Check Interval updater, using setter method
 * @param evt   Action Event Object
 */
    private void refreshBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBoxActionPerformed
        // TODO add your handling code here:
        JComboBox combo = (JComboBox)evt.getSource();
        selectionItems = Integer.parseInt(combo.getSelectedItem().
                toString()) * 1000;
        SystemOps.setInterval(selectionItems);
        SystemOps.connPoolRestart();
    }//GEN-LAST:event_refreshBoxActionPerformed
/**
 * This method gui starter (main method)
 */
    public static void start() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
/**
 * This method convert pool data to HTML
 * @return  String
 */
    private static String compileNetstat() {
        pool = SystemOps.connPoolReturner();
        StringBuilder printStringBuilder = new StringBuilder();
        printStringBuilder.append("<html><head>");
        printStringBuilder.append("<style>"
                + "body {"
                + "  font-family: Verdana, Geneva, sans-serif;"
                + "}"
                + "td {"
                + "  border: 1px solid white;"
                + "}"
                + "#netstat_table {"
                + "  width: 647px;"
                + "  font-size: 7px;"
                + "}"
                + "#netstat_col1 {"
                + "  margin-left: 2px;"
                + "  width: 50px;"
                + "}"
                + "#netstat_col2 {"
                + "  width: 50px;"
                + "  text-align: center;"
                + "}"
                + "#netstat_col3 {"
                + "  text-align: center;"
                + "  width: 50px;"
                + "}"
                + "#netstat_col4 {"
                + "  width: 50px;"
                + "  text-align: center;"
                + "}"
                + "#netstat_col5 {"
                + "  width: 50px;"
                + "  text-align: center;"
                + "}"
                + "#netstat_col6 {"
                + "  width: 200px;"
                + "  margin-left: 2px;"
                + "}"
                + "#netstat_col7 {"
                + "  width: 50px;"
                + "  text-align: center;"
                + "}"
                + "</style>");
        printStringBuilder.append("</head><body>");
        printStringBuilder.append("<table id=\"netstat_table\">" 
                + "<tr style=\"text-decoration: underline;\">"
                + "<td id=\"netstat_col1\">Uygulama Adı</td>"
                + "<td id=\"netstat_col2\">PID</td> " 
                + "<td id=\"netstat_col3\">Tip</td>" 
                + "<td id=\"netstat_col4\">Kullanıcı</td>"
                + "<td id=\"netstat_col5\">Protokol</td>" 
                + "<td id=\"netstat_col6\">Bağlantı</td>" 
                + "<td id=\"netstat_col7\">Durum</td></tr>");
        for (int i = 0; i < pool.size(); i++) {
            tempMap = (HashMap) pool.get(i);
            tempParse = tempMap.get("connRoute").toString().
                    split("->")[1].toString();
            tempParse2 = tempParse.split(":")[0];
            if (ipPool.contains(tempParse2)) {
                trContent = "style=\"color: red;\"";
            } else {
                trContent = "";
            }
            appName = ""; 
            temppID = "";
            if (SystemOps.osType.contains("Windows")) {
                pidMap = SystemOps.procPool;
                temppID = tempMap.get("pID").toString();
                if (pidMap.containsKey(temppID)) {
                    appName = pidMap.get(temppID).toString().
                            split(".exe")[0];
                } else {
                    appName = tempMap.get("appName").toString();
                }
            } else {
                appName = tempMap.get("appName").toString();
            }
            printStringBuilder.append("<tr " + trContent + " >" 
                + "<td id=\"netstat_col1\">" + appName + "</td>" 
                + "<td id=\"netstat_col2\">" + tempMap.get("pID") + "</td>"
                + "<td id=\"netstat_col3\">" + tempMap.get("typE") + "</td>"
                + "<td id=\"netstat_col4\">" + tempMap.get("useR") + "</td>"
                + "<td id=\"netstat_col5\">" + tempMap.get("protO") + "</td>"
                + "<td id=\"netstat_col6\">" + tempMap.get("connRoute")+"</td>" 
                + "<td id=\"netstat_col7\">" + tempMap.get("statuS") 
                + "</td></tr>");
        }
        printStringBuilder.append("</table>");
        printStringBuilder.append("</body></html>");
        return printStringBuilder.toString();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel footer;
    private javax.swing.JLabel header1;
    private javax.swing.JLabel header2;
    private javax.swing.JLabel netstatOutput;
    private javax.swing.JScrollPane netstatScrool;
    private javax.swing.JComboBox<String> refreshBox;
    // End of variables declaration//GEN-END:variables
}
