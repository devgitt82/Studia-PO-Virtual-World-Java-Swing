package com.company.s178201.World.Display;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    public GridPanel(int N, int cellDimension) { cellDimension_ = cellDimension; N_ = N; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        //GRID LINES , VERTICAL AND HORIZONTAL
        for(int i = -1; i <N_; i++) {
            g.drawLine(cellDimension_ * (i + 1), 0, cellDimension_ * (i + 1), cellDimension_ * N_);
            g.drawLine(0, cellDimension_ * (i + 1), cellDimension_ * N_, cellDimension_ * (i + 1));
        }
    }
    private int cellDimension_;
    private int N_;

}