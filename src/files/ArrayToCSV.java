/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mxsxs2
 */
public final class ArrayToCSV {
    private final String[][] TableRows;
    private final String FileName;
    private final String Location;
    private final java.util.ArrayList<String> Log= new java.util.ArrayList<>();
    public ArrayToCSV(String[][] Data, String FileName, String Location){
        this.TableRows=Data;
        this.FileName=FileName;
        this.Location=Location;
        if(Data.length>0){
            this.createCSVFormat();
            this.createFile();
        }
    }
    private void createCSVFormat(){
        int ArraySize=this.TableRows[0].length;                                          //Get the size of the collumns
        for(int r=0; r<this.TableRows.length; r++){                                                   //Go trought every single Row
            for(int c=0; c<this.TableRows[r].length; c++){                                                          //Go trough every collumn in the row
                this.TableRows[r][c]='"'+this.TableRows[r][c]+'"';                                //Add double qoutes
                //System.out.println(c+":"+ArraySize);
                if(c<ArraySize-1){
                    this.TableRows[r][c]+=",";                                                 //Add , if its not the last collumn
                }
            }
        }
        this.Log.add("CSV format is created.");
    }
    private boolean createFile(){
        File Dir = new File(this.Location);                                     //Get folder
        boolean result;
        if (!Dir.exists()) {                                                    //Check if it exists
                return false;
        }else{
            result=true;
        }
            if(result) {                                                        //If the folder exists
                try {
                    //java.text.SimpleDateFormat dt = new java.text.SimpleDateFormat("yyyyy-mm-dd_hh-mm-ss"); 
                    //String Filename = dt.format(new java.util.Date());                                      //Create the file name what is the date
                    FileOutputStream out = new FileOutputStream(Dir.getAbsolutePath()+"/"+this.FileName+".csv"); //Create the file
                    for(String[] Row:this.TableRows){                           //Go trough the rows                   
                        String Line="";
                        for(String Collumn:Row){
                            Line+=Collumn;                              //Concat the collumns into a line
                        }
                        out.write(Line.getBytes());                             //Write the line into the file
                        out.write("\r\n".getBytes());                           //Write the line brake
                    }
                        out.flush();
                        out.close();                                            //Close the file
                        this.Log.add("File is created... OK");
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    this.Log.add("File is created... ERROR");
                    System.out.println(ex.getMessage());
                    return false;
                }
                
            }   
        
        return true;
    }
    public java.util.ArrayList<String> getLog() {
        return Log;
    } 
}
