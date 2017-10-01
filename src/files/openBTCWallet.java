/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;


import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mxsxs2
 */
public class openBTCWallet extends Entries{
    private String GUID;
    private String Time;
    private String Creator;
    private String URL;
    private String Session;
    private final String ApiCode="daaa9b8c-fa02-46c4-b5a7-5efe11538f15";
    public openBTCWallet(String URL,String Session){
        this.URL=URL;
        this.Session=Session;
    }
    public void openOneWallet(){
        if(this.getDetails()){      //if the wallet exist for the option
            this.getAddresses();    //then fetch the addresses
        }
    }
    private boolean getDetails(){           //check and get the wallet details from the db by the session id
        ResultSet rs = this.SQL.PreparedSelect("btcwallet", new String[]{"guid","rnd","time","session"}, "`session`="+this.Session, "1", "");
        try {
            if(rs.first()){
                this.GUID=rs.getString("guid");
                this.Time=rs.getString("time");
                this.Creator=rs.getString("rnd");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(openBTCWallet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private boolean getAddresses(){        //get the active addresses and ballances from the wallet (it gets in bult rather then multiple queries)
        try {
            URLtoJSON URLtoJSON = new URLtoJSON();
            JSONObject json = URLtoJSON.open(this.URL+"merchant/"+this.GUID+"/list?password="+this.pswd()+"&api_code="+this.ApiCode);
            
                if(!json.isNull("error")) return false;               //If there is no transactions return false;
                JSONArray nameArray = json.names();
                JSONArray TotalArray = json.toJSONArray(nameArray);
                JSONArray JAddresses = TotalArray.getJSONArray(0);
                
                for(int i = 0; i < JAddresses.length(); i++){
                    String O="false";
                    String S="false";
                    String N="false";
                    String[] Label=JAddresses.getJSONObject(i).getString("label").split("_",-1);
                    if(Label.length==3){
                        O=Label[1];
                        S=Label[0];
                        N=Label[2];
                    }
                    String[] Matched=this.matchDB(JAddresses.getJSONObject(i).getString("address"));
                    String[] Details= new String[]{
                        "S: "+S,                                                                        //0 Transaction ID
                        Matched[0],                                                                     //1 Sender
                        Float.toString(JAddresses.getJSONObject(i).getLong("total_received")/100000000)+" (BTC)",//2 Amount in btc
                        O,                                                                              //3 Choosen option
                        "btc",                                                                          //4 Currency
                        "O: "+Matched[2],                                                               //5 Date
                        Matched[1]                                                                      //6 Senders nem or false                
                    };
                    if(!this.list.contains(Details)){
                        this.list.add(Details);
                        if(!"false".equals(O) && !O.equals("")){
                            this.setAmountsAndEntries(Integer.parseInt(O), JAddresses.getJSONObject(i).getLong("total_received"), Details[6]); //add the amount and rise the entries
                        }
                    }
                }
                return true;
        } catch (JSONException | NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }
    private String pswd(){          //generate the password from the details given
        try{
            PBEKeySpec spec = new PBEKeySpec(this.Creator.toUpperCase().toCharArray(), this.Time.getBytes(), 100, 512);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            //System.out.println("pswd="+this.toHex(skf.generateSecret(spec).getEncoded()));
            return this.toHex(skf.generateSecret(spec).getEncoded());
        }catch(InvalidKeySpecException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
    private String[] matchDB(String AddressDetails){      //check if the address is allocated in the database
        String state="SELECT `entry`.`sender`,"
                          + "`entry`.`nem`,"
                          + "`entry`.`option`"
                          + "FROM `entry` "
                          + "WHERE `entry`.`recipientaddress`=?"
                          + "AND   `entry`.`type`='btc'"
                          + "LIMIT 1";
        PreparedStatement statement=null;
        ResultSet RS=null;
        String nem="false";
        String sender="false";
        String option="false";
        try{
            statement=SQL.Connect.prepareStatement(state);
            statement.setString(1, AddressDetails);
            RS=statement.executeQuery();
            if(RS.first()){
                    sender=RS.getString("sender");
                    nem=RS.getString("nem");
                    option=RS.getString("option");
            }
        }catch(SQLException ex){
        }finally{
            org.apache.commons.dbutils.DbUtils.closeQuietly(statement);
            org.apache.commons.dbutils.DbUtils.closeQuietly(RS);
        }
        return new String[]{sender,nem,option};
    }
    private String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}
