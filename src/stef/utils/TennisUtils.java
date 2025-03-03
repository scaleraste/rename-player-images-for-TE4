package stef.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class TennisUtils {
    static StringBuilder console = new StringBuilder();
    static String output = "";

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
                for (String fullName : playersList) {

                    // cleaning the file names
                    String surnameRaw = file.getName();
                    String surname = file.getName().replace(".png", "").toLowerCase();
                    if (surname.contains("_")) {
                        surname = surname.substring(0, surname.indexOf("_"));
                    }
                    if (surname.contains("-")) {
                        surname = surname.substring(0, surname.indexOf("-"));
                    }

                    // checking correspondences
                    if (fullName.toLowerCase().contains(surname)) {

                        // renaming files
                        boolean result = file.renameTo(new File(filesToRename_path + "\\" + fullName + suffix + ".png"));

                        // info on results
                        if (result) {
                            String renameSuccess = surnameRaw + " renamed to " + fullName + suffix + ".png";
                            System.out.println(renameSuccess);
                            console.append(renameSuccess).append("\n");
                        }
                    }

                }
            }
        }

        output = console.toString();
    }

    public static void renameFlags(String pathFlagsMapping, String pathFilesToRename) throws IOException{

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

                            String pngNoExt = file.getName().replace(".png", "");
                            if (pngNoExt.contains("-flag")) {
                                pngNoExt = pngNoExt.replace("-flag", "");
                            }

                            // checking correspondences
                            if (line.toLowerCase().contains(pngNoExt.toLowerCase())) {
                                newFlagName = line.substring(line.indexOf("=") + 2);

                                // renaming files
                                boolean result = file.renameTo(new File(pathFilesToRename + "\\" + newFlagName.toLowerCase() + ".png"));

                                // info on results
                                if (result) {
                                    String renameSuccess = file.getName() + " renamed to " + newFlagName.toLowerCase() + ".png";
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

    public static String removeString(String originalString, String stringToRemove) {
        return originalString.replace(stringToRemove, "");
    }

    public static boolean isFlagged(boolean flag) {
        return flag;
    }

}




