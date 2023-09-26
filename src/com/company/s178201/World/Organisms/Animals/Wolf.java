package com.company.s178201.World.Organisms.Animals;
import com.company.s178201.World.World;

public class Wolf extends Animal {
    public Wolf() { super(); initialize(); }
    public Wolf(int x, int y) { super(x, y); initialize(); }
    public Wolf(int x, int y, World world) { super(x, y, world); initialize(); }

    public String getSpecies() { return "Wolf"; }
    protected void createNewborn(int x, int y) {
        super.createNewborn(new Wolf(x, y));
    }
    protected void initialize() {
        initiative_ = 5;
        power_ = 9;
        id_ = 'W';
    }
}
