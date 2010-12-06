/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.dao;

import java.util.*;
import servicemonitor.model.*;
import servicemonitor.probe.CosConnection;
import servicemonitor.probe.UserDAO;

/**
 *
 * @author ktuomain
 */
public class AggregatelogDAO {
    UserDAO userDAO;

    public AggregatelogDAO(CosConnection cosConnection) {
        userDAO = new UserDAO(cosConnection);
    }

    public AggregatelogDAO() {
        userDAO = new UserDAO(new CosConnection());
    }

    public Collection getAggegateLogs(User user) {
        Collection<AggregatelogRow> result = new LinkedList();

        long currentTime = System.currentTimeMillis();

          for (int i = 0; i < 100; i++) {
              AggregatelogRow row = new AggregatelogRow();
              row.setFriends(user.getFriends());

              row.setBirthDate(new Date(currentTime));
              row.setSex(Sex.MALE);
              row.setMessageReadCount(i);
              row.setMessageWrittenCount(i);
              row.setPrivateChannelsCreateCount(0);
              row.setPrivateChannelsVisibleCount(0);
              row.setPublicChannelsCreateCount(i);
              row.setPublicChannelsVisibleCount(0);

              row.setTimestamp(new Date(currentTime - i * 60 * 1000 * 5));
              result.add(row);
          }
        
        return result;
    }
    
    public Collection<User> getUsers() {
        return userDAO.getUsers(false);
    }
}

