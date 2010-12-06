/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

/**
 *
 * @author ktuomain
 */
public enum ChannelType {
    PRIVATE, PUBLIC, UNKNOWN;

    public String toString() {
       switch(this) {
          case PRIVATE:
              return "private";
          case PUBLIC:
              return "public";
          case UNKNOWN:
              return "unknown";
        }
        return "n/a";
    }
}

