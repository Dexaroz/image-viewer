package software.ulpgc.app;

import software.ulpgc.arquitecture.control.Command;
import software.ulpgc.arquitecture.control.ImagePresenter;
import software.ulpgc.arquitecture.view.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final Map<String, Command> commands;
    private ImageDisplay imageDisplay;
    private ImagePresenter presenter;

    public MainFrame() throws HeadlessException {
        commands = new HashMap<>();
        setTitle("Image Viewer");
        setSize(1200,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createImageDisplay());
        add(createToolbar(),BorderLayout.SOUTH);
    }

    private Component createToolbar() {
        JPanel panel = new JPanel();
        panel.add(createButton("Previous"));
        panel.add(createButton("Next"));
        return panel;
    }

    private Component createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(label).execute();
            }
        });
        return button;
    }

    public void add(String name, Command command){ commands.put(name,command);}

    private KeyListener createkeyListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_RIGHT){
                    presenter.show(presenter.image().next());
                }else if (e.getKeyCode()==KeyEvent.VK_LEFT){
                    presenter.show(presenter.image().prev());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
    }


    public ImagePresenter presenter() {
        return presenter;
    }

    private Component createImageDisplay() {
        SwingImageDisplay display = new SwingImageDisplay();
        display.addKeyListener(createkeyListener());
        this.imageDisplay=display;
        presenter = new ImagePresenter(imageDisplay);
        return display;
    }

    public ImageDisplay imageDisplay(){
        return imageDisplay;
    }


}
