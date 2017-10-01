/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC'
 */
public class MysqlAccess {
    public Connection Connect;   
    public MysqlAccess(){
        try{
            this.MakeConnection();
        }catch(Exception e){
            System.err.println("Caught Exception: " + e.getMessage());
        }
    }
    private void MakeConnection() throws Exception{
        String url = "jdbc:mysql://127.0.0.1:3326/";
        String dbName = "nemlotto";
        String userName = "updater"; 
        String password = "uppass01";
        /*String url = "jdbc:mysql://localhost:3306/";
        String dbName = "nemlotto";
        String userName = "root"; 
        String password = "";*/
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        Connect = DriverManager.getConnection(url+dbName,userName,password);
        //System.out.println("Connected");
    }
    public Connection getConnect(){
        return Connect;
    }
    public boolean PreparedInsert(String Table, String Collumn[],String Value[]){
       /*The Method is populating a specifyed table with the given data.
        The StatementPart is an array what ha to contain
            The table    => 0
            The collumns => 1
            The values   => 2
       */
            PreparedStatement statement=null;
            int RowsAffected=0;
            try{   
                   if(Collumn.length==Value.length){
                        //System.out.println("Same length: "+Value.length);
                        int statementCollumn=1;    //Has to start from one because the 0 is the table in the statement        
                        String Collumns="";
                        String Values="";
                        String StatementMarks="";
                        int CnVLength=Value.length;
                        for(int i=0; i<CnVLength; i++){
                            Collumns +="`"+Collumn[i]+"`";
                            Values   +="'"+Value[i]+"'";
                            StatementMarks+="?";
                            if(i<(CnVLength-1)){
                                Collumns +=", ";
                                Values +=", ";
                                StatementMarks+=", ";
                            }
                        }
                        //System.out.println(Collumns);
                        //System.out.println(Values);
                        statement= Connect.prepareStatement(
                                          "INSERT INTO `"+Table+"`"
                                        + "("+Collumns+") "
                                        + "VALUES "
                                        + "("+StatementMarks+")");
                        for(int i=1; i<=CnVLength; i++){
                            //System.out.println(i+"_"+Value[i-1]);
                            statement.setString(i, Value[i-1]);
                        }
                        RowsAffected=statement.executeUpdate();
                   }
            }catch(Exception e){
                System.out.println(e.getMessage());
                return false;
            }finally{
                org.apache.commons.dbutils.DbUtils.closeQuietly(statement);
            }
        return RowsAffected>0;
   }
   public ResultSet PreparedSelect(String Table, String Collumn[], String WhereClause, String Limit, String OrderBy){
       String Collumns="";
       int CollLength=Collumn.length;
       PreparedStatement state;
       ResultSet Rs=null;
       for(int i=0;i<CollLength; i++){
           if(!Collumn[i].equals("*")){
               String C=Collumn[i].toLowerCase();
               String Field;
               String AS="";
               if(C.contains("as")){
                    String[] S=C.split(" ",2);
                    AS=S[1];
                    Field=S[0];
               }else{
                   Field=C;
               }
               if(Field.contains(".")){
                    String[] WithTableName=Field.split(java.util.regex.Pattern.quote("."));
                    for(String t:WithTableName){
                        System.out.println(t);
                    }
                    //System.out.println(Field);
                    Field="`"+WithTableName[0]+"`";
                    Field+=".`"+WithTableName[1]+"`";
                    
               }else{
                   Field="`"+C+"`";
               }
               Collumns+=Field+" "+AS;
               
           }else{
               Collumns+="*";
           }
           
           if(i<(CollLength-1)){
               Collumns+=", ";
           }
           
       }
       
       String[] Tables=Table.split(",");
       Table="";
       for(int t=0;t<Tables.length; t++){
           Table+="`"+Tables[t]+"`";
           if(t<Tables.length-1){
               Table+=",";
           }
       }
       String statement="SELECT "+Collumns+" FROM "+Table;
       if(!WhereClause.equals("")){
           statement +=" WHERE "+WhereClause;
       }
       if(OrderBy!=null && !OrderBy.equals("")){
           statement +=" ORDER BY "+OrderBy+"";
       }
       if(!Limit.equals("0") && !Limit.equals("")){
           statement +=" LIMIT "+Limit;
       }
       
        try {
            //System.out.println(statement);
            state= Connect.prepareStatement(statement);
            Rs=state.executeQuery(statement);
            if(!Rs.first()){
                Rs=null;
            }
            if(Rs!=null) Rs.beforeFirst();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }finally{
            //org.apache.commons.dbutils.DbUtils.closeQuietly(state);
            //org.apache.commons.dbutils.DbUtils.closeQuietly(Rs);
        }
        return Rs;
   }
        public Boolean PreparedUpdate(String Table, String Collumn[], String Value[], String Where, String Limit){
            if(Collumn.length==Value.length){
                        int statementCollumn=1;    //Has to start from one because the 0 is the table in the statement        
                        String Sets="";
                        String StatementMarks="";
                        int CnVLength=Value.length;
                        for(int i=0; i<CnVLength; i++){
                            Sets +="`"+Collumn[i]+"`=?";
                            if(i<(CnVLength-1)){
                                Sets +=", ";
                            }
                        }
                        if(Where==null || Where.equals("")) Where="1=1";
                        String statement ="UPDATE `"+Table+"` SET "+Sets+" WHERE "+Where;
                        if(!Limit.equals("") && !Limit.equals("0")){
                            statement +=" LIMIT "+Limit;
                        }
                        //System.out.println(statement);
                        //System.out.println(java.util.Arrays.toString(Value));
                        PreparedStatement state =null;
                        int RowsAffected =0;
                        try {
                            state = Connect.prepareStatement(statement);
                            for(int i=1; i<=CnVLength; i++){
                                state.setString(i, Value[i-1]);
                            }
                        RowsAffected=state.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(MysqlAccess.class.getName()).log(Level.SEVERE, null, ex);
                        }finally{
                            //org.apache.commons.dbutils.DbUtils.closeQuietly(state);
                        }
                        if(RowsAffected>0) return true;
            }
        return false;
   }
   public int PreparedDelete(String Table, String Where, String Limit){
       String statement = "DELETE FROM `"+Table+"` WHERE "+Where+" LIMIT "+Limit;
       PreparedStatement state=null;
       int RowsAffected=0;
        try {
            state = Connect.prepareStatement(statement);
            RowsAffected=state.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(MysqlAccess.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            org.apache.commons.dbutils.DbUtils.closeQuietly(state);
        }
        return RowsAffected;
   }
    @Override
   public void finalize(){
        try {
            org.apache.commons.dbutils.DbUtils.closeQuietly(this.Connect);
            super.finalize();
        } catch (Throwable ex) {}
   }
}