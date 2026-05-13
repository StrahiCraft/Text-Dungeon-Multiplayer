package client.graphics;

public class TextRenderer {
    public static void printText(String text) {
        System.out.println(text + Color.resetColor());
    }

    public static void skipLine(){
        skipLine(1);
    }

    public static void skipLine(int count){
        for(int i = 0; i < count; i++){
            System.out.println();
        }
        System.out.print(Color.resetColor());
    }
}
