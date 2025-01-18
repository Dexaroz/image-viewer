package software.ulpgc.arquitecture.control;

import software.ulpgc.arquitecture.model.Image;
import software.ulpgc.arquitecture.view.ImageDisplay;

import static software.ulpgc.arquitecture.view.ImageDisplay.*;

public class ImagePresenter {
    private final ImageDisplay display;
    private Image image;

    public ImagePresenter(ImageDisplay imageDisplay) {
        this.display = imageDisplay;
        this.display.on((Shift) offset -> shift(offset));
        this.display.on((Released) this::released);
    }

    private void released(int offset) {
        if (Math.abs(offset)>=display.getWidth()/2){
            image = offset>0 ? image.prev():image.next();
            display.show(image);
        }
        repaint();
    }

    private void repaint() {
        this.display.clear();
        this.display.paint(image.id(),0);
    }

    private void shift(int offset) {
        display.clear();
        display.paint(image.id(),offset);
        if (offset>0){
            display.paint(image.prev().id(),offset-display.getWidth());
        }else{
            display.paint(image.next().id(),display.getWidth()+offset);
        }

    }

    public void show(Image image){
        this.image = image;
        display.show(this.image);
        repaint();
    }
    public Image image(){
        return image;
    }
}
