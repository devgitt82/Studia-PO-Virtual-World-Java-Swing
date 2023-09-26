package com.company.s178201.World.Organisms.Plants;
import com.company.s178201.World.Organisms.Animals.Animal;
import com.company.s178201.World.Organism;
import com.company.s178201.World.World;

public class Guarana extends Plant {
    public Guarana() { super(); initialize(); }
    public Guarana(int x, int y) { super(x, y); initialize(); }
    public Guarana(int x, int y, World world) { super(x, y, world); initialize(); }

    public String getSpecies() { return "Guarana"; }
    public void speciesSpecificCollision(Organism enemyOrganism) {
        //organism power increased by 3
        if(enemyOrganism instanceof Animal) {
            ((Animal) enemyOrganism).increasePower(3);
        }
    }
    protected void createNewborn(int x, int y) {
        super.createNewborn(new Guarana(x, y));
    }
    protected void initialize() {
        power_ = 0;
        id_ = 'u';
    }
}
