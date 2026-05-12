package client.inventory.item.equipment;

import client.inventory.item.Item;
import client.inventory.item.Rarity;
import client.entity.player.Player;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Stats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EquipItem extends Item {
    private Stats statIncreases;
    private EquipmentSlot equipmentSlot;

    public EquipItem(){
        super();
        statIncreases = new Stats();
        equipmentSlot = EquipmentSlot.HEAD;
        handleRarity();
    }

    public EquipItem(EquipItem otherItem){
        super(otherItem);
        statIncreases = new Stats(otherItem.getStatIncreases());
        equipmentSlot = otherItem.getEquipmentSlot();
        handleRarity();
    }

    public EquipItem(Stats statIncreases, EquipmentSlot equipmentSlot) {
        super();
        this.statIncreases = statIncreases;
        this.equipmentSlot = equipmentSlot;
        handleRarity();
    }

    public EquipItem(String name, Rarity rarity, int price, Stats statIncreases, EquipmentSlot equipmentSlot) {
        super(name, rarity, price);
        this.statIncreases = statIncreases;
        this.equipmentSlot = equipmentSlot;
        handleRarity();
    }

    @Override
    public void onUse() {
        Player.Instance.getEquipment().equip(this);
    }

    @Override
    public void handleRarity() {
        switch (getRarity()){
            case COMMON -> {
                statIncreases.multiplyStats(0.85f);
                setPrice((int)((float)getPrice() * 0.85f));
            }
            case UNCOMMON -> {
                statIncreases.multiplyStats(1f);
            }
            case RARE -> {
                statIncreases.multiplyStats(1.25f);
                setPrice((int)((float)getPrice() * 1.25f));
            }
            case EPIC -> {
                statIncreases.multiplyStats(1.75f);
                setPrice((int)((float)getPrice() * 1.75f));
            }
            case LEGENDARY -> {
                statIncreases.multiplyStats(3f);
                setPrice((int)((float)getPrice() * 3f));
            }
            case MITHIC -> {
                statIncreases.multiplyStats(5f);
                setPrice((int)((float)getPrice() * 5f));
            }
        }
    }

    @Override
    public String info() {
        return "Increases stats while equipped:\n" + statIncreases + "\n" +
                "It can be equipped in the " + Color.getColor("blue") + equipmentSlot + Color.resetColor()
                + " slot.";
    }

    @Override
    public Item copy() {
        return new EquipItem();
    }

    public void onEquip(){
        TextRenderer.printText("Equipped " + this);
        Player.Instance.increaseStats(statIncreases);
    }

    public void onUnequip(){
        TextRenderer.printText("Unequipped " + this);
        Player.Instance.decreaseStats(statIncreases);
    }

    @Override
    public void writeToFile() {
        try {
            File file = new File("assets/items/equipItems/" + getName() + ".txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("name=" + getName() +
                    "\nprice=" + getPrice() +
                    "\nequipmentSlot=" + equipmentSlot + "\n");
            statIncreases.writeToFile(fileWriter);

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(Color.getColor("bright red") + "Error while creating or writing to utility.file: "
                    + Color.resetColor() + "assets/items/equipItems/" + getName() + ".txt");
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
        setEquipmentSlot(EquipmentSlot.valueOf(fileData.get(2)));

        statIncreases.interpretFileData(new ArrayList<String>(fileData.subList(3, fileData.size())));
    }

    public Stats getStatIncreases() {
        return statIncreases;
    }

    public void setStatIncreases(Stats statIncreases) {
        this.statIncreases = statIncreases;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public void setEquipmentSlot(EquipmentSlot equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
    }
}
