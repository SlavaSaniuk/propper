package me.saniukvyacheslav.core.repo.file;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileRepositoryUtils {

    public static List<String> readContentByLines(BufferedReader aReader) throws IOException {
        Objects.requireNonNull(aReader, "Specified [BufferedReader] cannot be null.");
        // Reset reader:
        aReader.reset();

        // List of read strings:
        List<String> stringsList = new ArrayList<>();

        String lastStr = null; // Last read str:
        boolean readFlag = true; // Read  flag;

        // Read line by line:
        while(readFlag) {
            String currStr = aReader.readLine();
            if (currStr != null) {
                if (lastStr != null) stringsList.add(lastStr +System.lineSeparator());
                lastStr = currStr.concat("");
            } else {
                stringsList.add(lastStr);
                readFlag = false;
            }
        }

        return stringsList;
    }

    /**
     * Write list of strings in file line by line. Each element in list is line of text.
     * If line doesn't contain {@link System#lineSeparator()} or '\n' character, method automatically add that character to it.
     * @param aListOfString - list of text lines.
     * @param aFile - file for writing.
     * @throws IOException - If IO exception occurs.
     */
    public static void writeStringsToFileLineByLine(List<String> aListOfString, File aFile) throws IOException {
        // Check parameters:
        Objects.requireNonNull(aListOfString, "Specified [List] instance of strings must be not null.");
        Objects.requireNonNull(aFile, "Specified [File] instance must be not null.");
        if (aListOfString.isEmpty()) return;

        // Open writer:
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(aFile))) {
            // Iterate through list of Strings:
            for (int i=0; i<aListOfString.size(); i++) {
                String currentStr = aListOfString.get(i);
                if (currentStr == null) continue; // Check for null;

                // If current string ends with '\n':
                if ((currentStr.contains(System.getProperty("line.separator"))) || (currentStr.contains("\n"))) {
                    // Write string:
                    writer.write(currentStr);
                }else {
                    // Check if current str is last str:
                    if (i == (aListOfString.size()-1)) writer.write(currentStr); // Simply write last str;
                    else {
                        currentStr += System.lineSeparator(); //Append line separator:
                        writer.write(currentStr);
                    }
                }
            }

            // Flush content:
            writer.flush();
        }
    }

    /**
     * Read file line by line.
     * @param aFile - file.
     * @return - list of strings.
     * @throws IOException - If IO exception occurs.
     */
    public static List<String> readStringsFromFileLineByLine(File aFile) throws IOException {
        // Check parameter:
        Objects.requireNonNull(aFile, "Specified [File] instance must be not null.");

        // Read line by line:
        List<String> readStrings = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(aFile))) {
            String currentLine; String previousLine = null;

            // Read file line by line:
            while (true) {

                currentLine = reader.readLine(); // Read current line;
                if (currentLine == null) { // If end of file:
                    if(previousLine != null) readStrings.add(previousLine);
                    break;
                }

                if(previousLine != null) readStrings.add(previousLine);
                previousLine = currentLine;
            }
        }

        return readStrings;

    }
}
