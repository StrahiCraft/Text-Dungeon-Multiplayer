package utility;

import client.graphics.Color;
import utility.file.FileInterpreter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Stats implements utility.file.FileWriter, FileInterpreter, Serializable {
    private static final float MAX_ARMOR = 100;

    private float maxHealth;
    private float currentHealth;
    private float armor;

    private float damage;

    private float maxSpeed;
    private float currentSpeed;

    public Stats() {
        maxHealth = 0;
        currentHealth = 0;
        armor = 0;
        damage = 0;
        maxSpeed = 0;
        currentSpeed = 0;
    }

    public Stats(Stats otherStats){
        maxHealth = otherStats.getMaxHealth();
        currentHealth = otherStats.getCurrentHealth();
        armor = otherStats.getArmor();
        damage = otherStats.getDamage();
        maxSpeed = otherStats.getMaxSpeed();
        currentSpeed = otherStats.getCurrentSpeed();
    }

    public Stats(float maxHealth, float currentHealth, float armor,
                 float damage,
                 float maxSpeed, float currentSpeed) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.armor = armor;
        this.damage = damage;
        this.maxSpeed = maxSpeed;
        this.currentSpeed = currentSpeed;
    }

    public Stats(float maxHealth, float armor, float damage, float maxSpeed){
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.armor = armor;
        this.damage = damage;
        this.maxSpeed = maxSpeed;
        currentSpeed = maxSpeed;
    }

    @Override
    public void writeToFile() {
        System.out.println(Color.getColor("bright red") +
                "Error, don't write stats to utility.file without a file writer name!" + Color.resetColor());
    }

    @Override
    public void writeToFile(FileWriter fileWriter) {
        try {
            fileWriter.append("health=" + maxHealth + "\n");
            fileWriter.append("armor=" + armor + "\n");
            fileWriter.append("damage=" + damage + "\n");
            fileWriter.append("speed=" + maxSpeed + "\n");

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(Color.getColor("bright red") +
                    "Error while writing stats to file!" + Color.resetColor());
            e.printStackTrace();
        }
    }

    @Override
    public void interpretFileData(ArrayList<String> fileData) {
        maxHealth = Float.parseFloat(fileData.get(0));
        currentHealth = maxHealth;
        armor = Float.parseFloat(fileData.get(1));
        damage = Float.parseFloat(fileData.get(2));
        maxSpeed = Float.parseFloat(fileData.get(3));
        currentSpeed = maxSpeed;
    }

    public void useSpeed(float multiplier) {
        currentSpeed -= multiplier + (multiplier * ((Math.min(armor, Stats.getMaxArmor())) / Stats.getMaxArmor()));
        if(currentSpeed < 0) {
            currentSpeed = 0;
        }
    }

    public void refillSpeed() {
        currentSpeed = maxSpeed;
    }

    public void multiplyStats(float multiplier){
        maxHealth *= multiplier;
        currentHealth *= multiplier;
        armor *= multiplier;
        damage *= multiplier;
        maxSpeed *= multiplier;
        currentSpeed *= multiplier;
    }

    public void increaseStats(Stats otherStats, float multiplier){
        maxHealth += otherStats.getMaxHealth() * multiplier;
        currentHealth += otherStats.getCurrentHealth() * multiplier;
        armor += otherStats.getArmor() * multiplier;
        damage += otherStats.getDamage() * multiplier;
        maxSpeed += otherStats.getMaxSpeed() * multiplier;
        currentSpeed += otherStats.getCurrentSpeed() * multiplier;
    }

    public static float getMaxArmor() {
        return MAX_ARMOR;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = Math.min(currentHealth, maxHealth);

        if(this.currentHealth < 0){
            this.currentHealth = 0;
        }
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        if(currentSpeed < 0){
            this.currentSpeed = 0;
            return;
        }

        if(currentSpeed > maxSpeed){
            this.currentSpeed = maxSpeed;
            return;
        }

        this.currentSpeed = currentSpeed;
    }

    @Override
    public String toString() {
        return "Stats: | " +
                currentHealth + "/" + maxHealth + Color.getColor("red") + " HP " + Color.resetColor() + "| " +
                armor + Color.getColor("yellow") + " ARMOR " + Color.resetColor() +"| " +
                damage + Color.getColor("magenta") + " DAMAGE " + Color.resetColor() + "| " +
                currentSpeed + "/" + maxSpeed + " SPEED |";
    }
}
