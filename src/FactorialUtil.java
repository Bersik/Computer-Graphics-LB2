import java.math.BigDecimal;
import java.util.HashMap;

public class FactorialUtil {
    private static HashMap<Integer,BigDecimal> cache = new HashMap<Integer,BigDecimal>();
    public static BigDecimal max = BigDecimal.ZERO;

    public static BigDecimal factorial(int n) {
        BigDecimal ret;

        if (n == 0) return BigDecimal.ONE;
        if (null != (ret = cache.get(n))) return ret;
        ret = BigDecimal.valueOf(n).multiply(factorial(n-1));
        cache.put(n, ret);
        if (ret.compareTo(max)>0)
            max=ret;
        return ret;
    }
}