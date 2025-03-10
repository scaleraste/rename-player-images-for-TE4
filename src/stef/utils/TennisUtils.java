package stef.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class TennisUtils {
    static StringBuilder console = new StringBuilder();
    static String output = "";

    /**
     * Renames player images downloaded from atptour.com or wtatennis.com to be used in TE4
     * @param   DB_path   the player's database path.
     * @param filesToRename_path the folder where the images to rename are located.
     */
    public static void renamePlayers(String DB_path, String filesToRename_path) throws IOException {
        String suffix;

        FileReader fileReader = new FileReader(DB_path);
        ArrayList<String> playersList = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            playersList.add(line);
        }

        bufferedReader.close();
        fileReader.close();

        // folder
        File folder = new File(filesToRename_path);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {

                // reading image sizes (WxH) and adding suffix in case
                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth();
                int height = image.getHeight();
                if (width == 300 && height == 300) {
                    suffix = "_HS";
                } else suffix = "";

                // it cycles on the players' list
                for (String DB_nameSurname : playersList) {

                    // cleaning the file names
                    String surnameImageRaw = file.getName();
                    String extension = surnameImageRaw.substring(surnameImageRaw.lastIndexOf("."));
                    String surnameImage = getString(file, surnameImageRaw);

                    // checking correspondences
                    if (DB_nameSurname.toLowerCase().contains(" "+ surnameImage)) {

                        // renaming files
                        boolean result = file.renameTo(new File(filesToRename_path + "\\" + DB_nameSurname + suffix + extension));

                        // info on results
                        if (result) {
                            String renameSuccess = surnameImageRaw + " renamed to " + DB_nameSurname + suffix + extension;
                            System.out.println(renameSuccess);
                            console.append(renameSuccess).append("\n");
                        }
                    }

                }
            }
        }

        output = console.toString();
    }

    private static String getString(File file, String surnameImageRaw) {
        String extension = surnameImageRaw.substring(surnameImageRaw.lastIndexOf("."));

        String surnameImage = file.getName().replace(extension, "").toLowerCase();
        if (surnameImage.contains("_")) {
            surnameImage = surnameImage.substring(0, surnameImage.indexOf("_"));
        }
        if (surnameImage.contains("-")) {
            surnameImage = surnameImage.substring(0, surnameImage.indexOf("-"));
        }
        return surnameImage;
    }

    /**
     * Renames flags images into 2 letter code format.
     * @param   pathFlagsMapping   the flag's database path.
     * @param   pathFilesToRename  the folder where the images to rename are located.
     */
    public static void renameFlags(String pathFlagsMapping, String pathFilesToRename) {

        File fileObj = new File(pathFlagsMapping);
        Scanner flagsIndex = null;
        try {
            flagsIndex = new Scanner(fileObj);
        } catch (FileNotFoundException e) {
            System.out.println("No files to rename in path.");
        }

        String newFlagName;

        File folder = new File(pathFilesToRename);
        ArrayList<File> flags = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));

        for (File flag : flags) {

            if (flag.isFile()) {

                try {
                    while (true) {
                        assert flagsIndex != null;
                        if (!flagsIndex.hasNext()) break;

                        String line = flagsIndex.nextLine();
                        for (File file : flags) {

                            String extension = file.getName().substring(file.getName().lastIndexOf("."));
                            String pngNoExt = file.getName().replace(extension, "");
                            if (pngNoExt.contains("-flag")) {
                                pngNoExt = pngNoExt.replace("-flag", "");
                            }

                            // checking correspondences
                            if (line.toLowerCase().contains(pngNoExt.toLowerCase())) {
                                newFlagName = line.substring(line.indexOf("=") + 2);

                                // renaming files
                                boolean result = file.renameTo(new File(pathFilesToRename + "\\" + newFlagName.toLowerCase() + extension));

                                // info on results
                                if (result) {
                                    String renameSuccess = file.getName() + " renamed to " + newFlagName.toLowerCase() + extension;
                                    System.out.println(renameSuccess);
                                    console.append(renameSuccess).append("\n");
                                }

                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("No index file found in path.");
                }
            }
        }

        output = console.toString();
    }

    /**
     * Inverts player's name from "SurnameName" to "Name Surname"
     * @param   DB_path   the player's database path.
     * @param filesToRename_path the folder where the images to rename are located.
     */
    public static void rebuildNames(String DB_path, String filesToRename_path) throws IOException {
        String suffix = "_renamed";

        FileReader fileReader = new FileReader(DB_path);
        ArrayList<String> playersList = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            playersList.add(line);
        }

        bufferedReader.close();
        fileReader.close();

        // folder
        File folder = new File(filesToRename_path);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {

                // it cycles on the players' list
                for (String fullName : playersList) {

                    // cleaning the file names
                    String extension = file.getName().substring(file.getName().lastIndexOf("."));
                    String invertedNameNoSpace = file.getName().replace(extension, "");
                    StringBuilder stringBuilder = new StringBuilder();

                    StringCharacterIterator iterator = new StringCharacterIterator(invertedNameNoSpace);
                    String invertedName = "";
                    String name;

                    for (char c = iterator.first(); c != CharacterIterator.DONE; c = iterator.next()) {
                        if (Character.isUpperCase(c)) {
                            stringBuilder.append(" ").append(c);
                        } else stringBuilder.append(c);

                        invertedName = stringBuilder.toString().trim();
                    }

                    String[] split = invertedName.split(" ");
                    try {
                        name = split[1] + " " + split[0];
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    // checking correspondences
                    if (fullName.toLowerCase().contains(name.toLowerCase())) {

                        // renaming files
                        boolean result = file.renameTo(new File(filesToRename_path + "\\" + fullName + suffix + extension));

                        // info on results
                        if (result) {
                            String renameSuccess = invertedNameNoSpace + " renamed to " + fullName + suffix + extension;
                            System.out.println(renameSuccess);
                            console.append(renameSuccess).append("\n");
                        }
                    }

                }

            }
        }

        output = console.toString();
    }

}




