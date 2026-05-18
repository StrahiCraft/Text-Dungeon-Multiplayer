package client.entity;

import utility.Stats;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private String name;
    private Stats stats;

    public Entity() {
        name = "Entity";
        stats = new Stats();
    }

    public Entity(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    public void takeDamage(float damage) {
        stats.setCurrentHealth(stats.getCurrentHealth() - damage + (damage > 0? damage *
                ((Math.min(stats.getArmor(), Stats.getMaxArmor())) / Stats.getMaxArmor()) : 0));

        if(stats.getCurrentHealth() <= 0) {
            handleDeath();
            return;
        }

        if(stats.getCurrentHealth() > stats.getMaxHealth()) {
            stats.setCurrentHealth(stats.getMaxHealth());
        }
    }

    public void increaseStats(Stats increase) {
        getStats().increaseStats(increase, 1);
    }
    public void decreaseStats(Stats decrease){
        getStats().increaseStats(decrease, -1);
    }

    public abstract void handleDeath();

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " +  stats;
    }
}
