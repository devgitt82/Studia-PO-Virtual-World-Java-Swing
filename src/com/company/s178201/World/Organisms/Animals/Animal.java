package com.company.s178201.World.Organisms.Animals;

import java.awt.Point;
import com.company.s178201.World.Organism;
import com.company.s178201.World.World;

abstract public class Animal extends Organism {
    Animal() { super(); previousPosition_ = position_; }
    Animal(int x, int y) { super(x, y); previousPosition_ = position_; }
    Animal(int x, int y, World world) { super(x, y, world); previousPosition_ = position_; }

    public void moveBack() { setPosition(previousPosition_); }
    public void increasePower(int by) { power_ += by; }
    public void decreasePower(int by) { power_ -= by; }
    public void action() {
        int r = randomNo_.nextInt(4);
        if ((r == 0) && world_.isClearFromNewborns(position_.x-1, position_.y)) moveX(DIRECTIONS.LEFT);
        else if ((r == 1) && world_.isClearFromNewborns(position_.x+1, position_.y)) moveX(DIRECTIONS.RIGHT);
        else if ((r == 2) && world_.isClearFromNewborns(position_.x, position_.y-1 )) moveY(DIRECTIONS.UP);
        else if ((r == 3) && world_.isClearFromNewborns(position_.x, position_.y+1)) moveY(DIRECTIONS.DOWN);
        super.action();
    }

    protected void moveX(DIRECTIONS direction) {
        previousPosition_= position_;
        if(direction == DIRECTIONS.LEFT) {
            for(int i = moveRange; i >= 1; i--) {
                if(position_.x - i >= 0) {
                    setPosition(new Point(position_.x - i, position_.y));
                    break;
                }
            }
        }
        if(direction == DIRECTIONS.RIGHT) {
            for(int i = moveRange; i >= 1; i--) {
                if(position_.x + i < world_.getN()) {
                    setPosition(new Point(position_.x + i, position_.y));
                    break;
                }
            }
        }
    }

    protected void moveY(DIRECTIONS direction) {
        previousPosition_= position_;
        if (direction == DIRECTIONS.UP) {
            for (int i = moveRange; i >= 1; i--) {
                if (position_.x - i >= 0) {
                    setPosition(new Point(position_.x, position_.y - i));
                    break;
                }
            }
        }
        else {
            for (int i = moveRange; i >= 1; i--) {
                if (position_.x + i < world_.getN()) {
                    setPosition(new Point(position_.x, position_.y + i));
                    break;
                }
            }
        }
    }
    public void speciesSpecificCollision(Organism enemyOrganism) {}
    protected boolean isCombatAllowed(Animal enemyAnimal) { return true; }
    public void collision(Organism enemyOrganism) {
        //multiplication
        try {
        if (getId() == enemyOrganism.getId()) {
            moveBack();

                if (world_.isClear(position_.x - 1, position_.y))
                    createNewborn(position_.x - 1, position_.y);
                else if (world_.isClear(position_.x + 1, position_.y))
                    createNewborn(position_.x + 1, position_.y);
                else if (world_.isClear(position_.x, position_.y - 1))
                    createNewborn(position_.x, position_.y - 1);
                else if (world_.isClear(position_.x, position_.y + 1))
                    createNewborn(position_.x, position_.y + 1);

                else if (world_.isClear(enemyOrganism.getPosition().x - 1, enemyOrganism.getPosition().y))
                    createNewborn(enemyOrganism.getPosition().x - 1, enemyOrganism.getPosition().y);
                else if (world_.isClear(enemyOrganism.getPosition().x + 1, enemyOrganism.getPosition().y))
                    createNewborn(enemyOrganism.getPosition().x + 1, enemyOrganism.getPosition().y);
                else if (world_.isClear(enemyOrganism.getPosition().x, enemyOrganism.getPosition().y - 1))
                    createNewborn(enemyOrganism.getPosition().x, enemyOrganism.getPosition().y - 1);
                else if (world_.isClear(enemyOrganism.getPosition().x, enemyOrganism.getPosition().y + 1))
                    createNewborn(enemyOrganism.getPosition().x, enemyOrganism.getPosition().y + 1);

                else
                    throw new MultiplicationImpossibleException();
            world_.updateLog(getSpecies() + " multiplies");
        }
        else {
            isSteady = false;
            if(enemyOrganism instanceof Animal) {
                if (isCombatAllowed((Animal) enemyOrganism) && ((Animal) enemyOrganism).isCombatAllowed(this)) {
                    combat(enemyOrganism);
                }
                else {
                    speciesSpecificCollision(enemyOrganism);
                    enemyOrganism.speciesSpecificCollision(this);
                }
            }
            else {
                eat(enemyOrganism);
            }
            isSteady = true;
        }
    }
    catch (MultiplicationImpossibleException e) {
        world_.updateLog(e.getMessage() +  this.getSpecies());
        System.err.println(e.getMessage());
            }
    }
    protected boolean isSteady = true;
    protected void eat(Organism plant) {
        super.eat(plant);
    }
    protected enum DIRECTIONS { LEFT, RIGHT, UP, DOWN}
    protected int moveRange = 1;
}
