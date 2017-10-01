/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.json.JSONException;

/**
 *
 * @author Mxsxs2
 */
public class CreateNXTWallet {
    protected final MysqlAccess SQL = new MysqlAccess();
    private String Rnd;
    private String Time;
    private String URL;
    private final java.util.ArrayList<String[]> Addresses=new java.util.ArrayList<>();
    public CreateNXTWallet(){
        try {
            ResultSet RS = this.SQL.PreparedSelect("siteaddress", new String[]{"nxt_address"}, "`id`=1", "1", "");
            if(RS.first()){
                this.URL =RS.getString("nxt_address");  
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreateNXTWallet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean Create(){
        for(int i=1; i<=4; i++){
            String[] address=this.generateAccount();
            if(address!=null){
                this.Addresses.add(new String[]{address[0],address[1],this.Time,this.Rnd});
            }
        }
        return this.insertToDB();
    }    
    private String[] generateAccount(){
        try {
            //change from this
            URL url = new URL(this.URL+"nxt");                                         //The url for nxt/nis
            Map<String,Object> params = new LinkedHashMap<>();                               //Parameters hasmap
            params.put("requestType", "getAccountId");                                       //.put(key, value)
            params.put("secretPhrase", this.pswd());
            ///change until this NOT MORE
            
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {                          //Add parameters
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");                               //set charset
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");                                                              //set request type
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            //System.out.println("Phrase: "+phrase);
            while ((line = br.readLine()) != null) {
                //check answer from the server line by line. or parse it to JSON
                sb.append(line);
                //System.out.println(line);
            }
            org.json.JSONObject json= new org.json.JSONObject(sb.toString());                       //arse to JSON
            if(json.isNull("errorCode")){                                                           //CHECK ERROR CODE
                //System.out.println(json.getString("accountRS"));
                //System.out.println(json.getString("publicKey"));
                return new String[]{json.getString("accountRS"),json.getString("publicKey")};
            }else{
                return null;
            }

        } catch (IOException | JSONException ex) {
            Logger.getLogger(CreateNXTWallet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private boolean insertToDB(){
        if(this.Addresses.size()<1) return false;
        String[] Values=new String[8];
        Values[0]=this.Addresses.get(0)[0];
        Values[1]=this.Addresses.get(1)[0];
        Values[2]=this.Addresses.get(2)[0];
        Values[3]=this.Addresses.get(3)[0];
        Values[4]=this.Addresses.get(0)[1];
        Values[5]=this.Addresses.get(1)[1];
        Values[6]=this.Addresses.get(2)[1];
        Values[7]=this.Addresses.get(3)[1];
        if(this.SQL.PreparedInsert("optionaddresses", new String[]{"nxt1","nxt2","nxt3","nxt4","nxtp1","nxtp2","nxtp3","nxtp4"}, Values)){
            try {
                ResultSet RS=this.SQL.PreparedSelect("optionaddresses", new String[]{"id"}, "", "1", "id desc");
                if(RS.first()){
                    String[] Details=new String[]{
                        this.Addresses.get(0)[3],
                        this.Addresses.get(1)[3],
                        this.Addresses.get(2)[3],
                        this.Addresses.get(3)[3],
                        this.Addresses.get(0)[2],
                        this.Addresses.get(1)[2],
                        this.Addresses.get(2)[2],
                        this.Addresses.get(3)[2],
                        RS.getString("id")
                    };
                    if(Details.length==9 && this.SQL.PreparedInsert("addressdetails", new String[]{"rnd1","rnd2","rnd3","rnd4","time1","time2","time3","time4","id"}, Details)){
                        //System.out.println("inserted");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(CreateNXTWallet.class.getName()).log(Level.SEVERE, null, ex);
            }
                SendMail SM=new SendMail();
                String Subject="New Next addresses were created.";
                String Message="4 new addresses were created with the following details: <br><br>\n";
                for(int i=0;i<=3;i++){
                    Message+=this.Addresses.get(i)[0]+" created at "+this.Addresses.get(i)[2]+" with "+this.Addresses.get(i)[3]+"<br>\n";
                    Message+="Public Key:  "+this.Addresses.get(i)[1]+"<br><br><br>\n\n\n";
                }
                
                
                SM.setValues("nem@mxsxs2.info", "NemSessionCloser", Message, Subject);
                SM.send();
                return true;
            
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
            //System.out.println("pswd="+this.toHex(skf.generateSecret(spec).getEncoded()));
            return this.toHex(skf.generateSecret(spec).getEncoded());
        }catch(InvalidKeySpecException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
    private String toHex(byte[] array) throws NoSuchAlgorithmException{
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
