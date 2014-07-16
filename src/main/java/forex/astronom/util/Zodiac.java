package forex.astronom.util;

public class Zodiac {

  public static String[] SIGN = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagitarius", "Capricorn", "Aquarius", "Piesces"};

  public static boolean isBetween(double a, double b, double c) {
    return degree(b - a) <= degree(c - a);
//    double aa = degree(a);
//    double bb = degree(b);
//    double cc = degree(c);
//
//    if (bb >= aa) {
//      if (cc < bb) {
//        return (cc < aa);
//      } else {
//        return true;
//      }
//    } else {
//      if (cc < aa) {
//        return (cc >= bb);
//      } else {
//        return false;
//      }
//    }
  }

  public static double degree(double degree) {
    degree %= 360;

    if (degree < 0) {
      degree += 360;
    }
    return degree;
  }

  /**
   * 
   * @param degree
   * @param format a string containing
   *               A - angle degrees
   *               D - degrees in sign
   *               M - minutes
   *               S - seconds
   *               Z - sign name
   * 
   * @return
   */
  public static String toString(double degree, String format) {
    int[] c = new int[5];
    String[] d = new String[5];
    char[] f = format.toCharArray();
    char[] r = new char[f.length];

    d[0] = format(degree, 3);
    d[1] = format(degree % 30, 2);
    d[2] = format((degree % 1) * 60, 2);
    d[3] = format((((degree % 1) * 60) % 1) * 60, 2);
    d[4] = SIGN[(int) (degree / 30)];

    for (int i = 0; i < r.length; i++) {
      switch (f[i]) {
        case 'A': {
          r[i] = d[1].charAt(c[01]);
          c[1]++;
          break;
        }
        case 'D': {
          r[i] = d[1].charAt(c[01]);
          c[1]++;
          break;
        }
        case 'M': {
          r[i] = d[2].charAt(c[2]);
          c[2]++;
          break;
        }
        case 'S': {
          r[i] = d[3].charAt(c[3]);
          c[3]++;
          break;
        }
        case 'Z': {
          r[i] = (d[4].length() > c[4]) ? d[4].charAt(c[4]) : ' ';
          c[4]++;
          break;
        }
        default: {
          r[i] = f[i];
        }
      }
    }
    return new String(r);
  }

  private final static String format(double data, int range) {
    String string = "     " + data;
    int es = string.indexOf('E');
    if (es > 0) {
      int e = Integer.parseInt(string.substring(es + 1));
      int index = string.indexOf('.');
      if (index < 0) {
        index = string.length();
      }
      String temp = string.substring(0, index - (e < 0 ? 1 : 0));
      if (e >= 0) {
        String pre = string.substring(index + 1, es);
        temp += pre;
        e -= pre.length();
      }
      for (int i = 0; i < Math.abs(e); i++) {
        temp += "0";
        if (i == 0 && e < 0) {
          temp += '.';
        }
      }
      if (e < 0) {
        temp += string.charAt(index - 1);
        temp += string.substring(index + 1, es);
      }
      string = temp;
    }
    int index = string.indexOf('.');
    if (index < 0) {
      index = string.length();
    }
    return string.substring(index - range);
  }
}