/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mxsxs2
 */
public class updateBTCEntryValue {
    protected final MysqlAccess SQL = new MysqlAccess();
    
    private String getSID(){
        try {
            ResultSet RS = SQL.PreparedSelect("session", new String[]{"SID"}, "", "", "`SID` Desc");
            if(RS.first()){
                return RS.getString("SID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateBTCEntryValue.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void updateFieldsInDB(){
        openBTCWallet W=new openBTCWallet("https://blockchain.info/en/",this.getSID());
                      W.openOneWallet();
                      String[] Values=new String[]{W.getEachEntries()[0][0]+"_"+W.getEachEntries()[0][1],
                                                   W.getEachEntries()[1][0]+"_"+W.getEachEntries()[1][1],
                                                   W.getEachEntries()[2][0]+"_"+W.getEachEntries()[2][1],
                                                   W.getEachEntries()[3][0]+"_"+W.getEachEntries()[3][1]
                      };
        SQL.PreparedUpdate("amounts", new String[]{"btc1","btc2","btc3","btc4"}, Values, "`id`=1", "1");        
    }
    
}
