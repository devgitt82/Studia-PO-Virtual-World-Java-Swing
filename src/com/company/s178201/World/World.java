package com.company.s178201.World;

import com.company.s178201.World.Display.UIrefresher;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;


final public class World {

    public World(int N) {N_ = N;}
    public World() {}

    public void nextRound() {
        turnNumber++;
        myUIrefresher_.appendLogTextArea("\nTurn " + turnNumber);
        //ACTION
        for(Organism organism : organisms_){
            //IF SOMEONE DIED ALREADY< THERE IS NO NEED ANY MORE
            if (organism.isAlive_ == true){
                organism.action();
                for (int i = 0; i < organisms_.size(); ++i) {
                    //IF NOT ALREADY DIED
                    if (organisms_.get(i).isAlive_ == true) {
                        if (organism.getPosition().equals(organisms_.get(i).getPosition())       //COLLISION
                         && !(organism.getSerialNo() == (organisms_.get(i).getSerialNo()))) {    // IF NOT SAME ORGANISM
                                organism.collision(organisms_.get(i));
                        }
                    }
                }
            }
        }
        deleteDefeated();       // delete those who lost in combat or were eaten
        updateGridPanel();      //referesh gridPanel - add changes
    }

    public boolean isClear(int x, int y) {
        if (x < 0 || y < 0 || x >= N_ || y >= N_)
            return false;
        Point tempPosition = new Point(x, y);
        for(Organism organism : organisms_)
            if(organism.getPosition().equals(tempPosition))
                return false;
        for(Organism organism : newBorns_)
            if(organism.getPosition().equals(tempPosition))
                return false;
        return true;
    }

    public boolean isClearFromStronger(int x, int y, Organism thisOrganism) {
        if (x < 0 || y < 0 || x >= N_ || y >= N_)
            return false;
        Point tempPosition = new Point(x, y);
        for(Organism organism : organisms_)
            if((organism.getPosition().equals(tempPosition)) && (organism.getPower() > thisOrganism.getPower()))
                return false;
        for(Organism organism : newBorns_)
            if(organism.getPosition().equals(tempPosition)) return false;
        return true;
    }
    public boolean isClearFromNewborns(int x, int y) {
        if (x < 0 || y < 0 || x >= N_ || y >= N_)
            return false;
        Point tempPosition = new Point(x, y);
        for(Organism organism : newBorns_)
            if(organism.getPosition().equals(tempPosition))
                return false;
        return true;
    }

    public int getN() {
        return N_; }

    public void setN(int N) {
        this.N_ = N; }

    public int getTurnNumber() {
        return turnNumber; }
    public void setTurnNumber(int turnNumb) {
        turnNumber = turnNumb; }

    public ArrayList<Organism> getOrganisms() {
        return organisms_;
    }

    public UIrefresher getUIrefresher() {
        return myUIrefresher_;
    }
    public void setMyUIrefresher(UIrefresher myUIrefresher) {
        this.myUIrefresher_ = myUIrefresher;
    }

    public void addNewOrganism(Organism new_org) {
        if (isClear(new_org.getPosition().x, new_org.getPosition().y)) {
            new_org.setWorld(this);
            newBorns_.add(new_org);
        }
    }

    public void updateLog(String logEntry) {
        String entry = (log_.size() + 1) + ") " + logEntry;
        log_.add(entry);
    }

    public void addNewBorns() {
        // Newborned Organisms added to organisms arraylist
        organisms_.addAll(newBorns_);
        newBorns_.clear();
        // Sort as per own Comparator - see below
        organisms_.sort(new MyOrganismsComparator());
    }

    // my own Organisms comparataor,
    static class MyOrganismsComparator implements Comparator<Organism> {
        public int compare(Organism organismA, Organism organismB) {
            if(organismA.getInitiative() != organismB.getInitiative())
                return organismB.getInitiative() - organismA.getInitiative();
            else if(organismA.getSerialNo() == (organismB.getSerialNo()))
                return 0;
            else
                return (organismA.getSerialNo() - organismB.getSerialNo());
        }
    }

    public void updateGridPanel() {
       myUIrefresher_.clearScreen(N_);
        showOrganismsOnGridPanel();  // self explanatory
        // update log on screen
        for(String event : log_)
            myUIrefresher_.appendLogTextArea(event);
        log_.clear(); //arraylList emptied
        addNewBorns();
    }

    public void addToDefeatedAfterKilling(Organism winner, Organism defeated) {
        updateLog(winner.getSpecies() + " kills " + defeated.getSpecies());
        defeatedOrganisms_.add(defeated.getSerialNo());
    }


    public void addToDefeatedAfterEating(Organism animal, Organism plant) {
        updateLog(animal.getSpecies() + " eats " + plant.getSpecies());
        defeatedOrganisms_.add(plant.getSerialNo());
    }


    private void deleteDefeated() {
        for(Integer id : defeatedOrganisms_) {
            for(Organism organism : organisms_) {
                if(organism.isAlive_ == false) {
                    organisms_.remove(organism);
                    break;
                }
            }
        }
        defeatedOrganisms_.clear();
    }

    private void showOrganismsOnGridPanel() {
        for(Organism organism : organisms_)
            organism.showOnGridPanel();
    }
    public void deleteWorld() {
        newBorns_.clear();
        organisms_.clear();
        defeatedOrganisms_.clear();
        log_.clear();
        Organism.objectCounter_= 0;
        turnNumber = 0;
    }


    private ArrayList<Organism> organisms_ = new ArrayList<Organism>();
    private ArrayList<Organism> newBorns_ = new ArrayList<Organism>();
    private ArrayList<Integer> defeatedOrganisms_ = new ArrayList<Integer>();
    private ArrayList<String> log_ = new ArrayList<>();
    private int N_ = 20;
    private static int turnNumber = 0;
    private UIrefresher myUIrefresher_;
}
