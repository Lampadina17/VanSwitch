package test.vanswitcher;

import java.io.OutputStream;
import java.io.PrintStream;
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
}
