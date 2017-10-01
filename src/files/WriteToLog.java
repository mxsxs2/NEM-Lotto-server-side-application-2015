/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mxsxs2
 */
public class WriteToLog {
    //private final String Location=System.getenv("APPDATA")+"\\Nem"; //win
    private final String Desktop="/root/";
    private final String Location=this.Desktop+"/NemArchives";
    private java.io.BufferedWriter out = null;
    private String Result="";
    public WriteToLog(String[] Log){
        if(this.createFolderIfNotExists()){
            this.Result=this.OpenAndWriteLog(Log);
        }
    }
    private boolean createFolderIfNotExists(){
        java.io.File Dir = new java.io.File(this.Location);
        boolean result;
        if (!Dir.exists()) {                                                    //Check if it exists
            try{
                Dir.mkdir();//If not then create it
                return true;
            } catch(SecurityException se){
                //System.out.println(se.getMessage());
                return false;
            }
        }
        return true;
    }
    private String OpenAndWriteLog(String[] Log){
        try  {
            java.io.FileWriter fstream = new java.io.FileWriter(this.Location+"/SessionLog.txt", true); //true tells to append data.
            this.out = new java.io.BufferedWriter(fstream);
            for(String Line:Log){
                this.out.write("\r\n"+Line);
            }
            this.out.close();
        }catch (java.io.IOException e){
            return "Log saved... ERROR";
        }
        return "Log saved... OK";
    }
    public String getResult(){
        return this.Result;
    }
}
