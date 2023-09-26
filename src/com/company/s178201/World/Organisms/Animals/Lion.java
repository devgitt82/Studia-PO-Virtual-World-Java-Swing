package com.company.s178201.World.Organisms.Animals;
import com.company.s178201.World.World;

public class Lion extends Animal {
    public Lion() { super(); initialize(); }
    public Lion(int x, int y) { super(x, y); initialize(); }
    public Lion(int x, int y, World world) { super(x, y, world); initialize(); }

    public String getSpecies() { return "Lion"; }
    protected void createNewborn(int x, int y) {
        super.createNewborn(new Lion(x, y));
    }
    protected void initialize() {
        initiative_ = 7;
        power_ = 11;
        id_ = 'L';
    }
    protected boolean isCombatAllowed(Animal enemyAnimal) {
        if ((isSteady == true ) && (enemyAnimal.getPower() < 5 )){
            enemyAnimal.moveBack();
            world_.updateLog("Lion is too strong for: " + enemyAnimal.getSpecies());
            return false;
        }
        else
            return true;
    }
}