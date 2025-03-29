package Utils;

import java.io.File;

public class Utils {
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    public static double getFileSizeKb(File file) {
        return (double) file.length() / 1024;
    }

    public static void printOutput(String thing, String... strings) {
        System.out.print(thing);
        for (String string : strings) {
            System.out.print(string);
        }
        System.out.println();
        
    }
}
