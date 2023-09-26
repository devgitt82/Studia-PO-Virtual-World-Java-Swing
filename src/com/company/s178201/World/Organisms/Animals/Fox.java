package com.company.s178201.World.Organisms.Animals;
import com.company.s178201.World.World;

public class Fox extends Animal {
    public Fox() { super(); initialize(); }
    public Fox(int x, int y) { super(x, y); initialize(); }
    public Fox(int x, int y, World world) { super(x, y, world); initialize(); }

    public String getSpecies() { return "Fox"; }

    protected void createNewborn(int x, int y) {
        super.createNewborn(new Fox(x, y));
    }
    protected void initialize() {
        initiative_ = 7;
        power_ = 3;
        id_ = 'F';
    }

    protected void moveX(DIRECTIONS direction) {
        int intDirection = (direction == DIRECTIONS.LEFT ? -1 : 1);
        if (world_.isClearFromStronger(position_.x + intDirection, position_.y, this))
            super.moveX(direction);
        else if (world_.isClearFromStronger(position_.x - intDirection, position_.y, this))
            super.moveX(direction == DIRECTIONS.LEFT ? DIRECTIONS.RIGHT : DIRECTIONS.LEFT);
        else if (world_.isClearFromStronger(position_.x , position_.y -1, this))
            super.moveY(DIRECTIONS.UP);
        else if (world_.isClearFromStronger(position_.x , position_.y +1, this))
            super.moveY(DIRECTIONS.DOWN);
    }
    protected void moveY(DIRECTIONS direction) {
        int intDirection = (direction == DIRECTIONS.UP ? -1 : 1);
        if (world_.isClearFromStronger(position_.x, position_.y + intDirection,this))
            super.moveX(direction);
        else if (world_.isClearFromStronger(position_.x, position_.y - intDirection,this))
            super.moveX(direction == DIRECTIONS.UP ? DIRECTIONS.DOWN : DIRECTIONS.UP);
        else if (world_.isClearFromStronger(position_.x - 1, position_.y,this))
            super.moveY(DIRECTIONS.LEFT);
        else if (world_.isClearFromStronger(position_.x + 1, position_.y,this))
            super.moveY(DIRECTIONS.RIGHT);
    }
}
