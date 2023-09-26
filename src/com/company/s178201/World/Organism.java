package com.company.s178201.World;
import java.awt.Point;
import java.util.Random;

public abstract class Organism {

    protected Organism(int x, int y) { this(x, y, new World()); }
    protected Organism() {
        this(0, 0, new World());
    }
    protected Organism(int x, int y, World world) {
        setPosition(new Point(x, y));
        serialNo_ = objectCounter_;
        objectCounter_++;
        world_ = world;
        previousPosition_ = new Point(x, y);
    }

    public void setWorld(World world) {
        world_ = world;
    }
    public void action() {}
    public void speciesSpecificCollision(Organism enemyOrganism) {}
    public void collision(Organism enemyOrganism){}
    public void showOnGridPanel() {
        world_.getUIrefresher().showOrganismImage(position_.x, position_.y, id_);
    }
    public int getInitiative() { return initiative_; }
    public int getPower() { return power_; }
    public int getSerialNo() { return serialNo_; }
    public char getId() { return id_; }
    public static int getObjectCounter() { return objectCounter_; }
    public abstract String getSpecies();
    public Point getPosition() { return position_; }
    public void setPosition(Point position) {
        position_ = position;
    }
    public void setSerialNo(int serialNo) {  serialNo_ = serialNo; }
    public void setPower(int power) {  power_ = power; }
    public static void setObjectCounter(int objCounter) { objectCounter_= objCounter; }

    protected abstract void initialize();
    protected void kill(Organism organism) {
        world_.addToDefeatedAfterKilling(this, organism);
    }
    protected void eat(Organism organism) {
        world_.addToDefeatedAfterEating(this, organism);
        organism.isAlive_ = false;
        organism.speciesSpecificCollision(this);
    }
    protected void combat(Organism enemy) {
        if (power_ > enemy.getPower()) {
            world_.addToDefeatedAfterKilling(this, enemy);
            enemy.isAlive_ = false;
        }
        else if (power_ < enemy.getPower()) {
            world_.addToDefeatedAfterKilling(enemy, this);
            this.isAlive_ = false;
        }
        else {
            if (initiative_ > enemy.getInitiative()) {
                world_.addToDefeatedAfterKilling(this, enemy);
                enemy.isAlive_ = false;
            }
            else if (initiative_ < enemy.getInitiative()) {
                world_.addToDefeatedAfterKilling(enemy, this);
                this.isAlive_ = false;
            }
            else if (serialNo_ < enemy.getSerialNo()) {
                world_.addToDefeatedAfterKilling(this, enemy);
                enemy.isAlive_ = false;
            }
            else if (serialNo_ > enemy.getSerialNo()) {
                world_.addToDefeatedAfterKilling(enemy, this);
                this.isAlive_ = false;
            }
        }
    }
    protected abstract void createNewborn(int x, int y);
    protected void createNewborn(Organism o) {
        world_.addNewOrganism(o);
    }
    protected static int objectCounter_;
    protected int serialNo_;
    protected Point position_;
    protected Point previousPosition_;
    protected World world_;
    protected int initiative_;
    protected int power_;
    protected char id_ = ' ';
    protected static Random randomNo_ = new Random();
    protected boolean isAlive_ = true;
}
