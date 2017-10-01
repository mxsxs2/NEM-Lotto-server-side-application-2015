/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mxsxs2
 */
public class URLtoJSON {
    public JSONObject open(String URL){
        try {
            InputStream is = new URL(URL).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, java.nio.charset.Charset.forName("UTF-8")));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = rd.read(chars)) != -1) buffer.append(chars, 0, read);
            //System.out.println(buffer.toString());
            is.close();
            return new JSONObject(buffer.toString()); 
        } catch (MalformedURLException ex) {
            Logger.getLogger(URLtoJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException |JSONException ex) {
            Logger.getLogger(URLtoJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
