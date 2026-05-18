package client.inventory.item.equipment;

import client.entity.player.Player;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;

public class Equipment implements Serializable {
    private Dictionary<EquipmentSlot, EquipItem> equippedItems;

    public Equipment() {
        equippedItems = new Hashtable<>();

        equippedItems.get(EquipmentSlot.HEAD);
        equippedItems.get(EquipmentSlot.BODY);
        equippedItems.get(EquipmentSlot.HANDS);
        equippedItems.get(EquipmentSlot.LEGS);
        equippedItems.get(EquipmentSlot.FEET);
        equippedItems.get(EquipmentSlot.WEAPON);
    }

    public void equip(EquipItem equipItem){
        float maxSpeedPostEquip = Game.getPlayer().getStats().getMaxSpeed() + equipItem.getStatIncreases().getMaxSpeed();

        EquipItem equippedItem = equippedItems.get(equipItem.getEquipmentSlot());
        if(equippedItem != null){
            maxSpeedPostEquip -= equippedItem.getStatIncreases().getMaxSpeed();
        }

        if(maxSpeedPostEquip <= 0) {
            TextRenderer.printText(Color.getColor("red") + "You will be too slow to fight with that equipped!"
                    + Color.resetColor() + "\nGet faster if you wish to use it.");
            return;
        }

        Game.getPlayer().getInventory().removeItem(equipItem);

        if(equippedItems.get(equipItem.getEquipmentSlot()) != null) {
            EquipItem unequippedItem = equippedItems.get(equipItem.getEquipmentSlot());
            unequippedItem.onUnequip();
            Game.getPlayer().getInventory().addItem(unequippedItem);
        }

        equippedItems.put(equipItem.getEquipmentSlot(), equipItem);
        equipItem.onEquip();
        Game.getPlayer().getStats().refillSpeed();
    }

    public void unequip(EquipmentSlot equipmentSlot) {
        if(Game.getPlayer().getInventory().isFull()) {
            TextRenderer.printText("Cannot" +
                    Color.getColor("gray") + " unequip," + Color.resetColor() +
                    Color.getColor("red") + " inventory is full." + Color.resetColor());
            return;
        }

        if(equippedItems.get(equipmentSlot) == null) {
            TextRenderer.printText("Equipment slot " + equipmentSlot + " is empty.");
            return;
        }

        EquipItem equippedItem = equippedItems.get(equipmentSlot);
        if(equippedItem != null){
            float healthPostUnequip = Game.getPlayer().getStats().getCurrentHealth() -
                    equippedItem.getStatIncreases().getMaxHealth();

            if(healthPostUnequip <= 0) {
                TextRenderer.printText(Color.getColor("red") + "You will die if you unequip that!"
                        + Color.resetColor() + "\nHeal up if you wish to use it.");
                return;
            }
        }

        EquipItem unequippedItem = equippedItems.get(equipmentSlot);
        unequippedItem.onUnequip();
        Game.getPlayer().getInventory().addItem(unequippedItem);

        equippedItems.remove(equipmentSlot);
        equippedItems.get(equipmentSlot);
    }

    public Equipment(Dictionary<EquipmentSlot, EquipItem> equippedItems) {
        this.equippedItems = equippedItems;
    }

    public EquipItem getEquippedItem(EquipmentSlot equipmentSlot){
        return equippedItems.get(equipmentSlot);
    }

    public Dictionary<EquipmentSlot, EquipItem> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(Dictionary<EquipmentSlot, EquipItem> equippedItems) {
        this.equippedItems = equippedItems;
    }

    @Override
    public String toString() {
        return "Equipment:\n" +
                equipmentFromSlot(EquipmentSlot.HEAD) +
                equipmentFromSlot(EquipmentSlot.BODY) +
                equipmentFromSlot(EquipmentSlot.HANDS) +
                equipmentFromSlot(EquipmentSlot.LEGS) +
                equipmentFromSlot(EquipmentSlot.FEET) +
                equipmentFromSlot(EquipmentSlot.WEAPON);
    }

    private String equipmentFromSlot(EquipmentSlot equipmentSlot){
        StringBuilder equipment = new StringBuilder();

        equipment.append(equipmentSlot).append(": ");
        if(equippedItems.get(equipmentSlot) == null) {
            equipment.append(Color.getColor("red")).append("empty").append(Color.resetColor()).append('\n');
        }
        else {
            equipment.append(equippedItems.get(equipmentSlot)).append('\n');
        }

        return equipment.toString();
    }
}
