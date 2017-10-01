/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Mxsxs2
 */
public class DBDump {
    private final MysqlAccess SQL= new MysqlAccess();
   //private final String Desktop=System.getProperty("user.home") + "\\Desktop";
    private final String Desktop="/root/";
    private final String Location=Desktop+"/NemArchives";
    private final String[] Tables= new String[]{"entry","optionaddresses","refund","session","transaction","sitetexts"};
    private String FolderName;
    private java.util.ArrayList<String> Log= new java.util.ArrayList<>();
    public DBDump(){
        try {
            DumpTableAndCreateFiles();
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
    }
    private boolean createFolder(){
        this.Log.add("Check folder...");
        //System.out.println(this.Location+"\\"+this.FolderName);
        java.io.File Parent = new java.io.File(this.Location);
        if(!Parent.exists()) Parent.mkdir();
        java.io.File Dir = new java.io.File(this.Location+"/"+this.FolderName);
        boolean result;
        if (!Dir.exists()) {                                                    //Check if it exists
            try{
                Dir.mkdir();//If not then create it
                result=true;
            } catch(SecurityException se){
                result=false;
                //System.out.println(se.getMessage());
                
            }
        }else{                                                                  //If exists then create an other one.
            this.FolderName+=2;
            try{
                new java.io.File(this.Location+"/"+this.FolderName).mkdir();
                result=true;
            }catch(Exception e){
                result=false;
                //System.out.println(e.getMessage());
            }
        }
        this.Log.add("Folder created: "+result);
        this.Log.add(this.FolderName);
        this.Log.add("");
        return result;
    }
    private void DumpTableAndCreateFiles() throws Exception{
        
        PreparedStatement preparedStatemaent = SQL.Connect.prepareStatement("SELECT `end`, `start`,`sid` FROM `session` ORDER BY `SID` DESC LIMIT 1");
        ResultSet RS=preparedStatemaent.executeQuery();
        if(RS.first()){
            java.text.SimpleDateFormat dt = new java.text.SimpleDateFormat("yyyy_MM_dd");
            String Start=dt.format(RS.getDate("start"));
            String End=dt.format(RS.getDate("end"));
            this.FolderName=Start+"_to_"+End;
        }
        if(this.createFolder()){
            for(String Table:this.Tables){
                TableDump Dumped= new TableDump(Table);
                this.Log.addAll(Dumped.getLog());
                ArrayToCSV CFile= new ArrayToCSV(Dumped.getTable(),Table,this.Location+"/"+this.FolderName);
                this.Log.addAll(CFile.getLog());
            }
        }
    }
    public void truncateTable(){
        this.Log.add("");
        for(String Table:this.Tables){
            if(!Table.equals("optionaddresses") && !Table.equals("session") && !Table.equals("sitetexts")){
                try {
                    this.SQL.Connect.createStatement().executeUpdate("TRUNCATE TABLE `"+Table+"`");
                    this.Log.add(Table+" Truncated: OK");
                } catch (SQLException ex) {
                    this.Log.add(Table+" Truncated: ERROR");
                    //System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public java.util.ArrayList<String> getLog() {
        return Log;
    }
}
