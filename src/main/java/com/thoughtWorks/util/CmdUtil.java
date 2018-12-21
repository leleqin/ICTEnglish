package com.thoughtWorks.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CmdUtil {
    public static void runCmd(String cmd) {
        String s;
        try {
            System.out.println("runCmd");
            Runtime rt = Runtime.getRuntime();
            Process pc = rt.exec(cmd);
            ProcessUitl pu = new ProcessUitl(pc.getInputStream());
            pu.run();
            pc.waitFor();
            pc.getInputStream().close();
            System.out.println("after runCmd");

        } catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

    }
}
