package com.company.s178201.World.Organisms.Plants;
import com.company.s178201.World.World;

final public class Thorn extends Plant {
    public Thorn() { super(); initialize(); }
    public Thorn(int x, int y) { super(x, y); initialize(); }
    public Thorn(int x, int y, World world) { super(x, y, world); initialize(); }
    public String getSpecies() {
        return "Thorn";
    }
    protected void createNewborn(int x, int y) {
        super.createNewborn(new Thorn(x, y));
    }
    protected void initialize() {
        power_ = 0;
        id_ = 't';
    }

    @Override
    public void action() {
           multiplication();
    }
}

