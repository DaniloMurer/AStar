package main.java.com.energyeet.astar.util.helper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for file util methods
 */
public class FileHelper {
    /**
     * Write a text in a file
     * @param file {@link File} file to create
     * @param text {@link String} text that has to be in the file
     */
    public void writeFile(File file, String text) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Read the content from a file
     * @param file {@link File} the file that has to be read
     * @return {@link String} content of the file
     */
    public String readFile(File file) {
        //Create data char array based on the length of the file
        char[] data = new char[(int) file.length()];
        try {
            FileReader fileReader = new FileReader(file);
            fileReader.read(data);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new String(data);
    }
}
