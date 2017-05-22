package com.mirea.laba1;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by nikitos on 20.05.17.
 */
public class MyGraphic  extends JComponent {
    List<Map.Entry<String, Integer>> array;
    int width;
    int height;

    MyGraphic(List<Map.Entry<String, Integer>> array, int width, int height) {
        this.array = array;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(Color.WHITE);
        g2.setColor(Color.BLACK);
        float stepX = width/11;
        double scaleY = height/(array.get(0).getValue().doubleValue());
        for(int i = 0; i<array.size()-1;++i) {
            g2.drawLine((int)(stepX/2+(stepX*i)), height - (int) (array.get(i).getValue()*scaleY),
                    (int)(stepX/2+(stepX*(i+1))), height - (int)(array.get(i+1).getValue()*scaleY));
        }
    }
}
