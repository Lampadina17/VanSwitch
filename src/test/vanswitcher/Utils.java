package test.vanswitcher;

import java.io.*;
import java.util.prefs.Preferences;

import static java.lang.System.setErr;
import static java.util.prefs.Preferences.systemRoot;

public class Utils {

    // this perform admin check
    // Code taken from StackOverflow
    public static boolean isAdmin() {
        Preferences preferences = systemRoot();
        synchronized (System.err) {
            setErr(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                }
            }));

            try {
                preferences.put("foo", "bar"); // SecurityException on Windows
                preferences.remove("foo");
                preferences.flush(); // BackingStoreException on Linux
                return true;
            } catch (Exception exception) {
                return false;
            } finally {
                setErr(System.err);
            }
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
}
