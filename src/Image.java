/**
 * Created by Bersik on 16.03.2015.
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class Image extends JPanel {
    public static int cx,cy;
    private BufferedImage imag;
    private Graphics2D graphics;
    private int height;
    private int width;

    public Image(int width, int height) {
        super();
        this.width=width;
        this.height=height;
        setBounds(0, 0, width, height);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) imag.createGraphics();
        cx = width / 2;
        cy = height / 2;
        graphics.translate(cx,cy);
        clear();
    }

    private void paintXY() {
        graphics.drawLine(0,-cy,0,height);
        graphics.drawLine(-cx,0,width,0);
        int dx=6;
        int dy=10;
        graphics.drawLine(-dx,-cy+dy,0,-cy);
        graphics.drawLine(0,-cy,dx,-cy+dy);
        graphics.drawLine(cx-dy,-dx,cx,0);
        graphics.drawLine(cx-dy,dx,cx,0);
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imag, 0, 0, this);
    }

    public void paintPixels(ArrayList<Point> points,Color color) {
        graphics.setColor(color);
        for (Point point : points) {
            paintPixel(point);
        }
        this.repaint();
    }

    public void clear() {
        graphics.setColor(Color.white);

        graphics.fillRect(-cx, -cy, width, height);
        graphics.setColor(Color.black);
        paintXY();
        this.repaint();
    }

    private void paintPixel(Point point) {
        graphics.fillRect(point.x, point.y, 1, 1);
    }

    public void paintPoint(ArrayList<Point> points) {
        int id = points.size();
        Point p = points.get(id - 1);
        int d = 3;
        Color tmp = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.format("%d) %d,%d", id, p.x, p.y), p.x + 7, p.y + 3);
        if (id > 1) {
            Point pPrev = points.get(id - 2);
            drawDottedLine(pPrev.x, pPrev.y, p.x, p.y);
            graphics.setColor(Color.BLUE);
            graphics.fillRect(pPrev.x - d, pPrev.y - d, d * 2, d * 2);
        }
        graphics.setColor(Color.BLUE);
        graphics.fillRect(p.x - d, p.y - d, d * 2, d * 2);
        graphics.setColor(tmp);
        this.repaint();
    }

    public void drawDottedLine(int x1, int y1, int x2, int y2) {
        Stroke tmp = graphics.getStroke();
        float dash[] = {3.0f};
        graphics.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));

        graphics.drawLine(x1, y1, x2, y2);
        graphics.setStroke(tmp);
        this.repaint();
    }

    public Point getXY(int x,int y){
        return new Point(x-cx,y-cy);
    }
}