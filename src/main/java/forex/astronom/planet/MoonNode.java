package forex.astronom.planet;

import java.util.Calendar;

import forex.astronom.ActivePoint;
import forex.astronom.util.*;

public class MoonNode extends ActivePoint {

  private double position;

  public MoonNode() {
  }

  public String getName() {
  	return SolarSystem.MOON;
  }

	public String getIcon() {
		return " ";
	}

  public boolean position(double t) {
    double t2 = t * t;
    double t3 = t2 * t;
    double t4 = t3 * t;

    double l = 125.0446;
    l = l - 1934.13618 * t;
    l = l + 20.76210E-4 * t2;
    l = l + 2.139E-6 * t3;
    l = l - 1.650E-8 * t4;
    l = l - 1.4979 * FNsin(49.1562 - 75869.8120 * t + 35.458E-4 * t2 + 4.231E-6 * t3 - 2.001E-8 * t4);
    l = l - 0.1500 * FNsin(357.5291 + 35999.0503 * t - 1.536E-4 * t2 + 0.041E-6 * t3);

    position = Zodiac.degree(l);
    return true;
  }

  private double FNsin(double a) {
    return Math.sin(Math.PI / 180 * a);
  }

  @Override
  public double getPosition(Calendar calendar) {
    return position;
  }

}

/*
http://www.true-node.com/eph1/?inputNumber=Moon&day=1&month=1&year=2012&time=00%3A00%3A00&zone=GMT+0&interval_num=10&interval=day&lines=100&.submit=Get+Ephemeris&calendar=Gregorian&zodiac=Tropical&origin=Geocentric&lighttime=Apparent&clock=Universal+Time&zodsec=D%B0M&nnode=on

    Date     |    Moon    |
             |            |
             |    NNOD    |
---------------------------
01 Jan 2012  |   13 sa 58 |
11 Jan 2012  |   13 sa 49 |
21 Jan 2012  |   13 sa 28 |
31 Jan 2012  |   12 sa 25 |
10 Feb 2012  |   11 sa 35 |
20 Feb 2012  |   10 sa 53 |
01 Mar 2012  |    9 sa 29 |
11 Mar 2012  |    8 sa 17 |
21 Mar 2012  |    7 sa 32 |
31 Mar 2012  |    6 sa 41 |
10 Apr 2012  |    5 sa 53 |
20 Apr 2012  |    5 sa 26 |
30 Apr 2012  |    5 sa 19 |
10 May 2012  |    5 sa  7 |
20 May 2012  |    5 sa  3 |
30 May 2012  |    5 sa  3 |
09 Jun 2012  |    5 sa  0 |
19 Jun 2012  |    5 sa  3 |
29 Jun 2012  |    4 sa 41 |
09 Jul 2012  |    4 sa 10 |
19 Jul 2012  |    3 sa 53 |
29 Jul 2012  |    3 sa  8 |
08 Aug 2012  |    1 sa 56 |
18 Aug 2012  |    1 sa  7 |
28 Aug 2012  |    0 sa 19 |
07 Sep 2012  |   28 sc 59 |
17 Sep 2012  |   28 sc  1 |
27 Sep 2012  |   27 sc 31 |
07 Oct 2012  |   26 sc 50 |
17 Oct 2012  |   26 sc 18 |
27 Oct 2012  |   26 sc 10 |
06 Nov 2012  |   26 sc  6 |
16 Nov 2012  |   26 sc  3 |
26 Nov 2012  |   26 sc  6 |
06 Dec 2012  |   25 sc 53 |
16 Dec 2012  |   25 sc 47 |
26 Dec 2012  |   25 sc 40 |
05 Jan 2013  |   24 sc 43 |
15 Jan 2013  |   23 sc 58 |
25 Jan 2013  |   23 sc 26 |
04 Feb 2013  |   22 sc  4 |
14 Feb 2013  |   20 sc 45 |
24 Feb 2013  |   20 sc  1 |
06 Mar 2013  |   19 sc  5 |
16 Mar 2013  |   18 sc  0 |
26 Mar 2013  |   17 sc 32 |
05 Apr 2013  |   17 sc 17 |
15 Apr 2013  |   16 sc 55 |
25 Apr 2013  |   16 sc 50 |
05 May 2013  |   16 sc 52 |
15 May 2013  |   16 sc 49 |
25 May 2013  |   16 sc 51 |
04 Jun 2013  |   16 sc 42 |
14 Jun 2013  |   16 sc 10 |
24 Jun 2013  |   15 sc 56 |
04 Jul 2013  |   15 sc 29 |
14 Jul 2013  |   14 sc  8 |
24 Jul 2013  |   13 sc 25 |
03 Aug 2013  |   12 sc 46 |
13 Aug 2013  |   11 sc 13 |
23 Aug 2013  |   10 sc 16 |
02 Sep 2013  |    9 sc 45 |
12 Sep 2013  |    8 sc 52 |
22 Sep 2013  |    8 sc 12 |
02 Oct 2013  |    8 sc  2 |
12 Oct 2013  |    7 sc 52 |
22 Oct 2013  |    7 sc 43 |
01 Nov 2013  |    7 sc 45 |
11 Nov 2013  |    7 sc 42 |
21 Nov 2013  |    7 sc 36 |
01 Dec 2013  |    7 sc 30 |
11 Dec 2013  |    7 sc  1 |
21 Dec 2013  |    6 sc 11 |
31 Dec 2013  |    5 sc 41 |
10 Jan 2014  |    4 sc 45 |
20 Jan 2014  |    3 sc  3 |
30 Jan 2014  |    2 sc 19 |
09 Feb 2014  |    1 sc 31 |
19 Feb 2014  |   29 li 58 |
01 Mar 2014  |   29 li 25 |
11 Mar 2014  |   29 li 10 |
21 Mar 2014  |   28 li 33 |
31 Mar 2014  |   28 li 23 |
10 Apr 2014  |   28 li 26 |
20 Apr 2014  |   28 li 22 |
30 Apr 2014  |   28 li 24 |
10 May 2014  |   28 li 16 |
20 May 2014  |   28 li  0 |
30 May 2014  |   27 li 47 |
09 Jun 2014  |   27 li 15 |
19 Jun 2014  |   26 li 24 |
29 Jun 2014  |   25 li 30 |
09 Jul 2014  |   24 li 45 |
19 Jul 2014  |   23 li 40 |
29 Jul 2014  |   22 li 17 |
08 Aug 2014  |   21 li 43 |
18 Aug 2014  |   21 li  1 |
28 Aug 2014  |   19 li 55 |
07 Sep 2014  |   19 li 42 |
17 Sep 2014  |   19 li 34 |
*/