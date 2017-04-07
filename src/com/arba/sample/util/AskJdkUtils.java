package com.arba.sample.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AskJdkUtils
{
    public static List<String> findJavaProcesses(){
        return executeCommand("jps -mlvV");
    }

    public static List<String> getMemoryUsageForProcesses(Integer pid){
        return executeCommand("jmap -heap " + pid);
    }

    public static List<String> getMemoryMapForProcesses(Integer pid){
        return executeCommand("jmap -histo " + pid);
    }
    
    private static List<String> executeCommand(String command){
        List<String> answer = new ArrayList<>();
        try
        {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine())!= null) {
                answer.add(line);
            }
        }
        catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
        }
        return answer;
    }
}
