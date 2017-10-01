/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mxsxs2
 */
public class TableDump {
    private final MysqlAccess SQL= new MysqlAccess();
    private String Table;
    private String[] CollumnNames;
    private String[][] TableRows;
    private java.util.ArrayList<String> Log=new java.util.ArrayList<>();
    public TableDump(String Table){
        this.Table=Table;                                                       //Set table name
        try{
            if(this.selectCollumnNames()){                                      //Get collumns
                if(this.selectRows()){                                          //Get rows
                    this.addCollumnNames();                                     //Create an union of them
                }
            }
        }catch(SQLException e){
            //System.out.println(e.getMessage());
        }
    }
    public void resetClass(){
        this.Table=null;
        this.CollumnNames=null;
        this.TableRows=null;
    }
    private Boolean selectCollumnNames() throws SQLException{
        PreparedStatement statement = this.SQL.Connect.prepareStatement("  SELECT COLUMN_NAME" +
                                                                        "  FROM INFORMATION_SCHEMA.COLUMNS" +
                                                                        "  WHERE table_name = ?");
        statement.setString(1, this.Table);                           //Add table name to select
        ResultSet RS=statement.executeQuery();                        //Run select              
        if(RS.first()){                                               //If there is any line              
            RS.last();
            CollumnNames=new String[RS.getRow()];                     //Set the collumn Names Array's size
            RS.beforeFirst();                                         //Set the cursor back to 0
            int Index=0;                                              
            while(RS.next()){                      
                this.CollumnNames[Index]=RS.getString(1);             //Add the collumn name into the array
                Index++;
            }
            return true;                                              //True if there was any result
        }
        for(String cn:this.CollumnNames){
            //System.out.println(cn);
            break;
        }
        return false;                                                 //False if there wasnt result.
    }
    private boolean selectRows() throws SQLException{                 //Select the rows from the table
        String state="SELECT ";
        int Last=this.CollumnNames.length;                            //Get the Last collumns index
        int Index=0;
        for(String Collumn:this.CollumnNames){                        //Add the collumn names to the select
            state+="`"+Collumn+"`";
            if(Index<Last-1) state+=", ";                               //Add , after the collumn names unless its the last.
            Index++;
        }
        state+=" FROM "+this.Table;    
        PreparedStatement Statement = this.SQL.Connect.prepareStatement(state); //Create statement
        ResultSet RS=Statement.executeQuery();                                  //Run select
        if(RS.first()){                                                         //If there wasn any reuslt
            RS.last();
            this.TableRows=new String[RS.getRow()+1][Last];                     //Create the Rows array [number of rows][number of collumns]
            RS.beforeFirst();                                                   //Back to 0
            int RowIndex=1;
            while(RS.next()){
                for(int CollumnIndex=1; CollumnIndex<=Last; CollumnIndex++){            //it will run exactly as long it gtes the last collumn
                    this.TableRows[RowIndex][CollumnIndex-1]=RS.getString(CollumnIndex);//Insert into the [rownumber][collumn]
                }
                RowIndex++;
            }
            this.Log.add(this.Table+" selected... OK");
            return true;
        }
        this.Log.add(this.Table+" selected... ERROR");
        return false;
    }
    private void addCollumnNames(){
        this.TableRows[0]=this.CollumnNames;
    }
    public String[][] getTable(){
        if(this.TableRows==null) this.TableRows=new String[][]{};
        return this.TableRows;
    }
    public ArrayList<String> getLog() {
        return Log;
    }
}
