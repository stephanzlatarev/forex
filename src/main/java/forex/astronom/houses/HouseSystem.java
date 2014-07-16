/**
 function midheaven(ra,ob){
 var x=Math.atan(Math.tan(ra)/Math.cos(ob))
 if (x<0) {x=x+Math.PI}
 if (Math.sin(ra)<0) {x=x+Math.PI}
 return FNmod(FNdeg(x))
 }

 function ascendant(ra,ob,la){
 asn=Math.atan(Math.cos(ra)/(-Math.sin(ra)*Math.cos(ob)-Math.tan(la)*Math.sin(ob)))
 if (asn<0) {asn=asn+Math.PI}
 if (Math.cos(ra)<0) {asn=asn+Math.PI}
 return FNmod(FNdeg(asn))
 }

 function fortune(p1,p2,p3){
 if (p3<p1) {g1=360+p3} else {g1=p3}
 g2=p1;dif=g1-g2;f=p2+dif
 if (f>360) {f=f-360}
 return f
 }

 function eastpoint(ra,ob){return ascendant(ra,ob,0)}

 function vertex(ra,ob,la){
 x=Math.cos(ra+Math.PI)
 y=-Math.sin(ra+Math.PI)*Math.cos(ob)-Math.sin(ob)/Math.tan(la)
 vt=Math.atan(x/y)
 if (vt<0) {vt=vt+Math.PI}
 if (x<0) {vt=vt+Math.PI}
 return FNdeg(vt)
 }

 //koch
 function koch(ra,ob,la) {
 a1=FNasn(Math.sin(ra)*Math.tan(la)*Math.tan(ob))
 for (i=1; i < 13; i++) {
 d=FNmod(60+30*i)
 a2=d/90-1;kn=1
 if (d>=180) {kn=-1;a2=d/90-3}
 a3=FNrad(FNmod(FNdeg(ra)+d+a2*FNdeg(a1)))
 x=Math.atan(Math.sin(a3)/(Math.cos(a3)*Math.cos(ob)-kn*Math.tan(la)*Math.sin(ob)))
 if (x<0) {x=x+Math.PI}
 if (Math.sin(a3)<0) {x=x+Math.PI}
 house[i]=FNmod(FNdeg(x))
 }
 return true
 }

 //placidus
 function placidus(ra,ob,la){
 mc=midheaven(ra,ob)
 house[4]=FNmod(mc+180)
 house[1]=ascendant(ra,ob,la)
 r1=ra+FNrad(30)
 house[5]=FNmod(plac(3,0,r1,ra,ob,la)+180)
 r1=ra+FNrad(60)
 house[6]=FNmod(plac(1.5,0,r1,ra,ob,la)+180)
 r1=ra+FNrad(120)
 house[2]=FNmod(plac(1.5,1,r1,ra,ob,la))
 r1=ra+FNrad(150)
 house[3]=FNmod(plac(3,1,r1,ra,ob,la))
 for (i=7; i < 13; i++) {house[i]=FNmod(house[i-6]+180)}
 return true
 }

 function plac(ff,y,r1,ra,ob,la){
 x=-1
 if (y == 1) {x=1}
 for (i=1; i < 11; i++){
 xx=FNacs(x*Math.sin(r1)*Math.tan(ob)*Math.tan(la))
 if (xx<0) {xx=xx+Math.PI}
 r2=ra+(xx/ff)
 if (y == 1) {r2=ra+Math.PI-(xx/ff)}
 r1=r2
 }
 lo=Math.atan(Math.tan(r1)/Math.cos(ob))
 if (lo<0) {lo=lo+Math.PI}
 if (Math.sin(r1)<0) {lo=lo+Math.PI}
 return FNdeg(lo)
 }

 **/

package forex.astronom.houses;

import forex.astronom.Time;
import forex.astronom.util.Zodiac;

public abstract class HouseSystem {

	Time event;

  protected HouseSystem(Time event) {
    this.event = event;
  }

  public abstract double getHouse(int number);

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[\n");

    for (int i = 1; i < 13; i++) {
      buffer.append(i);
      buffer.append(" at ");
      buffer.append(Zodiac.toString(getHouse(i), "DD ZZZ MM' SS\""));
      buffer.append("\n");
    }
    buffer.append("]");

    return buffer.toString();
  }

}