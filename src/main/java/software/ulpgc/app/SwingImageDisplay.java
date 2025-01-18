package software.ulpgc.app;

import software.ulpgc.arquitecture.model.Image;
import software.ulpgc.arquitecture.view.ImageDisplay;
import software.ulpgc.arquitecture.view.ViewPort;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.awt.Color.BLACK;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    public Image image;
    private BufferedImage bitmap;

    private Shift shift = Shift.Null;
    private Released released = Released.Null;
    private int initShift;
    private final List<Paint> paints = new ArrayList<>();

    public SwingImageDisplay(){
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
        setFocusable(true);
    }

    private MouseListener mouseListener() {
        return  new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {
                initShift = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released.offset(e.getX()-initShift);
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        };
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX()-initShift);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
    }

    @Override
    public void paint(String id, int offset) {
        paints.add(new Paint(id,offset));
        repaint();
    }

    @Override
    public void clear() {
        paints.clear();
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift != null ? shift : Shift.Null;
    }

    @Override
    public void on(Released released) {
        this.released = released != null ? released : Released.Null;
    }

    private record Paint(String id, int offset){
    }


    @Override
    public void show(Image image) {
        this.removeAll();
        this.image = image;
        this.bitmap = load(image.id());
        repaint();
    }

    @Override
    public Image image() {
        return image;
    }


    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        drawImages(g);
    }



    HashMap<String, BufferedImage> images = new HashMap<>();
    private void drawImages(Graphics g) {
        for (Paint paint : paints) {
            checkGallery(paint);
            bitmap = images.get(paint.id());
            ViewPort viewPort = ViewPort.ofSize(this.getWidth(), this.getHeight());
            ViewPort fitted = viewPort.fit(bitmap.getWidth(), bitmap.getHeight());
            int x =  ((this.getWidth()-fitted.width())/2);
            int y = ((this.getHeight()- fitted.height())/2);
            g.drawImage(bitmap,x+ paint.offset,y,fitted.width(),fitted.height(),null);
        }
    }

    private void checkGallery(Paint paint) {
        if (images.size()>10){
            images.clear();
        }
        if (!images.containsKey(paint.id())){
            images.put(paint.id(),load(paint.id()));
        }

    }

}
