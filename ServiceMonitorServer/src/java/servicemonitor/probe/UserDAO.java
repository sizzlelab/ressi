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
import java.text.DateFormat;
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

//TODO: UserDAO
//-getUsers
//   -getFriends?
/**
 *
 * @author ktuomain
 */
public class UserDAO {

    private CosConnection cosConnection;

    private void debugPrint(String msg) {
        //System.out.println("\n----- " + msg);
    }

    /*
    void search() {
    debugPrint("search");
    try {
    URLConnection conn = getUrlConnection("people?search=*");
    setCookies(conn);
    printInputStream(conn.getInputStream());
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    }
     */
    public UserDAO(CosConnection cosConnection) {
        this.cosConnection = cosConnection;
    }

    public Set<String> getFriendIds(String id) {
        Set<String> result = new HashSet();
        if (id.equals("")) {
            System.out.println("ERROR: Tyhjä userid, tutki!");
            return result;
        }

        debugPrint("getFriends");
        try {
            BufferedReader br = cosConnection.doGetBr("people/" + id + "/@friends");
            JSONTokener tokener = new JSONTokener(br);
            JSONObject jObject = new JSONObject(tokener);

            JSONArray o = (JSONArray) jObject.get("entry");
            for (int i = 0; i < o.length(); i++) {
                JSONObject obj = (JSONObject) o.get(i);
                result.add(obj.getString("id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private Double normalizeNulls(String number) {
        return number.equals("null") ? null : Double.parseDouble(number);
    }

    public Location getLocation(String id) {
        Location result = null;

        debugPrint("getLocation");
        try {
            BufferedReader br = cosConnection.doGetBr("people/" + id + "/@location");

            //TODO: FIXME
 /*           String line;
            System.out.println("getLocation():");
            while ((line = br.readLine()) != null) {
            System.out.println(line);
            }
             */
            JSONTokener tokener = new JSONTokener(br);
//            JSONObject jObject = new JSONObject(tokener);

            JSONObject entry = new JSONObject(tokener);
            JSONObject jObject = entry.getJSONObject("entry");

            result = new Location();

            String updatedAt = jObject.getString("updated_at");

            if (updatedAt != null) {
                try {
                    //    "updated_at": "2009-09-28T13:59:12Z",
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    Date date = (Date) formatter.parse(updatedAt);
                    result.setUpdatedAt(date);
                } catch (Exception ex) {
                    System.out.println("Invalid timestamp: " + updatedAt);
                    ex.printStackTrace();
                }
            }

            result.setLongitude(normalizeNulls(jObject.getString("longitude")));
            result.setLabel(jObject.getString("label"));
            result.setLatitude(normalizeNulls(jObject.getString("latitude")));
            result.setAccuracy(normalizeNulls(jObject.getString("accuracy")));

            /*
             * {"updated_at":"2009-03-10T07:55:00Z","longitude":
            24.827181,"label":"Otaniemi","latitude":60.185622,"accuracy":null}
             */

            /*
             * dY6KP-RMer3PL4aaWPfx7J doesn't have valid location
            {"entry":{"updated_at":"2010-01-25T12:45:49Z","accuracy":null,"latitude":60.210539102554,"label":"Verkatehtaanpolku","longitude":24.979283809662}}
            c9flMUROir3O7IaaWPfx7J doesn't have valid location
             *
             */

        } catch (Exception ex) {
            //TODO: FIXME
//            ex.printStackTrace();
        }

        return result;
    }
    private Map<String, User> usersById = new HashMap();

    public User getUserById(String userId) {
        if (usersById.isEmpty()) {
            getUsers(false);
        }
        return usersById.get(userId);
    }
    private Map<String, User> usersByUsername = new HashMap();

    public User getUserByUsername(String username) {
        if (usersByUsername.isEmpty()) {
            getUsers(false);
        }
        return usersByUsername.get(username);
    }

    private boolean isNull(Object o) {
        if (o == null)
            return true;
        if (o == org.json.JSONObject.NULL)
            return true;
        return false;
    }


    public List<User> getUsers(boolean fetchFriends) {
        LinkedList<User> result = new LinkedList();

        DateFormat formatter = new SimpleDateFormat("yyy-MM-dd");

        debugPrint("getUsers");
        try {
            //URLConnection conn = getUrlConnection("people?search='");
            BufferedReader bf = cosConnection.doGetBr("people");

            JSONTokener tokener = new JSONTokener(bf);
            JSONObject jObject = new JSONObject(tokener);
            JSONArray o = (JSONArray) jObject.get("entry");

            for (int i = 0; i < o.length(); i++) {
                try {
                    JSONObject obj = (JSONObject) o.get(i);
                    String userName = obj.getString("username");
                    if (isNull(userName)) {
                        System.err.println("Skipping user where username==null");
                        continue;
                    }

                    User user = new User(userName);

                    String birthdate = obj.getString("birthdate");
                    if (!isNull(birthdate) && !birthdate.equals("null")) {
                        try {
                            Date date = (Date) formatter.parse(birthdate);
                            user.setTimeOfBirth(date);
                        } catch (Exception ex) {
                            System.out.println("Error parsing date: " + birthdate);
                        }
                    }

                    System.out.println("Gender = " + obj.get("gender"));
                    //                            JSONObject gender = (JSONObject) obj.get("gender");
                    //            String sex = gender.getString("key");
                    //FIXME, oliko tuo muuttunut?

                    Object gender = obj.get("gender");
                    String sex = "";
                    try {
                        if (!isNull(gender)) {
                            sex = (String) gender;
                        }
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }

                    if (sex.equals("MALE")) {
                        user.setSex(Sex.MALE);
                    } else if (sex.equals("FEMALE")) {
                        user.setSex(Sex.FEMALE);
                    } else {
                        user.setSex(Sex.UNKNOWN);
                    }

                    user.setId(obj.getString("id"));
                    result.add(user);

                    usersById.put(user.getId(), user);
                    usersByUsername.put(user.getName(), user);
                    System.out.println("UserDAO: saving " + user.getName());
                } catch (Throwable ex) {
                    System.out.println("Aargh!!!!");
                    ex.printStackTrace();
                }
            }

            //Set friends for each user
            if (fetchFriends) {
                for (User i : result) {
                    Set<String> friendIds = getFriendIds(i.getId());
                    Set<User> friends = new HashSet();
                    for (String id : friendIds) {
                        User friend = usersById.get(id);
                        if (friend != null) {
                            friends.add(friend);
                        } else {
                            System.err.println("Error: user " + i.getName() + " has friend id=" + id + " who doesn't exist");
                        }
                    }
                    i.setFriends(friends);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public List<User> getUsers() {
        return getUsers(true);
    }

    public static void main(String[] args) {
        System.out.println("Going to list users and their friends");

        try {
            CosConnection cosConnection = new CosConnection();

            UserDAO userDAO = new UserDAO(cosConnection);
            List<User> users = userDAO.getUsers();
            cosConnection.logout();

            for (User i : users) {
                System.out.println(i.toString());
                for (User friend : i.getFriends()) {
                    System.out.println("\tfriend: " + friend.getName());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

