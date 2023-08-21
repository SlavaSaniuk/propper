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
     * Open BufferedWriter for specified file with mode.
     * @param aFile - file.
     * @param isAppend - is append mode.
     * @return - writer.
     * @throws IOException - If IO Exception occurs.
     */
    public static BufferedWriter openWriter(File aFile, boolean isAppend) throws IOException {
        Objects.requireNonNull(aFile);
        if (!aFile.canWrite()) {
            throw new IOException(String.format("File: [%s] cannot be written.", aFile.getPath()));
        } else {
            return new BufferedWriter(new FileWriter(aFile, isAppend));
        }
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

}
