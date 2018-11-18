package com.marcelfiore.jungle_ai.game.gui;

import java.awt.*;

public class Background extends Canvas{

    Image img;

    public Background(String path){
        img = Toolkit.getDefaultToolkit().getImage(path);
    }

    public void paint(Graphics g){
        int width = getSize().width;
        int height = getSize().height;
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(img, 0, 0, width, height, this);
    }
}
