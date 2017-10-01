/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;


import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mxsxs2
 */
public class CreateBTCWallet {
    protected final MysqlAccess SQL = new MysqlAccess();
    private String Password;
    private final String ApiCode="daaa9b8c-fa02-46c4-b5a7-5efe11538f15";
    private final String Mail="nem@mxsxs2.info";
    private String Rnd;
    private String Time;
    
    public boolean Create(){
        this.Password=this.pswd();
        if(this.Password!=null){
            return this.RequestWallet();
        }
        return false;
    }
    private boolean RequestWallet(){
        try {
            URLtoJSON URLtoJSON = new URLtoJSON();
            JSONObject json = URLtoJSON.open("https://blockchain.info/en/api/v2/create_wallet?password="+this.Password+"&api_code="+this.ApiCode+"&email="+this.Mail);
            System.out.println("tried");
            if(!json.isNull("error")) return false;               //If there is no transactions return false;
            if(json.getString("guid")!=null && json.getString("address")!=null){
                return this.insertToDB(json.getString("guid"),json.getString("address"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
private String pswd(){          //generate the password from the details given
        try{
            this.Rnd=Integer.toString(new java.util.Random().nextInt());
            java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.ENGLISH);
            this.Time=format.format(new java.util.Date()); 
            PBEKeySpec spec = new PBEKeySpec(this.Rnd.toUpperCase().toCharArray(), this.Time.getBytes(), 100, 512);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            System.out.println("pswd="+this.toHex(skf.generateSecret(spec).getEncoded()));
            return this.toHex(skf.generateSecret(spec).getEncoded());
        }catch(InvalidKeySpecException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
private boolean insertToDB(String GUID,String Address){
    if(this.SQL.PreparedInsert("btcwallet", new String[]{"guid","address","time","rnd","session"},new String[]{GUID,Address,this.Time,this.Rnd,"0"})){
        SendMail SM=new SendMail();
                 String Subject="New Blochain wallet was created.";
                 String Message="There is a new wallet generated at "+this.Time+" with "+this.Rnd+"<br>\n";
                        Message+="The new wallet id is "+GUID+"<br>\n";
                        Message+="The new address is "+Address;
                    
                 SM.setValues("nem@mxsxs2.info", "NemSessionCloser", Message, Subject);
                 SM.send();
                 return true;
    }
    return false;
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

