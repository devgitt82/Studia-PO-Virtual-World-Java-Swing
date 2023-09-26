package com.company.s178201.World.Organisms.Plants;
import com.company.s178201.World.Organism;
import com.company.s178201.World.World;

abstract public class Plant extends Organism {
    Plant(int x, int y, World world) { super(x, y, world); initiative_ = 0; }
    Plant(int x, int y) { super(x, y); initiative_ = 0; }
    Plant() { super(); initiative_ = 0; }

    protected void multiplication() {
        int x = position_.x;
        int y = position_.y;

        if (world_.isClear(position_.x + 1, position_.y))
            x++;
        else if (world_.isClear(position_.x - 1, position_.y))
            x--;
        else if (world_.isClear(position_.x, position_.y + 1))
            y++;
        else if (world_.isClear(position_.x, position_.y - 1))
            y--;
        createNewborn(x, y);
    }

    public void action() {
        // 1 on 4 multiplication will happen
        if (randomNo_.nextInt(4) == 1) {
            multiplication();
        }
    }
}
