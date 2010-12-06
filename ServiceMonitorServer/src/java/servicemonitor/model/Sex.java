/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

/**
 *
 * @author ktuomain
 */
public enum Sex {
    MALE, FEMALE, UNKNOWN;

    public int getIntValue() {
       switch(this) {
          case MALE:
              return 1;
          case FEMALE:
              return 2;
        }
        return 0;
    }

    public String toString() {
       switch(this) {
          case MALE:
              return "male";
          case FEMALE:
              return "female";
        }
        return "n/a";
    }

}

