package com.company.s178201.World.Organisms.Animals;
import com.company.s178201.World.Organism;
import com.company.s178201.World.World;

public class Skunk extends Animal {
    public Skunk() { super(); initialize(); }
    public Skunk(int x, int y) { super(x, y); initialize(); }
    public Skunk(int x, int y, World world) { super(x, y, world); initialize(); }

    public String getSpecies() { return "Skunk"; }
    protected void createNewborn(int x, int y) {
        super.createNewborn(new Skunk(x, y));
    }
    protected void initialize() {
        initiative_ = 3;
        power_ = 2;
        id_ = 'X';
    }

    //IF SKUNK IS ATTACKED, ATTACKER MOVES BACK
    protected boolean isCombatAllowed(Animal enemyAnimal) {
        if (isSteady == true) {
            enemyAnimal.moveBack();
            world_.updateLog("Skunk Odour caused: " + enemyAnimal.getSpecies() + " to move back");
            return false;
        }
        else
            return true;
    }

    @Override

    //IF SKUNK ATTACKS, ENEMY POWER -3
    public void collision(Organism enemyOrganism) {
        if((enemyOrganism instanceof Animal) && (enemyOrganism.getId() != ('X'))) {
            ((Animal) enemyOrganism).decreasePower(3);
            world_.updateLog("Skunk Odour reduced: " + enemyOrganism.getSpecies() + " power by 3");
        }
        super.collision(enemyOrganism);
        if((enemyOrganism instanceof Animal) && (enemyOrganism.getId() != ('X'))) {
            ((Animal) enemyOrganism).increasePower(3);
        }
    }
}