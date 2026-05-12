package utility.factory;

import client.entity.enemy.Enemy;
import utility.Stats;

public class EnemyFactory {

    public static void main(String[] args) {
        createEnemies();
    }

    private static void createEnemies(){
        Enemy slime = new Enemy("Slime",
                new Stats( 2f, 0f, 1f, 2),
                0.2f);
        slime.writeToFile();

        Enemy troll = new Enemy("Troll",
                new Stats(10f, 5f, 3f, 1.2f),
                0.8f);
        troll.writeToFile();

        Enemy livingStatue = new Enemy("Living Statue",
                new Stats(10f, 10f, 1.5f, 0.5f),
                0.6f);
        livingStatue.writeToFile();

        Enemy crystalSpider = new Enemy("Crystal Spider",
                new Stats( 15f, 7f, 2.5f, 3f),
                3.5f);
        crystalSpider.writeToFile();

        Enemy livingSword = new Enemy("Living Sword",
                new Stats(5f, 0f, 7.5f, 5f),
                1.3f);
        livingSword.writeToFile();

        Enemy sneakyGolem = new Enemy("Sneaky golem",
                new Stats(100f, 50f, 200f, 1f),
                10f);
        sneakyGolem.writeToFile();

        Enemy abyssling = new Enemy("Abyssling",
                new Stats(29f, 15f, 7f, 5f),
                2f);
        abyssling.writeToFile();
    }
}
