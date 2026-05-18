package client.graphics;

import client.inventory.item.Rarity;

public class Color {
    public static String resetColor(){
        return "\u001B[0m";
    }

    /**
     * Takes in lower case string with a color name supported by the ANSI escape character standard.
     * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code#Colors">Check supported colors here</a>
     * @param color
     * String of a color that is supported by the ANSI escape character standard
     * @return
     * an ANSI escape character for color
     */
    public static String getColor(String color){
        return switch (color.toLowerCase()) {
            case "black" -> "\u001B[30m";
            case "red" -> "\u001B[31m";
            case "green" -> "\u001B[32m";
            case "yellow" -> "\u001B[33m";
            case "blue" -> "\u001B[34m";
            case "magenta" -> "\u001B[35m";
            case "cyan" -> "\u001B[36m";
            case "white" -> "\u001B[37m";
            case "gray" -> "\u001B[90m";
            case "bright red" -> "\u001B[91m";
            case "bright green" -> "\u001B[92m";
            case "bright yellow" -> "\u001B[93m";
            case "bright blue" -> "\u001B[94m";
            case "bright magenta" -> "\u001B[95m";
            case "bright cyan" -> "\u001B[96m";
            case "bright white" -> "\u001B[97m";
            default -> "\u001B[0m";
        };
    }

    public static String getRarityColor(Rarity rarity){
        return switch (rarity){
            case COMMON -> getColor("gray");
            case UNCOMMON -> getColor("green");
            case RARE -> getColor("blue");
            case EPIC -> getColor("magenta");
            case LEGENDARY -> getColor("bright yellow");
            case MYTHIC -> getColor("bright red");
        };
    }
}
