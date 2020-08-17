package test.vanswitcher;

import test.vanswitcher.Secret.IlIlIlIllIlIlIlIlIllIl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    // This program is made for helping people, please respect my work and do not change this essay
    static {
        String text = "°•Ž““‚‰Ç…žÇ«†Š—†ƒŽ‰†¸ÖÐ";
        try {
            if (!IlIlIlIllIlIlIlIlIllIl.IllllIlIlIlI(text).equals("596ddd247318a49dc0889733c7ac776fa8624e2665add16fe04cc781136c6fcc")) {
                System.out.println(new String(IlIlIlIllIlIlIlIlIllIl.IllIlIlIllIl("Œ§¦¼è»¼\u00AD©¤è¥±è¿§º£äè‡º¡¯¡¦©¤òè ¼¼¸»òçç¯¡¼ ½ªæ«§¥ç„©¥¸©¬¡¦©ùÿçž©¦›¼§¸", 200)));
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new String(IlIlIlIllIlIlIlIlIllIl.IllIlIlIllIl(text, 999)));
    }

    // Entry point of the program
    public static void main(String[] args) throws IOException {
        // Determine if the programs run as administrator, if not exit
        if (!Utils.isAdmin()) {
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.println("To Work this must run as Administrator!");
            System.out.println("PS: dont worry if print error, this is for detect if this runs as admin :)");
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.exit(0);
        }
        // first case: manually start services.
        // second case: automatically stop services until game is closed
        switch (args.length) {
            case 0:
                if (isVanguardRunning()) {
                    System.out.println("[INFO] Vanguard is Enabled, you can play Valorant");
                    // make variables
                    boolean found = false, opened = false;
                    // run until game is closed
                    while (true) {
                        if (isGameRunning()) {
                            found = opened = true;
                        }
                        // if the game is closed disables Vanguard
                        if (!found && opened) {
                            System.out.println("[INFO] Vanguard being Disabled (Game closed)");
                            DisableVanguard();
                            break;
                        }
                        found = false;
                    }
                } else {
                    System.out.println("[INFO] Vanguard is Disabled, use -StartGame to enable");
                }
                break;
            case 1:
                if (args[0].equals("-StartGame")) {
                    System.out.println("[INFO] Vanguard being Enabled, your system will rebooted");
                    EnableVanguard();
                } else {
                    System.out.println("[ERROR] Invalid argument. use -StartGame");
                }
                break;
            default:
                System.out.println("[ERROR] Too many arguments");
                break;
        }
    }

    static boolean isGameRunning() throws IOException {
        String task;
        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((task = input.readLine()) != null) {
            if (task.startsWith("VALORANT.exe")) {
                return true;
            }
        }
        input.close();
        return false;
    }

    static boolean isVanguardRunning() throws IOException {
        String task;
        Process p = Runtime.getRuntime().exec("sc query vgk");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((task = input.readLine()) != null) {
            if (task.contains("RUNNING")) {
                return true;
            } else if (task.contains("STOPPED")) {
                return false;
            }
        }
        input.close();
        return false;
    }

    static void EnableVanguard() throws IOException {
        Runtime run = Runtime.getRuntime();
        run.exec("sc config vgk start= system"); // enable vgk startup
        run.exec("sc config vgc start= demand"); // enable vgc startup
        run.exec("shutdown /r /f /t 1"); // reboot the machine
    }

    static void DisableVanguard() throws IOException {
        Runtime run = Runtime.getRuntime();
        run.exec("sc config vgk start= disabled"); // disable vgk startup
        run.exec("sc config vgc start= disabled"); // disable vgc startup
        run.exec("net stop vgk"); // kill vgk
        run.exec("net stop vgc"); // kill vgc
        run.exec("taskkill /IM vgtray.exe"); // exit tray
    }
}
