package com.company.s178201.World.Organisms.Plants;
import com.company.s178201.World.World;

public class Grass extends Plant {
    public Grass() { super(); initialize(); }
    public Grass(int x, int y) { super(x, y); initialize(); }
    public Grass(int x, int y, World world) { super(x, y, world); initialize(); }

    public String getSpecies() { return "Grass"; }
    protected void createNewborn(int x, int y) {
        super.createNewborn(new Grass(x, y));
    }
    protected void initialize() {
        power_ = 0;
        id_ = 'g';
    }
}
