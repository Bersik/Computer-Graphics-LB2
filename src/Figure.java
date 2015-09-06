import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Bersik on 16.03.2015.
 */
public class Figure {
    public static ArrayList<Point> drawCurve(ArrayList<Point> arr,double step) {
        return BezierCurve.bezierCurve(arr,step);
    }

    public static Point getCentre(ArrayList<Point> points){
        int minX=points.get(0).x;
        int minY=points.get(0).y;
        int maxX=0;
        int maxY=0;
        for (int i=1;i<points.size();i++){
            Point p = points.get(i);
            if (p.x<minX)
                minX = p.x;
            else if (p.x>maxX)
                maxX = p.x;
            if (p.y<minY)
                minY= p.y;
            else if (p.y>maxY)
                maxY = p.y;
        }
        return new Point(minX+(maxX-minX)/2,minY+(maxY-minY)/2);
    }

    public static ArrayList<Point> turn(ArrayList<Point> points,int def){
        Point center = getCentre(points);
        double rad = Math.PI*def/180;
        ArrayList<Point> result = new ArrayList<Point>();
        for (Point point:points){
            result.add(
                    new Point(
                            (int)(center.x+(point.x-center.x)*Math.cos(rad)-(point.y-center.y)*Math.sin(rad)+0.5),
                            (int)(center.y+(point.x-center.x)*Math.sin(rad)+(point.y-center.y)*Math.cos(rad)+0.5))
            );
        }
        return result;
    }

    public static ArrayList<Point> zoom(ArrayList<Point> points,double mx,double my){
        Point center = getCentre(points);

        ArrayList<Point> result = new ArrayList<Point>();
        for (Point point:points){
            result.add(
                    new Point(
                            (int)(center.x+(point.x-center.x)*mx+0.5),
                            (int)(center.y+(point.y-center.y)*my+0.5))
            );
        }
        return result;
    }

    public static ArrayList<Point> move(ArrayList<Point> points,int dx,int dy){
        ArrayList<Point> result = new ArrayList<Point>();
        for (Point point:points){
            result.add(
                    new Point(
                            (int)(point.x+dx+0.5),
                            (int)(point.y+dy+0.5))
            );
        }
        return result;
    }

    public static ArrayList<Point> mirror(ArrayList<Point> points) {
        Point center = getCentre(points);

        ArrayList<Point> result = new ArrayList<Point>();
        for (Point point:points){
            result.add(new Point(point.x,center.y+(center.y-point.y)));
        }
        return result;
    }
}
