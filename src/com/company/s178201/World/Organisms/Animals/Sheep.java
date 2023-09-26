package com.company.s178201.World.Organisms.Animals;
import com.company.s178201.World.World;

public class Sheep extends Animal {
    public Sheep() { super(); initialize(); }
    public Sheep(int x, int y) { super(x, y); initialize(); }
    public Sheep(int x, int y, World world) { super(x, y, world); initialize(); }

    public String getSpecies() { return "Sheep"; }
    protected void createNewborn(int x, int y) {
        super.createNewborn(new Sheep(x, y));
    }
    protected void initialize() {
        initiative_ = 4;
        power_ = 4;
        id_ = 'S';
    }

}
