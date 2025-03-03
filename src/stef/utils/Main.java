package stef.utils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
/*
        // players stuff
        final String playersDB = "D:\\Documenti\\Players.ATP.ini";
        final String playersFolder = "D:\\Documenti\\a";
        TennisUtils.renamePlayers(playersDB, playersFolder);
*/
//        new Frame();
/*
        // flags stuff
        final String flagsMapping = "C:\\Users\\Stefano\\Desktop\\TE4Utils\\Flags\\DB.ini";
        final String flagsFolder = "C:\\Users\\Stefano\\Desktop\\TE4Utils\\Flags";
        TennisUtils.renameFlags(flagsMapping, flagsFolder);
*/
        String prova = "country-flag";
        prova = TennisUtils.removeString(prova, "-flag");
        System.out.println(prova);

    }
}
