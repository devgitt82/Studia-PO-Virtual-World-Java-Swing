package com.company.s178201.World;
import com.company.s178201.World.Display.ImagePanel;
import com.company.s178201.World.Organisms.Animals.*;
import com.company.s178201.World.Display.UIrefresher;
import com.company.s178201.World.Organisms.Plants.Thorn;
import com.company.s178201.World.Organisms.Plants.Grass;
import com.company.s178201.World.Organisms.Plants.Guarana;
import com.company.s178201.World.Display.GridPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class WorldEngine extends JFrame {

    public WorldEngine() {
        super("Virtual World 1.0.7 by Michal Scibisz  - s178201");
        setContentPane(formMainPanel_);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1000,700);
        startNewGameButton_.setVisible(true);
        loadGameButton_.setVisible(true);
        quitGameButton_.setVisible(true);
        nextTurnButton_.setVisible(false);
        saveGameButton_.setVisible(false);
        returnToMainMenuButton_.setVisible(false);
        setVisible(true);

        startNewGameButton_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                world_ = new World(N_);
                world_.setMyUIrefresher(worldRefresher_);
                initializeOrganisms();
                world_.addNewBorns();
                world_.updateGridPanel();
                startNewGameButton_.setVisible(false);
                loadGameButton_.setVisible(false);
                quitGameButton_.setVisible(true);
                nextTurnButton_.setVisible(true);
                saveGameButton_.setVisible(true);
                returnToMainMenuButton_.setVisible(true);
                isGameRunning_ = true;
            }
        });
        loadGameButton_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                world_ = new World();
                world_.setMyUIrefresher(worldRefresher_);
                File file = new File("save.txt");
                Scanner scanner;
                try
                { scanner = new Scanner(file);}
                catch(FileNotFoundException ex)
                {   return;  }
                world_.setN(scanner.nextInt());
                world_.setTurnNumber(scanner.nextInt());
                int tempOrganismObjectCounter = scanner.nextInt();
                int organismsLength = scanner.nextInt();
                for(int i = 0; i < organismsLength ;i++) {
                        String loadedSpecies = scanner.next();
                        Point loadedPosition = new Point(scanner.nextInt(), scanner.nextInt());
                        Organism newOrg = createOrganism(loadedSpecies,loadedPosition.x, loadedPosition.y);
                        newOrg.setSerialNo (scanner.nextInt());
                        newOrg.setPower(scanner.nextInt());
                        world_.addNewOrganism(newOrg);
                }
                world_.addNewBorns();
                Organism.setObjectCounter(tempOrganismObjectCounter);
                world_.updateGridPanel();
                returnToMainMenuButton_.setVisible(true);
                quitGameButton_.setVisible(true);
                startNewGameButton_.setVisible(false);
                nextTurnButton_.setVisible(true);
                saveGameButton_.setVisible(true);
                loadGameButton_.setVisible(false);
                isGameRunning_ = true;
            }
        });
        saveGameButton_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    PrintWriter zapis = new PrintWriter("save.txt");
                    zapis.println(world_.getN());
                    zapis.println(world_.getTurnNumber());
                    zapis.println(Organism.getObjectCounter());
                    zapis.println(world_.getOrganisms().size());
                    zapis.println();
                    for (Organism organism : world_.getOrganisms()) {
                        zapis.print(organism.getSpecies() + " " + organism.getPosition().x + " " +
                                organism.getPosition().y + " " + organism.getSerialNo() + " " + organism.getPower()
                                + String.format("%n"));
                    }
                    zapis.close();
                }
                catch(FileNotFoundException exc){System.err.println(exc.getMessage());}

            }
        });
        nextTurnButton_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                world_.nextRound();
            }
        });

        drawPanel_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (isGameRunning_ == false)
                    return;

                Point clickedPoint = e.getPoint();
                int gridPosX = clickedPoint.x / gridSize_;
                int gridPosY = clickedPoint.y / gridSize_;

                //proceed only if position is Clear from other organisms
                if (world_.isClear(gridPosX, gridPosY)) {
                    String[] options = { "Sheep","Wolf","Fox","Lion", "Skunk", "Grass", "Guarana", "Thorn"};
                    String selectedSpecies = (String) JOptionPane.showInputDialog(
                            null, "",
                            "Select New Organism", -1, null, options, "Skunk"
                    );
                    Organism newBorn = null;
                    if (selectedSpecies != null)
                        newBorn =createOrganism(selectedSpecies,gridPosX,gridPosY);

                    //if something selected
                    if (newBorn != null) {
                        world_.addNewOrganism(newBorn);
                        world_.addNewBorns();
                        world_.updateGridPanel();
                    }
                } //end of isClear
            } // end of mouseClicked
        }); //end of listener
        quitGameButton_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
        returnToMainMenuButton_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                world_.deleteWorld();
                world_.getUIrefresher().clearScreen(N_);
                world_.getUIrefresher().clearLogTextArea();
                world_ = null;
                returnToMainMenuButton_.setVisible(false);
                startNewGameButton_.setVisible(true);
                nextTurnButton_.setVisible(false);
                loadGameButton_.setVisible(true);
                saveGameButton_.setVisible(false);

            }
        });
    }//end of constructor

    private void createUIComponents() {

        titlePanel_ = new ImagePanel();
        drawPanel_ = new GridPanel(N_, gridSize_);
        worldRefresher_ = new UIrefresher();
        worldRefresher_.setGridSize(gridSize_);
        worldRefresher_.setOrganismGridPanel(drawPanel_);
        logScrollPane_ = new JScrollPane();
        logTextArea_ = new JTextArea();
        worldRefresher_.setLogTextArea(logTextArea_);
        // TODO: place custom component creation code here
    }

    private void initializeOrganisms() {
        createMultipleOrganisms("Sheep", 5);
        createMultipleOrganisms("Wolf", 5);
        createMultipleOrganisms("Fox", 5);
        createMultipleOrganisms("Lion", 5);
        createMultipleOrganisms("Skunk", 5);
        createMultipleOrganisms("Grass", 5);
        createMultipleOrganisms("Guarana", 5);
        createMultipleOrganisms("Thorn", 5);
    }

    private void createMultipleOrganisms(String species, int quantity) {
        Random randomFigure = new Random();
        for (int i = 0; i < quantity; ) {
            int gridPosX = randomFigure.nextInt(N_);
            int gridPosY= randomFigure.nextInt(N_);
            Organism newBorn = createOrganism(species,gridPosX,gridPosY);
            if (newBorn != null)
                i++;
        }
    }
    private Organism createOrganism(String species, int gridPosX, int gridPosY) {

        Organism newBorn = null;
        if (world_.isClear(gridPosX, gridPosY)) {
                if (species.equals("Fox"))
                    newBorn = new Fox(gridPosX, gridPosY);
                else if (species.equals("Sheep"))
                    newBorn = new Sheep(gridPosX, gridPosY);
                else if (species.equals("Wolf"))
                    newBorn = new Wolf(gridPosX, gridPosY);
                else if (species.equals("Lion"))
                    newBorn = new Lion(gridPosX, gridPosY);
                else if (species.equals("Skunk"))
                    newBorn = new Skunk(gridPosX, gridPosY);
                else if (species.equals("Grass"))
                    newBorn = new Grass(gridPosX, gridPosY);
                else if (species.equals("Guarana"))
                    newBorn = new Guarana(gridPosX, gridPosY);
                else if (species.equals("Thorn"))
                    newBorn = new Thorn(gridPosX, gridPosY);
                if (newBorn != null)
                world_.addNewOrganism(newBorn);
            }
        return newBorn;
    }

    private JPanel formMainPanel_;
    private JButton loadGameButton_;
    private JButton quitGameButton_;
    private JButton startNewGameButton_;
    private JButton nextTurnButton_;
    private JButton saveGameButton_;
    private JButton returnToMainMenuButton_;
    private JPanel drawPanel_;
    private JTextArea logTextArea_;
    private JScrollPane logScrollPane_;
    private JPanel titlePanel_;
    private static int N_ = 20;
    private static int gridSize_ = 24;
    private World world_;
    private UIrefresher worldRefresher_;
    private boolean isGameRunning_ = false;
}