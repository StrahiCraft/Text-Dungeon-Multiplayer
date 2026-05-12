package client.combat;

import client.dungeon.Dungeon;
import client.dungeon.rooms.EmptyRoom;
import client.entity.enemy.Enemy;
import client.inventory.item.equipment.EquipItem;
import client.entity.player.Player;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Stats;

import java.util.ArrayList;

public class CombatManager {
    private static ArrayList<Enemy> enemies = new ArrayList<>();
    private static boolean inCombat = false;
    private static int goldReward;
    private static Stats temporaryStatBonuses = new Stats();
    private static boolean statsChanged = false;

    public static void resetCombat() {
        enemies.clear();
        Player.Instance.getStats().refillSpeed();
        inCombat = true;
    }

    public static void endCombat() {
        inCombat = false;
        TextRenderer.printText("You won the battle and got " + goldReward + Color.getColor("yellow") +
                " gold " + Color.resetColor() + "for it.");
        Player.Instance.addGold(goldReward);
        Player.Instance.getStats().refillSpeed();
        clearTemporaryStats();
        Dungeon.setRoom(new EmptyRoom(Player.Instance.getCurrentRoom().getPosition()),
                Player.Instance.getCurrentRoom().getPosition());
    }

    public static void onEnemyDeath(Enemy deadEnemy) {
        enemies.remove(deadEnemy);
        Player.Instance.increaseScore((int)(10 * deadEnemy.getThreatLevel()));
        if(enemies.isEmpty()){
            CombatManager.endCombat();
        }
    }

    public static void checkForTurnEnd(){
        if(Player.Instance.getStats().getCurrentSpeed() > 0){
            return;
        }

        for(Enemy enemy : enemies) {
            if(enemy.getStats().getCurrentSpeed() > 0) {
                return;
            }
        }

        onTurnEnd();
    }

    public static void attackEnemy(int index) {
        if(index < 1 || index > enemies.size()){
            TextRenderer.printText(Color.getColor("red") + "There is no such enemy!" + Color.resetColor());
            return;
        }

        Enemy attackedEnemy = enemies.get(index - 1);

        float currenEnemyHealth = attackedEnemy.getStats().getCurrentHealth();

        attackedEnemy.takeDamage(Player.Instance.getStats().getDamage());
        Player.Instance.getStats().useSpeed(2);

        if(attackedEnemy.getStats().getCurrentHealth() == 0){
            TextRenderer.printText("Defeated " + attackedEnemy.getName());
        }
        else {
            TextRenderer.printText("Dealt " + (currenEnemyHealth - attackedEnemy.getStats().getCurrentHealth()) +
                    Color.getColor("red") + " damage " + Color.resetColor() + "to " + attackedEnemy.getName());
        }

        checkForTurnEnd();
    }

    public static void takeEnemyAttack() {
        Enemy attackingEnemy = getFastestEnemy();

        float currentPlayerHealth = Player.Instance.getStats().getCurrentHealth();

        Player.Instance.takeDamage(attackingEnemy.getStats().getDamage());
        TextRenderer.printText(attackingEnemy.getName() + " attacks and deals " +
                (currentPlayerHealth - Player.Instance.getStats().getCurrentHealth()) +
                Color.getColor("red") + " damage" + Color.resetColor() + ".");
        attackingEnemy.getStats().useSpeed(2);

        checkForTurnEnd();
    }

    public static void useItem(int index) {
        if(index < 1 || index > Player.Instance.getInventory().getItems().size()){
            TextRenderer.printText(Color.getColor("red") + "There is no such item!" + Color.resetColor());
            return;
        }

        if(Player.Instance.getInventory().getItem(index).getClass() == EquipItem.class){
            TextRenderer.printText(Color.getColor("red") +
                    "You cannot use equip items during combat!" + Color.resetColor());
            return;
        }

        Player.Instance.getInventory().useItem(index);
        Player.Instance.getStats().useSpeed(1);
        checkForTurnEnd();
    }

    public static void addToTemporaryStats(Stats temporaryStats) {
        temporaryStatBonuses.increaseStats(temporaryStats, 1);
        statsChanged = true;
    }

    private static void clearTemporaryStats(){
        if(statsChanged){
            return;
        }

        Player.Instance.getStats().increaseStats(temporaryStatBonuses, -1);
        temporaryStatBonuses = new Stats();
        TextRenderer.printText("Temporary stats fade...");
        statsChanged = false;
    }

    private static void onTurnEnd(){
        Player.Instance.getStats().refillSpeed();
        for(Enemy enemy : enemies) {
            enemy.getStats().refillSpeed();
        }
        clearTemporaryStats();
    }

    public static boolean playerTurn() {
        try {
            return Player.Instance.getStats().getCurrentSpeed() > getFastestEnemy().getStats().getCurrentSpeed();
        } catch (NullPointerException ignored){
            return false;
        }
    }

    private static Enemy getFastestEnemy() {
        if(enemies.isEmpty()){
            return null;
        }

        Enemy fastestEnemy = enemies.get(0);

        for(Enemy enemy : enemies) {
            if(enemy.getStats().getCurrentSpeed() > fastestEnemy.getStats().getCurrentSpeed()){
                fastestEnemy = enemy;
            }
        }
        return fastestEnemy;
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public static void setEnemies(ArrayList<Enemy> enemies) {
        CombatManager.enemies = enemies;
        goldReward = (int)(Dungeon.getDungeonStats().getCurrentThreat() * enemies.size() * 5f);
    }

    public static boolean isInCombat() {
        return inCombat;
    }

    public static void setInCombat(boolean inCombat) {
        CombatManager.inCombat = inCombat;
    }

    public static int getGoldReward() {
        return goldReward;
    }

    public static void setGoldReward(int goldReward) {
        CombatManager.goldReward = goldReward;
    }

    public static Stats getTemporaryStatBonuses() {
        return temporaryStatBonuses;
    }

    public static void setTemporaryStatBonuses(Stats temporaryStatBonuses) {
        CombatManager.temporaryStatBonuses = temporaryStatBonuses;
    }

    public static boolean isStatsChanged() {
        return statsChanged;
    }

    public static void setStatsChanged(boolean statsChanged) {
        CombatManager.statsChanged = statsChanged;
    }

    public static String asString() {
        StringBuilder combatStatus = new StringBuilder();

        combatStatus.append(Color.getColor("red")).append("Enemies:\n").append(Color.resetColor());

        int aliveEnemyIndex = 1;
        for(Enemy enemy : enemies) {
            if(enemy.getStats().getCurrentHealth() > 0) {
                combatStatus.append(aliveEnemyIndex++).append(". ").append(enemy).append('\n');
            }
        }

        return combatStatus.toString();
    }
}
