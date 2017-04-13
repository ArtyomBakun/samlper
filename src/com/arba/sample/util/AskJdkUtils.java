package com.arba.sample.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AskJdkUtils
{
    public static List<String> findJavaProcesses(){
        return executeCommand("jps -mlvV");
    }

    public static List<String> getMemoryUsageForProcesses(Integer pid){
        return executeCommand("jmap -heap " + pid);
    }
    
    public static List<String> getShortMemoryUsageForProcesses(Integer pid){
        return executeCommand("jstat -gcutil " + pid);
    }

    public static List<String> getMemoryMapForProcesses(Integer pid){
        return executeCommand("jmap -histo " + pid);
    }

    public static List<String> getThreadsDumpForProcesses(Integer pid){
        return executeCommand("jstack " + pid);
    }

    public static List<String> detectDeadlocksForProcesses(Integer pid){
        return executeCommand("jstack -F " + pid);
    }

    private static List<String> executeCommand(String program){
        List<String> answer = new ArrayList<>();
        Process proc = null;
        try
        {
            proc = Runtime.getRuntime().exec(program);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Stream.of(proc.getErrorStream(), proc.getInputStream()).parallel().map(
                (InputStream isForOutput) -> {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(isForOutput))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            answer.add(line);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return answer;
        }).collect(Collectors.toList());
        return answer;
    }
    
    private static List<String> executeCommandOld(String command){
        List<String> answer = new ArrayList<>();
        try
        {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            System.out.println("here");
            BufferedReader reader =new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine())!= null) {
                answer.add(line);
                System.out.println(line);
            }
        }
        catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
        }
        return answer;
    }
}
