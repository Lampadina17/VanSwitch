package test.vanswitcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    public boolean isGameRunning() {
        String task;
        try {
            Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\tasklist.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((task = input.readLine()) != null) {
                if (task.startsWith("VALORANT.exe"))
                    return true;
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isVanguardRunning() {
        String task;
        try {
            Process p = Runtime.getRuntime().exec("sc query vgk");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((task = input.readLine()) != null) {
                if (task.contains("RUNNING"))
                    return true;
                else if (task.contains("STOPPED"))
                    return false;
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
