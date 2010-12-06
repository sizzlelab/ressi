/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.probe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.Channel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import servicemonitor.model.ChannelType;
import servicemonitor.model.User;

/**
 *
 * @author ktuomain
 */
public class CosConnection {
    private static String HOSTNAME = null;
    private static String USERNAME = null;
    private static String PASSWORD = null;

    private String cookie = "";

    public void init(String hostUrl, String userName, String password) {
        HOSTNAME = hostUrl;
        USERNAME = userName;
        PASSWORD = password;
    }

    private void debugPrint(String msg) {
        //System.out.println("\n----- " + msg);
    }

    private URLConnection getUrlConnection(String path) {
        URLConnection result = null;

        try {
            URL url = new URL(HOSTNAME + path);
            result = url.openConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private void handleHeader(Map<String, List<String>> headers) {
        for (String key : headers.keySet()) {
            if (key != null) {
                for (String i : headers.get(key)) {
                    if (key.equals("Set-Cookie")) {
                        String aCookie = i.substring(0, i.indexOf(";"));
                        cookie = aCookie;
                    }
                    System.out.println(key + ": " + i);
                }
            }
        }
    }

    private void printInputStream(InputStream stream) {
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
        String line;

//        System.out.println("------->");
        try {
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            rd.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void login() {
        debugPrint("login");

        try {
            String data = URLEncoder.encode("session[app_name]", "UTF-8") + "=" + URLEncoder.encode(USERNAME, "UTF-8");
            data += "&" + URLEncoder.encode("session[app_password]", "UTF-8") + "=" + URLEncoder.encode(PASSWORD, "UTF-8");

            URLConnection conn = getUrlConnection("session");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            handleHeader(conn.getHeaderFields());
            printInputStream(conn.getInputStream());

            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        debugPrint("logout");

        try {
            //Fake delete by passing magic variable _method
            String data = URLEncoder.encode("_method", "UTF-8") + "=" + URLEncoder.encode("DELETE", "UTF-8");

            URLConnection conn = getUrlConnection("session");
            setCookies(conn);

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            handleHeader(conn.getHeaderFields());
//            printInputStream(conn.getInputStream());

            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setCookies(URLConnection conn) {
        //System.out.println("setRequestProperty: Cookie, " + cookie);
        conn.setRequestProperty("Cookie", cookie);
    }

    public CosConnection() {
//        login();
    }

    public BufferedReader doGetBr(String name) throws Exception {
        StringBuffer result = new StringBuffer();

        try {
            URLConnection conn = getUrlConnection(name);
            setCookies(conn);

            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return bf;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String doGet(String name) throws Exception {
        StringBuffer result = new StringBuffer();

        BufferedReader bf = doGetBr(name);

        try {
            String str;
            while ((str = bf.readLine()) != null) {
                result.append(str);
            }
            bf.close();
        } catch (IOException ex) {
            bf.close();
            throw ex;
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("COS test");

        try {
            CosConnection cc = new CosConnection();

            // System.out.println("doGet(people): " + cc.doGet("people"));
            System.out.println("doGet(channels): " + cc.doGet("channels"));

            cc.logout();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

