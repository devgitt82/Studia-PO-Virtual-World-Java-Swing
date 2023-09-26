package com.company.s178201.World.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class UIrefresher {

    public void showOrganismImage(int x, int y, char id) {
        Graphics g = organismGridPanel_.getGraphics();

        if(id == 'S') {
            Image img = setImage(image_, "sheep.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
        else if(id == 'W') {
            Image img = setImage(image_, "wolf.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
        else if(id == 'F') {
            Image img = setImage(image_, "fox.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
        else if(id == 'L') {
            Image img = setImage(image_, "lion.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
        else if(id == 'X') {
            Image img = setImage(image_, "Skunk.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
        else if(id== 'g')  {
            Image img = setImage(image_, "grass.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
        else if(id == 'u') {
            Image img = setImage(image_, "guarana.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
        else if(id == 't') {
            Image img = setImage(image_, "thorn.jpg");
            g.drawImage(img,x * gridSize_ + 1, y * gridSize_ + 1, organismGridPanel_);
            return;
        }
    }

    public void clearScreen(int N) {
        Graphics g = organismGridPanel_.getGraphics();
        Color backgroundColor  = organismGridPanel_.getBackground();
        g.setColor(backgroundColor);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j)
        g.fillRect(i * gridSize_ + 1, j * gridSize_ + 1, gridSize_ - 1, gridSize_ - 1);
        }
    }
    public void clearLogTextArea() {
        logTextArea_.setText("");
        logTextArea_.updateUI();
    }

    public void appendLogTextArea(String text) {
        logTextArea_.append("\n"+text);
        logTextArea_.updateUI();
    }

    public void setOrganismGridPanel(JPanel panel) {
        organismGridPanel_ = panel;
    }
    public void setLogTextArea(JTextArea logTextArea) {
        logTextArea_ = logTextArea;
    }
    public void setGridSize(int gridSize) {
        gridSize_ = gridSize;
    }

    private Image setImage(Image image, String path){
        File imageFile = new File(path);
        try {
            image = ImageIO.read(imageFile);
        }
        catch (IOException e) {
            System.err.println("Cannot read image");
            e.printStackTrace();
        }
        return image;
    }
    private JPanel organismGridPanel_;
    private JTextArea logTextArea_;
    private int gridSize_;
    private BufferedImage image_;
}
