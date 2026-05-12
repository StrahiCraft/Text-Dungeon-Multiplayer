package client.entity.enemy;

import client.combat.CombatManager;
import client.entity.Entity;
import client.graphics.Color;
import utility.Stats;
import utility.file.FileInterpreter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy extends Entity implements utility.file.FileWriter, FileInterpreter {
    private float threatLevel;

    public Enemy(Enemy enemy){
        setName(enemy.getName());
        setStats(new Stats(enemy.getStats()));

        threatLevel = enemy.getThreatLevel();
    }

    public Enemy() {
        super();
        threatLevel = 0f;
    }

    public Enemy(float threatLevel) {
        this.threatLevel = threatLevel;
    }

    public Enemy(String name, Stats stats, float threatLevel) {
        super(name, stats);
        this.threatLevel = threatLevel;
    }

    @Override
    public void handleDeath() {
        CombatManager.onEnemyDeath(this);
    }

    @Override
    public void writeToFile(FileWriter fileWriter) {
        writeToFile();
    }

    @Override
    public void writeToFile() {
        try {
            File file = new File("assets/enemies/" + getName() + ".txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("name=" + getName() + "\nthreatLevel=" + threatLevel + "\n");
            getStats().writeToFile(fileWriter);

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(Color.getColor("bright red") + "Error while creating or writing to utility.file: "
                    + Color.resetColor() + "assets/enemies" + getName() + ".txt");
            e.printStackTrace();
        }
    }

    @Override
    public void interpretFileData(ArrayList<String> fileData) {
        setName(fileData.get(0));
        setThreatLevel(Float.parseFloat(fileData.get(1)));

        getStats().interpretFileData(new ArrayList<String>(fileData.subList(2, fileData.size())));
    }

    public float getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(float threatLevel) {
        this.threatLevel = threatLevel;
    }
}
