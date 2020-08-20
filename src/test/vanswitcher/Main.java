package test.vanswitcher;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Utils util = new Utils();
        if (!WindowsAdminUtil.isUserWindowsAdmin()) {
            logger.log(Level.SEVERE, "This program requires Administrator privileges");
            System.exit(1);
        }
        // Parse Argument
        Options options = new Options();
        Option play = new Option("p", "play", false, "launch the game");
        play.setRequired(false);
        options.addOption(play);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (cmd.hasOption("p")) { // -p or -play
            if (util.isVanguardRunning()) {
                logger.log(Level.INFO, "Vanguard is Enabled, you can play Valorant");
                // make variables
                boolean found = false, opened = false;
                // run until game is closed
                while (true) {
                    if (util.isGameRunning()) {
                        found = opened = true;
                    }
                    // if the game is closed disables Vanguard
                    if (!found && opened) {
                        logger.log(Level.INFO, "Vanguard being Disabled (Game closed)");
                        disableVan();
                        break;
                    }
                    found = false;
                }
            } else {
                logger.log(Level.INFO, "Vanguard is Disabled");
            }
        } else {
            logger.log(Level.INFO, "Vanguard being Enabled, your system will restarted");
            enableVan();
        }
    }

    private static void disableVan() {
        Runtime run = Runtime.getRuntime();
        // disable vgk, disable vgc, stop vgk, stop vgc, stop vgtray
        try {
            run.exec("sc config vgk start= disabled && sc config vgc start= disabled && net stop vgk && net stop vgc && taskkill /IM vgtray.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void enableVan() {
        Runtime run = Runtime.getRuntime();
        // enable vgk && enable vgc && reboot system
        try {
            run.exec("sc config vgk start= system && sc config vgc start= demand && shutdown /r /f /t 1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
