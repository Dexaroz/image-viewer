package software.ulpgc.arquitecture.view;

import software.ulpgc.arquitecture.model.Image;

public interface ImageDisplay {

    void show(Image image);
    Image image();

    void paint(String id, int offset);
    int getWidth();
    void clear();

    void on(Shift shift);
    void on(Released released);

    interface Shift {
        Shift Null = offset->{};
        void offset(int offset);
    }


    interface Released {
        Released Null = offset->{};
        void offset(int offset);
    }
}
