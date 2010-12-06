/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.probe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import servicemonitor.model.*;
import org.json.*;

/**
 *
 * @author ktuomain
 */
public class ChannelDao {

    public CosConnection cc;

    private void debugPrint(String msg) {
        //System.out.println("\n----- " + msg);
    }

    public ChannelDao(CosConnection cc) {
        this.cc = cc;
    }

    private Double normalizeNulls(String number) {
        return number.equals("null") ? null : Double.parseDouble(number);
    }

    private Date parseDate(String date) {
        Date result = null;

        try {
            //2009-09-05T19:41:32Z
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            result = (Date) dateFormat.parse(date);
        } catch (Exception ex) {
        }

        return result;
    }
    private Map<String, Channel> channelsByName = null;

    public Channel getChannelByName(String channelName) {
        if (channelsByName == null) {
            getChannels();
        }
        return channelsByName.get(channelName);
    }

    public List<Channel> getChannels() {
        LinkedList<Channel> result = new LinkedList();
        channelsByName = new HashMap();

        try {
            BufferedReader bf = cc.doGetBr("channels");

            JSONTokener tokener = new JSONTokener(bf);
            JSONObject jObject = new JSONObject(tokener);
            JSONArray o = (JSONArray) jObject.get("entry");

            Map<String, User> usersById = new HashMap();

            for (int i = 0; i < o.length(); i++) {
                Channel channel = new Channel();
                JSONObject obj = (JSONObject) o.get(i);

                channel.setName(obj.getString("name"));

                String channelType = obj.getString("channel_type");
                if (channelType.equals("public")) {
                    channel.setChannel_type(ChannelType.PUBLIC);
                } else if (channelType.equals("private")) {
                    channel.setChannel_type(ChannelType.PRIVATE);
                } else {
                    channel.setChannel_type(ChannelType.UNKNOWN);
                }

                channel.setCreated_at(parseDate(obj.getString("created_at")));
                channel.setUpdated_at(parseDate(obj.getString("updated_at")));
                
                channel.setId(obj.getString("id"));

                result.add(channel);
                if (channel.getName() != null) {
                    channelsByName.put(channel.getName(), channel);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("Going to list channels");

        CosConnection cc = new CosConnection();
        ChannelDao channelDao = new ChannelDao(cc);
        List<Channel> channels = channelDao.getChannels();
        cc.logout();

        for (Channel i : channels) {
            System.out.println(i.getName());
        }
    }
}
