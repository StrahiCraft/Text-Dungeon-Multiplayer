package utility.factory;

import client.inventory.item.Rarity;
import client.inventory.item.equipment.EquipItem;
import client.inventory.item.equipment.EquipmentSlot;
import client.inventory.item.potions.Potion;
import client.inventory.item.potions.TemporaryPotion;
import client.inventory.item.special.BundleOfGold;
import client.graphics.Color;
import utility.Stats;

public class ItemFactory {
    public static void main(String[] args) {
        createEquipItems();
        createPotions();
        createSpecialItems();
    }

    // =================================================
    // Equip items
    // =================================================

    private static void createWeapons(){
        EquipItem woodenSword = new EquipItem("Wooden sword", Rarity.UNCOMMON, 2,
                new Stats(0, 0, 0.5f, 0), EquipmentSlot.WEAPON);
        woodenSword.writeToFile();

        EquipItem shortsword = new EquipItem("Shortsword", Rarity.UNCOMMON, 10,
                new Stats(0, 0, 2.5f, 4), EquipmentSlot.WEAPON);
        shortsword.writeToFile();

        EquipItem longsword = new EquipItem("Longsword", Rarity.UNCOMMON, 15,
                new Stats(0, 3, 6f, -2), EquipmentSlot.WEAPON);
        longsword.writeToFile();

        EquipItem greatsword = new EquipItem("Greatsword", Rarity.UNCOMMON, 25,
                new Stats(15, 10, 15f, -10), EquipmentSlot.WEAPON);
        greatsword.writeToFile();

        EquipItem hornedShield = new EquipItem("Horned shield", Rarity.UNCOMMON, 12,
                new Stats(5, 7, 3f, -1), EquipmentSlot.WEAPON);
        hornedShield.writeToFile();

        EquipItem trident = new EquipItem("Trident", Rarity.UNCOMMON, 17,
                new Stats(0, 0, 5f, 5), EquipmentSlot.WEAPON);
        trident.writeToFile();

        EquipItem dagger = new EquipItem("Dagger", Rarity.UNCOMMON, 8,
                new Stats(0, -3, 1f, 10), EquipmentSlot.WEAPON);
        dagger.writeToFile();
    }

    private static void createArmor(){
        // =======================================================================================
        // Leather set
        // =======================================================================================
        EquipItem leatherCap = new EquipItem("Leather cap", Rarity.UNCOMMON, 3,
                new Stats(0, 1, 0f, 1), EquipmentSlot.HEAD);
        leatherCap.writeToFile();

        EquipItem leatherTunic = new EquipItem("Leather tunic", Rarity.UNCOMMON, 5,
                new Stats(1, 2, 0f, 1), EquipmentSlot.BODY);
        leatherTunic.writeToFile();

        EquipItem leatherGloves = new EquipItem("Leather gloves", Rarity.UNCOMMON, 2,
                new Stats(0.5f, 0, 0f, 1), EquipmentSlot.HANDS);
        leatherGloves.writeToFile();

        EquipItem leatherPants = new EquipItem("Leather pants", Rarity.UNCOMMON, 4,
                new Stats(1, 1, 0f, 2), EquipmentSlot.LEGS);
        leatherPants.writeToFile();

        EquipItem leatherBoots = new EquipItem("Leather boots", Rarity.UNCOMMON, 3,
                new Stats(0, 1, 0f, 2), EquipmentSlot.FEET);
        leatherBoots.writeToFile();
        // =======================================================================================
        // Chainmail set
        // =======================================================================================
        EquipItem chainmailHelmet = new EquipItem("Chainmail helmet", Rarity.UNCOMMON, 10,
                new Stats(5, 2, 0f, 0), EquipmentSlot.HEAD);
        chainmailHelmet.writeToFile();

        EquipItem chainmailChestplate = new EquipItem("Chainmail chestplate", Rarity.UNCOMMON, 15,
                new Stats(10, 5, 0f, -1), EquipmentSlot.BODY);
        chainmailChestplate.writeToFile();

        EquipItem spikedGloves = new EquipItem("Spiked gloves", Rarity.UNCOMMON, 12,
                new Stats(3f, 2, 5f, 0), EquipmentSlot.HANDS);
        spikedGloves.writeToFile();

        EquipItem chainmailLeggings = new EquipItem("Chainmail leggings", Rarity.UNCOMMON, 13,
                new Stats(5, 3.5f, 0, -0.5f), EquipmentSlot.LEGS);
        chainmailLeggings.writeToFile();

        EquipItem chainmailBoots = new EquipItem("Chainmail boots", Rarity.UNCOMMON, 9,
                new Stats(3, 3, 0f, 2), EquipmentSlot.FEET);
        chainmailBoots.writeToFile();
        // =======================================================================================
        // Ironclad set
        // =======================================================================================
        EquipItem ironcladHelmet = new EquipItem("Ironclad helmet", Rarity.UNCOMMON, 25,
                new Stats(15, 7, 0f, -4), EquipmentSlot.HEAD);
        ironcladHelmet.writeToFile();

        EquipItem ironcladChestplate = new EquipItem("Ironclad chestplate", Rarity.UNCOMMON, 50,
                new Stats(25, 15, 0f, -8), EquipmentSlot.BODY);
        ironcladChestplate.writeToFile();

        EquipItem ironcladGauntlets = new EquipItem("Ironclad gauntlets", Rarity.UNCOMMON, 40,
                new Stats(10f, 5, 15f, -3), EquipmentSlot.HANDS);
        ironcladGauntlets.writeToFile();

        EquipItem ironcladLeggings = new EquipItem("Ironclad leggings", Rarity.UNCOMMON, 35,
                new Stats(20, 10f, 0, -5.5f), EquipmentSlot.LEGS);
        ironcladLeggings.writeToFile();

        EquipItem ironcladGrieves = new EquipItem("Ironclad grieves", Rarity.UNCOMMON, 30,
                new Stats(12, 5, 1f, -1), EquipmentSlot.FEET);
        ironcladGrieves.writeToFile();
    }

    private static void createEquipItems(){
        createWeapons();
        createArmor();
    }

    // =================================================
    // Potions
    // =================================================

    private static void createPotions(){
        createPermanentPotions();
        createTemporaryPotions();
    }

    private static void createPermanentPotions(){
        Potion healthPotion = new Potion(Color.getColor("red") + "Health potion" + Color.resetColor(),
                Rarity.COMMON, 10,
                new Stats(0, 5, 0, 0, 0, 0));
        healthPotion.writeToFile();

        Potion scalePotion = new Potion(Color.getColor("yellow") + "Scale potion" + Color.resetColor(),
                Rarity.COMMON, 15,
                new Stats(0, 0, 1f, 0, 0, 0));
        scalePotion.writeToFile();

        Potion bunnyPotion = new Potion(Color.getColor("green") + "Bunny potion" + Color.resetColor(),
                Rarity.COMMON, 20,
                new Stats(0, 0, 0, 0, 1, 0));
        bunnyPotion.writeToFile();

        Potion gorillaPotion = new Potion(Color.getColor("gray") + "Gorilla potion" + Color.resetColor(),
                Rarity.COMMON, 30,
                new Stats(0, 0, 0, 1, 0, 0));
        gorillaPotion.writeToFile();

        Potion heartGrowthPotion = new Potion(Color.getColor("bright red") + "Heart growth potion" + Color.resetColor(),
                Rarity.COMMON, 20,
                new Stats(2, 0, 0, 0, 0, 0));
        heartGrowthPotion.writeToFile();

        Potion speedPotion = new Potion("Speed potion",
                Rarity.COMMON, 20,
                new Stats(0, 0, 0, 0, 0, 1.5f));
        speedPotion.writeToFile();
    }

    private static void createTemporaryPotions(){
        TemporaryPotion turtlePotion = new TemporaryPotion(Color.getColor("yellow") + "Temporary " + Color.resetColor()
                + "turtle potion", Rarity.COMMON, 100,
                new Stats(0, 0, 40, 0, 0, 0));
        turtlePotion.writeToFile();

        TemporaryPotion potionOfSwiftness = new TemporaryPotion(Color.getColor("yellow") + "Temporary " + Color.resetColor()
                + "potion of swiftness", Rarity.COMMON, 50,
                new Stats(0, 0, 0, 0, 10, 10));
        potionOfSwiftness.writeToFile();
    }

    // =================================================
    // Special
    // =================================================

    private static void createSpecialItems(){
        createBundlesOfGold();
    }

    // =================================================
    // Bundles of gold
    // =================================================

    private static void createBundlesOfGold(){
        // Price must be -1 to not appear in shops

        BundleOfGold smallBundle = new BundleOfGold("Small bundle of" + Color.getColor("yellow") +
                " gold" + Color.resetColor(), Rarity.COMMON, -1, 1);
        smallBundle.writeToFile();

        BundleOfGold mediumBundle = new BundleOfGold("Medium bundle of" + Color.getColor("yellow") +
                " gold" + Color.resetColor(), Rarity.COMMON, -1, 2.5f);
        mediumBundle.writeToFile();

        BundleOfGold largeBundle = new BundleOfGold("Large bundle of" + Color.getColor("yellow") +
                " gold" + Color.resetColor(), Rarity.COMMON, -1, 5);
        largeBundle.writeToFile();
    }
}
