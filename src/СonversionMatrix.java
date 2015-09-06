import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class СonversionMatrix {
    private static double[][] turnMatrix(int def){
        double rad = Math.PI*def/180;
        double[][] matrix = {
                {Math.cos(rad), -Math.sin(rad), 0},
                {Math.sin(rad), Math.cos(rad),  0},
                {0,             0,              1}
        };
        return matrix;
    }

    private static double[][] zoomMatrix(double mx,double my){
        double[][] matrix = {
                {mx,0,  0},
                {0, my, 0},
                {0, 0,  1}
        };
        return matrix;
    }

    private static double[][] mirrorXMatrix(){
        double[][] matrix = {
                {-1,0,  0},
                {0, 1, 0},
                {0, 0,  1}
        };
        return matrix;
    }

    private static double[][] mirrorYMatrix(){
        double[][] matrix = {
                {1, 0,  0},
                {0, -1, 0},
                {0, 0,  1}
        };
        return matrix;
    }

    private static double[][] moveMatrix(int dx,int dy){
        double[][] matrix = {
                {1, 0,  dx},
                {0, 1,  dy},
                {0, 0,  1}
        };
        return matrix;
    }

    private static double[][] createConversionsMatrix(HashMap<ConversionType,double[]> conversions){
        if (conversions.size()<1)
            return null;
        double[][] result=null;
        double[][] matrix;
        for(Entry<ConversionType, double[]> entry : conversions.entrySet()) {
            switch (entry.getKey()){
                case TURN:
                    matrix = turnMatrix((int) entry.getValue()[0]);
                    break;
                case ZOOM:
                    matrix = zoomMatrix(entry.getValue()[0], entry.getValue()[1]);
                    break;
                case MIRROR_X:
                    matrix = mirrorXMatrix();
                    break;
                case MIRROR_Y:
                    matrix = mirrorYMatrix();
                    break;
                case MOVE:
                    matrix = moveMatrix((int) entry.getValue()[0], (int) entry.getValue()[1]);
                    break;
                default:
                    matrix=null;
            }
            if (result==null){
                result=matrix;
            }else{
                result = multiply(result,matrix);
            }
        }
        return result;
    }


    private static double[][] multiply(double[][] A, double[][] B) {
        if (A==null || B==null)
            return null;
        int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] C = new double[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++)
                    C[i][j] += (A[i][k] * B[k][j]);
        return C;
    }

    // matrix-vector multiplication (y = A * x)
    private static Point multiply(double[][] A, Point p) {
        if (A==null || p==null)
            return null;
        double x[] = {p.x,p.y,1};
        int m = A.length;
        int n = A[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += (A[i][j] * x[j]);
        return new Point((int)(y[0]+0.5),(int)(y[1]+0.5));
    }

    public static ArrayList<Point> conversion(ArrayList<Point> points, HashMap<ConversionType,double[]> conversions){
        double[][] conversionsMatrix = createConversionsMatrix(conversions);
        ArrayList<Point> res = new ArrayList<Point>();
        for(Point p:points){
            res.add(multiply(conversionsMatrix,p));
        }
        return res;
    }
}
