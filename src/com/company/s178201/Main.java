package com.company.s178201;


import com.company.s178201.World.WorldEngine;

import java.awt.EventQueue;
public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WorldEngine();
            }
        });
    }
}
