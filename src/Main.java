import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bersik on 15.03.2015.
 */

public final class Main {
    private static int sizeWidth = 1000;
    private static int sizeHeight = 660;
    private final JCheckBox checkTurn,checkZoom,checkMirrorX,checkMirrorY,checkMove;
    private Form form;
    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Point> pointsCurve;
    private Image img;
    private JTextField textGradus,textZoomX,textZoomY,textMoveX,textMoveY;
    private boolean draw = true;

    private void clear(){
        img.clear();
        draw=false;
    }

    public Main(){
        form = new Form(sizeWidth,sizeHeight);

        img = new Image(sizeWidth-200,sizeHeight-29);
        img.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (draw == true) {
                    clear();
                    points.clear();
                }
                Point p = img.getXY(e.getX(), e.getY());
                points.add(p);
                img.paintPoint(points);
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        form.add(img);

        JButton buttonDraw = new JButton("Намалювати");
        buttonDraw.setBounds(sizeWidth - 180, 30, 140, 30);
        buttonDraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (draw==true){
                    clear();
                }
                if (points.size()>2) {
                    pointsCurve = Figure.drawCurve(points, 0.0001);
                    img.paintPixels(pointsCurve,Color.BLACK);

                    //різні перетворення
                    HashMap<ConversionType,double[]> conversions = new HashMap<ConversionType, double[]>();
                    if (checkTurn.isSelected()){
                        double[] param = {Integer.valueOf(textGradus.getText())};
                        conversions.put(ConversionType.TURN,param);
                    }
                    if (checkZoom.isSelected()){
                        double[] param = {Double.valueOf(textZoomX.getText()),Double.valueOf(textZoomY.getText())};
                        conversions.put(ConversionType.ZOOM,param);
                    }
                    if (checkMirrorX.isSelected()){
                        conversions.put(ConversionType.MIRROR_X,null);
                    }
                    if (checkMirrorY.isSelected()){
                        conversions.put(ConversionType.MIRROR_Y,null);
                    }
                    if (checkMove.isSelected()){
                        double[] param = {Integer.valueOf(textMoveX.getText()), Integer.valueOf(textMoveY.getText())};
                        conversions.put(ConversionType.MOVE,param);
                    }
                    if (conversions.size()>0){
                        img.paintPixels(СonversionMatrix.conversion(pointsCurve,conversions),Color.RED);
                    }
                    draw = true;
                }
            }
        });
        form.add(buttonDraw);

        checkTurn = new JCheckBox("Поворот");
        checkTurn.setBounds(sizeWidth - 170, 100, 70, 30);
        form.add(checkTurn);

        JLabel label = new JLabel("°");
        label.setBounds(sizeWidth - 51, 102, 20, 20);
        form.add(label);
        textGradus = new JTextField();
        textGradus.setText("0");
        textGradus.setBounds(sizeWidth - 95, 105, 40, 20);
        form.add(textGradus);

        checkZoom = new JCheckBox("Масштабування");
        checkZoom.setBounds(sizeWidth - 160, 140, 140, 30);
        form.add(checkZoom);


        label = new JLabel("x");
        label.setBounds(sizeWidth - 170, 170, 20, 20);
        form.add(label);
        textZoomX = new JTextField();
        textZoomX.setText("1.00");
        textZoomX.setBounds(sizeWidth - 160, 170, 40, 20);
        form.add(textZoomX);
        label = new JLabel("y");
        label.setBounds(sizeWidth - 90, 170, 20, 20);
        form.add(label);
        textZoomY = new JTextField();
        textZoomY.setText("1.00");
        textZoomY.setBounds(sizeWidth - 80, 170, 40, 20);
        form.add(textZoomY);


        checkMirrorY= new JCheckBox("Відобразити по Y");
        checkMirrorY.setBounds(sizeWidth - 160, 200, 140, 20);
        form.add(checkMirrorY);


        checkMirrorX= new JCheckBox("Відобразити по X");
        checkMirrorX.setBounds(sizeWidth - 160, 220, 140, 20);
        form.add(checkMirrorX);


        checkMove= new JCheckBox("Перенести");
        checkMove.setBounds(sizeWidth - 150, 250, 140, 20);
        form.add(checkMove);


        label = new JLabel("x");
        label.setBounds(sizeWidth - 170, 280, 20, 20);
        form.add(label);
        textMoveX = new JTextField();
        textMoveX.setText("0");
        textMoveX.setBounds(sizeWidth - 160, 280, 40, 20);
        form.add(textMoveX);
        label = new JLabel("y");
        label.setBounds(sizeWidth - 90, 280, 20, 20);
        form.add(label);
        textMoveY = new JTextField();
        textMoveY.setText("0");
        textMoveY.setBounds(sizeWidth - 80, 280, 40, 20);
        form.add(textMoveY);



        final JButton clear = new JButton("Очистити");
        clear.setBounds(sizeWidth - 180,sizeHeight - 120, 140, 30);
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                clear();
                points.clear();
            }
        });
        form.add(clear);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new  Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new  Main();
            }
        });
    }
}
