import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Bersik on 2015-04-17.
 */
public class BezierCurve {

    private static BigDecimal factorialN;
    // i - номер вершини, n – к-сть вершин, t – положення кривої (від 0 до 1)
    private static BigDecimal getBezierBasis(int i, int n, double t) {
        // Факториал. Рахуємо i-й элемент полинома Берштейна
        return (factorialN.divide(FactorialUtil.factorial(i).multiply(FactorialUtil.factorial(n - i)))).multiply(BigDecimal.valueOf(Math.pow(t, i) * Math.pow(1 - t, n - i)));
    }

    public static ArrayList<Point> bezierCurve(ArrayList<Point> arr,double step) {
        ArrayList<Point> res = new ArrayList<Point>();
        int count = arr.size();
        factorialN = FactorialUtil.factorial(count - 1);
        for(double t=0;t<1+step;t+=step){
            if (t > 1) {
                t = 1;
            }
            BigDecimal x=BigDecimal.ZERO;
            BigDecimal y=BigDecimal.ZERO;
            for (int i = 0; i < count; i++) {
                BigDecimal b = getBezierBasis(i, count - 1, t);
                Point p =arr.get(i);
                x=x.add((BigDecimal.valueOf(p.x)).multiply(b));
                y=y.add((BigDecimal.valueOf(p.y)).multiply(b));
            }
            res.add(new Point((int)(x.doubleValue()+0.5), (int)(y.doubleValue()+0.5)));
        }
        return res;
    }
}
