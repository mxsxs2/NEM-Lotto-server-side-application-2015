/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.UIDefaults;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Mxsxs2
 */
public class MainFrame extends javax.swing.JFrame {
    private long EndTimeInSeconds;
    private ScheduledThreadPoolExecutor executor;
    private ScheduledThreadPoolExecutor TimeUpdater;
    private ScheduledThreadPoolExecutor AmountUpdater;
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        this.startTimeUpdaterThread();
        this.startAmountUpdaterThread();
        this.startCountDownThread();
        SendMail SM=new SendMail();
                 String Subject="Server program is started.";
                 SM.setValues("nem@mxsxs2.info", "server@mxsxs2.info", "The server program just started", Subject);
                 //SM.send();
        this.addLineToPane("Automatic count down started at "+new java.util.Date().toString());
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCounter = new javax.swing.JLabel();
        jCloseButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jStatusPane = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jStopButton = new javax.swing.JButton();
        jStartButton = new javax.swing.JButton();
        jDumpButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jCloseButton.setText("Close");
        jCloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCloseButtonActionPerformed(evt);
            }
        });

        jStatusPane.setBackground(new java.awt.Color(0, 0, 0));
        jStatusPane.setForeground(new java.awt.Color(0, 204, 0));
        jStatusPane.setDisabledTextColor(new java.awt.Color(0, 204, 0));
        jStatusPane.setSelectedTextColor(new java.awt.Color(0, 153, 0));
        java.awt.Color bgColor = java.awt.Color.BLACK;
        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane[Enabled].backgroundPainter", bgColor);
        this.jStatusPane.putClientProperty("Nimbus.Overrides", defaults);
        this.jStatusPane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        this.jStatusPane.setBackground(bgColor);
        this.jStatusPane.setEditable(false);
        jScrollPane1.setViewportView(jStatusPane);

        jLabel1.setText(" Time left until session change:");

        jStopButton.setText("Stop count down");
        jStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStopButtonActionPerformed(evt);
            }
        });

        jStartButton.setText("Start count down");
        jStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStartButtonActionPerformed(evt);
            }
        });

        jDumpButton.setText("Save database tables");
        jDumpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDumpButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Truncate database tables");
        jButton1.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jStopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jCloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jDumpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCounter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jDumpButton)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCloseButton)
                    .addComponent(jStopButton)
                    .addComponent(jStartButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStartButtonActionPerformed
        this.startCountDownThread();
        this.addLineToPane("Count down started by user at "+new java.util.Date().toString());
    }//GEN-LAST:event_jStartButtonActionPerformed

    private void jStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStopButtonActionPerformed
        int optionPane = javax.swing.JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to stop the count down?",
                "Warning",
                javax.swing.JOptionPane.YES_NO_OPTION);
        if(optionPane==javax.swing.JOptionPane.YES_OPTION){
            this.stopCountDownThread();
        }
    }//GEN-LAST:event_jStopButtonActionPerformed

    private void jCloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCloseButtonActionPerformed
        int optionPane = javax.swing.JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want close?\n"
                +"The session change may not run in time.",
                "Warning",
                javax.swing.JOptionPane.YES_NO_OPTION);
                if(optionPane==javax.swing.JOptionPane.YES_OPTION){
                    this.dispatchEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
                    this.dispose();
                }
        
    }//GEN-LAST:event_jCloseButtonActionPerformed

    private void jDumpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDumpButtonActionPerformed
        DBDump DBD=new DBDump();
        this.addLineToPane("-----------------------------------");
        this.addLineToPane("User indicated database dump started at "+new java.util.Date().toString());
        this.addLogToPane(DBD.getLog());
        this.addLineToPane("User indicated database dump finished at "+new java.util.Date().toString());
        this.addLineToPane("-----------------------------------");
        WriteToLog WTL= new WriteToLog(this.jStatusPane.getText().split("\\r?\\n?\\r\\n"));
        this.addLineToPane(WTL.getResult());
    }//GEN-LAST:event_jDumpButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
    private void getEndTime(){
        MysqlAccess SQL= new MysqlAccess();
        PreparedStatement preparedStatemaent =null;
        ResultSet RS=null;
        try {
            preparedStatemaent = SQL.Connect.prepareStatement("SELECT TIMESTAMPDIFF(second,NOW(),`end`) as `left` FROM `session` ORDER BY `SID` DESC LIMIT 1");
            RS=preparedStatemaent.executeQuery();
            if(RS.first()){
                    this.EndTimeInSeconds=RS.getLong("left");
                    //System.out.println("asd"+RS.getInt("left"));
            }
            
         } catch (SQLException ex) {
                //System.out.println(ex.getMessage());
         }finally{
          org.apache.commons.dbutils.DbUtils.closeQuietly(SQL.Connect);
          org.apache.commons.dbutils.DbUtils.closeQuietly(RS);
          org.apache.commons.dbutils.DbUtils.closeQuietly(preparedStatemaent);
        }
    }
    private void countDown(){
            this.EndTimeInSeconds--;
            if(this.EndTimeInSeconds<=0){
                this.stopCountDownThread();
                this.stopTimeUpdaterThread();
                this.stopAmountUpdaterThread();
                this.endSession();
                this.startCountDownThread();
                this.startTimeUpdaterThread();
                this.startAmountUpdaterThread();
                this.addLineToPane("Automatic count down started at "+new java.util.Date().toString());
            }
            long different=this.EndTimeInSeconds*1000;
            long secondsInMilli = 1000;
	    long minutesInMilli = secondsInMilli * 60;
	    long hoursInMilli = minutesInMilli * 60;
	    long daysInMilli = hoursInMilli * 24;
            long day = different / daysInMilli;
            different = different % daysInMilli;
            long hour = different / hoursInMilli;
            different = different % hoursInMilli;
            long min = different / minutesInMilli;
            different = different % minutesInMilli;
            long second = different / secondsInMilli;
            this.jCounter.setText(Long.toString(day)+" Days "+hour+":"+min+":"+second);
            
    }
    private void startCountDownThread(){
        this.getEndTime();
        //this.EndTimeInSeconds=10;
        this.addLineToPane(this.EndTimeInSeconds+" seconds left until session change");
        this.executor=new ScheduledThreadPoolExecutor(1);
        this.executor.scheduleWithFixedDelay(() -> countDown(),0, 1, TimeUnit.SECONDS);
        
        
    }
    private void stopCountDownThread(){
        this.executor.shutdown();
        this.addLineToPane("Count down stopped by user at "+new java.util.Date().toString());
    }
    private void startTimeUpdaterThread(){
        this.addLineToPane("Time updating thread is running.");
        this.TimeUpdater=new ScheduledThreadPoolExecutor(1);
        this.TimeUpdater.scheduleWithFixedDelay(() -> this.getEndTime(),0, 1, TimeUnit.MINUTES);
    }
    private void stopTimeUpdaterThread(){
        this.TimeUpdater.shutdown();
        this.addLineToPane("Time updating thread has stopped.");
    }
    private void startAmountUpdaterThread(){
        this.addLineToPane("Entry explorer thread is running.");
        updateBTCEntryValue uBTCEV= new updateBTCEntryValue();
        this.AmountUpdater=new ScheduledThreadPoolExecutor(1);
        this.AmountUpdater.scheduleWithFixedDelay(() -> uBTCEV.updateFieldsInDB(),0, 5, TimeUnit.MINUTES);
    }
    private void stopAmountUpdaterThread(){
        this.AmountUpdater.shutdown();
        this.addLineToPane("Entry explorer thread is has stopped.");
    }
    
    private void endSession(){
        DBDump DBD=new DBDump();
               this.addLineToPane("-----------------------------------");
               this.addLineToPane("Automatic session change started at "+new java.util.Date().toString());
               DBD.truncateTable();
               this.addLogToPane(DBD.getLog());
        this.addNextSession();
               this.addLineToPane("Automatic session change finished at "+new java.util.Date().toString());
               this.addLineToPane("-----------------------------------");
        WriteToLog WTL= new WriteToLog(this.jStatusPane.getText().split("\\r?\\n?\\r\\n"));
               this.addLineToPane(WTL.getResult());
    }
    private void addLogToPane(java.util.ArrayList<String> Log){
        for(String Line:Log.toArray(new String[Log.size()])){
            this.addLineToPane(Line);
        }
    }
    private void addLineToPane(String Line){
        try {
            javax.swing.text.StyledDocument doc = this.jStatusPane.getStyledDocument();
            javax.swing.text.SimpleAttributeSet keyWord = new javax.swing.text.SimpleAttributeSet();
            javax.swing.text.StyleConstants.setForeground(keyWord, java.awt.Color.YELLOW);
            javax.swing.text.StyleConstants.setBackground(keyWord, java.awt.Color.BLACK);
            javax.swing.text.StyleConstants.setBold(keyWord, true);
            doc.insertString(doc.getLength(), Line+"\n",keyWord);
            System.out.println(Line);
        } catch (BadLocationException ex) {
            //Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void addNextSession(){
        MysqlAccess SQL= new MysqlAccess();
        PreparedStatement preparedStatemaent=null;
        ResultSet RS=null;
        ResultSet SIDRS=null;
        ResultSet GROUP=null;
        String Errors="";
        int tries=0;
        try {
            preparedStatemaent = SQL.Connect.prepareStatement("SELECT `changeperiod`, `end`,`sid` FROM `session` ORDER BY `SID` DESC LIMIT 1");
            RS=preparedStatemaent.executeQuery();
            if(RS.first()){
                String state="INSERT INTO `session`(`start`,`end`,`changeperiod`) VALUES('"+RS.getString("end")+"',DATE_ADD('"+RS.getString("end")+"', INTERVAL 1 "+RS.getString("changeperiod")+"),'"+RS.getString("changeperiod")+"')";
                //System.out.println(state);
                java.sql.PreparedStatement Statement=SQL.Connect.prepareStatement(state);
                int RSI=Statement.executeUpdate();
                if(RSI>0){
                    this.addLineToPane("Next session started... OK");
                    SIDRS=SQL.PreparedSelect("session",new String[]{"sid"}, "", "1", "`sid` DESC");
                    SIDRS.first();
                    GROUP=SQL.PreparedSelect("optionaddresses",new String[]{"id"}, "`session`='0'", "1", "`id` asc");
                    if(GROUP==null){ //create an address group if there is no predefined
                        //--------------------------- Create NXT address GROUP
                        CreateNXTWallet CNXTW=new CreateNXTWallet();
                        while(1==1){
                            if(CNXTW.Create()){
                                this.addLineToPane("New Next address group created... OK");
                                break;
                            }else{
                                this.addLineToPane("New Next address group created... ERROR");
                                Errors+="New Next address group created... ERROR\n";
                            }
                            tries++;
                            if(tries>20){
                                this.addLineToPane("Next address group creating failed more than 20 times.");
                                Errors+="Next address group creating failed more than 20 times.\n";
                                break;
                            }
                    }
                        
                    }
                    //--------------------------- NXT address group allocation
                    if(SQL.PreparedUpdate("optionaddresses", new String[]{"session"}, new String[]{SIDRS.getString("sid")}, "`session`='0'", "1")){
                        this.addLineToPane("Nxt address group allocation... OK");
                    }else{
                        this.addLineToPane("Nxt address group allocation... ERROR");
                        Errors+="Nxt address group allocation... ERROR\n";
                    }
                    //System.out.println("before sql");
                    GROUP=SQL.PreparedSelect("btcwallet",new String[]{"guid"}, "`session`='0'", "1", "`time` asc");
                    //System.out.println("after");
                    if(GROUP==null){ //create an address group if there is no predefined
                        //System.out.println("in if");
                        CreateBTCWallet CW=new CreateBTCWallet();
                        tries=0;
                        while(1==1){
                            if(CW.Create()){
                                this.addLineToPane("New Blockchain wallet created... OK");
                                break;
                            }else{
                                this.addLineToPane("New Blockchain wallet created... ERROR");
                                Errors+="New Blockchain wallet created... ERROR\n";
                            }
                            tries++;
                            if(tries>20){
                                this.addLineToPane("Wallet creating failed more than 20 times.");
                                Errors+="Wallet creating failed more than 20 times.\n";
                                break;
                            }
                        }
                    }
                    //System.out.println("after if");
                    if(SQL.PreparedUpdate("btcwallet", new String[]{"session"}, new String[]{SIDRS.getString("sid")}, "`session`='0'", "1")){
                        this.addLineToPane("Blockchain wallet is allocated... OK");
                    }else{
                        this.addLineToPane("Blockchain wallet is allocated... ERROR");
                        Errors+="Blockchain wallet is allocated... ERROR\n";
                    }
                }else{
                    this.addLineToPane("Next session started... ERROR");
                    Errors+="Next session started... ERROR\n";
                }
            }
        } catch (SQLException ex) {
            this.addLineToPane("Next session started... ERROR");
            Errors+="Next session started... ERROR\n";
            //Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            org.apache.commons.dbutils.DbUtils.closeQuietly(SQL.Connect);
            org.apache.commons.dbutils.DbUtils.closeQuietly(RS);
            org.apache.commons.dbutils.DbUtils.closeQuietly(preparedStatemaent);
            org.apache.commons.dbutils.DbUtils.closeQuietly(SIDRS);
        }
        SendMail SM=new SendMail();
                 String Subject="Everything is ok.";
                 if(Errors.length()>0){
                     Subject="Something went wrong!";
                 }else{
                     Errors="Everything went alright without errors:)\n";
                 }
                 SM.setValues("nem@mxsxs2.info", "NemSessionCloser", Errors, Subject);
                 SM.send();
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jCloseButton;
    private javax.swing.JLabel jCounter;
    private javax.swing.JButton jDumpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jStartButton;
    private javax.swing.JTextPane jStatusPane;
    private javax.swing.JButton jStopButton;
    // End of variables declaration//GEN-END:variables
}
