package software.ulpgc.arquitecture.model;

public interface Image {
    String id();
    Image next();
    Image prev();
}
