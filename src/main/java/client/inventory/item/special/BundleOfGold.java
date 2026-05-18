package client.inventory.item.special;

import client.game.Game;
import client.inventory.item.Item;
import client.inventory.item.Rarity;
import client.entity.player.Player;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BundleOfGold extends Item {
    private float goldMultiplier;

    public BundleOfGold(){
        super();
        goldMultiplier = 0;
    }

    public BundleOfGold(float goldMultiplier) {
        super();
        this.goldMultiplier = goldMultiplier;
    }

    public BundleOfGold(BundleOfGold otherItem) {
        super(otherItem);
        goldMultiplier = otherItem.goldMultiplier;
    }

    public BundleOfGold(String name, Rarity rarity, int price, float goldMultiplier) {
        super(name, rarity, price);
        this.goldMultiplier = goldMultiplier;
    }

    @Override
    public void onUse() {
        int goldIncrease = (int)(Math.random() * 10 * Game.getDungeon().getCurrentDungeonThreat() * goldMultiplier);
        Game.getPlayer().addGold(goldIncrease);
        Game.getPlayer().getInventory().removeItem(this);

        TextRenderer.printText("Inside you find " +
                Color.getColor("yellow") + goldIncrease + " gold" + Color.resetColor() + ".");
    }

    @Override
    public void handleRarity() {
        switch (getRarity()){
            case COMMON ->    goldMultiplier *= 1;
            case UNCOMMON ->  goldMultiplier *= 1.25f;
            case RARE ->      goldMultiplier *= 1.5f;
            case EPIC ->      goldMultiplier *= 1.75f;
            case LEGENDARY -> goldMultiplier *= 2f;
            case MYTHIC ->    goldMultiplier *= 3f;
        }
    }

    @Override
    public String info() {
        return "Gives " + Color.getColor("yellow") + "gold " + Color.resetColor() + "when used.";
    }

    @Override
    public Item copy() {
        return new BundleOfGold();
    }

    @Override
    public void writeToFile() {
        try {
            File file = new File("assets/items/special/bundlesOfGold/" + getUnformattedName() + ".txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("name=" + getName() +
                    "\nprice=" + getPrice() +
                    "\ngoldMultiplier=" + goldMultiplier);

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(Color.getColor("bright red") + "Error while creating or writing to utility.file: "
                    + Color.resetColor() + "assets/items/specials/bundlesOfGold/" + getName() + ".txt");
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile(FileWriter fileWriter) {
        writeToFile();
    }

    @Override
    public void interpretFileData(ArrayList<String> fileData) {
        setName(fileData.get(0));
        setPrice(Integer.parseInt(fileData.get(1)));
        goldMultiplier = Float.parseFloat(fileData.get(2));
    }

    public float getGoldMultiplier() {
        return goldMultiplier;
    }

    public void setGoldMultiplier(float goldMultiplier) {
        this.goldMultiplier = goldMultiplier;
    }
}
