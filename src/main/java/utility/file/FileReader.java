package utility.file;

import client.graphics.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public static ArrayList<String> readFile(File file) {
        return readFile(file.getPath());
    }

    public static ArrayList<String> readFile(String filePath) {
        ArrayList<String> fileContents = new ArrayList<String>();

        try {
            File file = new File(filePath);
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String fileLine = fileReader.nextLine();
                fileContents.add(unFormatString(fileLine));
            }

            fileReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(Color.getColor("bright red") +  filePath + " not found!" + Color.resetColor());
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        return fileContents;
    }

    public static String unFormatString(String formatedString) throws NullPointerException {
        if(formatedString == null){
            throw new NullPointerException("formatedString cannot be null");
        }
        String[] unformattedStringArray = formatedString.split("=");
        return unformattedStringArray[unformattedStringArray.length - 1];
    }
}
