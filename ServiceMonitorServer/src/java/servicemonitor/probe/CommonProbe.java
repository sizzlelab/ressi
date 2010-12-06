/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.probe;

import java.util.List;
import servicemonitor.model.Location;
import servicemonitor.model.User;

/**
 *
 * @author ktuomain
 */
public class CommonProbe {
    private CosConnection cosConnection;
    private UserDAO userDAO = null;
    List<User> users = null;

    public void logout() {
        cosConnection.logout();
    }

    public void login() {
        cosConnection = new CosConnection();
        userDAO = new UserDAO(cosConnection);
        users = userDAO.getUsers(false);
    }

    public void printUserCount() {
        System.out.println(users.size());
    }

    public void printLocations()  {
        for (User i : users) {
            Location location = userDAO.getLocation(i.getId());
            if (location.isOK())
                System.out.println(location);
        }
    }

    public void printLatitudeAvg() {
        double sum = 0;
        int count = 0;

        for (User i : users) {
            Location location = userDAO.getLocation(i.getId());
            if (location.isOK()) {
                sum += location.getLatitude();
                count++;
            }
        }

        System.out.println((count > 0) ? (sum / (double) count) : "-1");
    }

    public void printLongitudeAvg() {
        double sum = 0;
        int count = 0;

        for (User i : users) {
            Location location = userDAO.getLocation(i.getId());
            if (location.isOK()) {
                sum += location.getLongitude();
                count++;
            }
        }

        System.out.println((count > 0) ? (sum / (double) count) : "-1");
    }


    public static void main(String[] args) {
        CommonProbe probe = new CommonProbe();
        probe.login();

        if (args.length == 1) {
            String parameter = args[0];
            if (parameter.equals("user_count"))
                probe.printUserCount();
            else if (parameter.equals("print_locations"))
                probe.printLocations();
            else if (parameter.equals("print_latitude_avg"))
                probe.printLatitudeAvg();
            else if (parameter.equals("print_longitude_avg"))
                probe.printLongitudeAvg();
            else
                System.out.println("error - unknown command: " + parameter);
        } else {
            System.out.println("args.length = " + args.length);
            probe.printUserCount();
            probe.printLocations();
            probe.printLatitudeAvg();
            probe.printLongitudeAvg();
        }

        probe.logout();
    }
}
