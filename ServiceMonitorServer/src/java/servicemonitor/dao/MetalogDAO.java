/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.dao;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import servicemonitor.model.*;
import servicemonitor.model.UserDistribution;

/**
 *
 * @author ktuomain
 */
public class MetalogDAO {
    Collection<MetalogRow> metalogRows = new LinkedList();
    Random random = new Random(12345);

    private Collection<UserDistribution> getRandomUserDistribution() {
        Collection<UserDistribution> result = new LinkedList();

        for (int i=18; i < 65; i += random.nextInt(5)) {
            UserDistribution male = new UserDistribution();
            UserDistribution female = new UserDistribution();

            male.setAge(i);
            female.setAge(i);
            male.setSex(Sex.MALE);
            female.setSex(Sex.FEMALE);
            male.setCount(random.nextInt(20));
            female.setCount(random.nextInt(20));

            result.add(male);
            result.add(female);
        }

        return result;
    }

    public MetalogDAO() {
      long currentTime = System.currentTimeMillis();

      for (int i = 0; i < 100; i++) {
          MetalogRow metalogRow = new MetalogRow();
          metalogRow.setDate(new Date(currentTime - i * 60 * 1000 * 5));
          metalogRow.setPrivateChannelCount(i);
          metalogRow.setPublicChannelCount(i);
          metalogRow.setUserDistributions(getRandomUserDistribution());
          metalogRows.add(metalogRow);
      }
    }

    public Collection<MetalogRow> getMetaLogs()  {
        return metalogRows;
    }
}

