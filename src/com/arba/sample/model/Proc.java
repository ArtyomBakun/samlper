package com.arba.sample.model;

import javafx.scene.control.RadioButton;

public class Proc extends RadioButton
{
    private Integer pid;
    private String cmdLine;

    public Proc()
    {
        super();
    }
    
    public Proc(final String jps)
    {
        super();
        int limiter = jps.indexOf(" ");
        pid = Integer.parseInt(jps.subSequence(0, limiter).toString());
        cmdLine = jps.subSequence(limiter + 1, jps.length()).toString();
        setText("" + pid);
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
    }

    public String getCmdLine()
    {
        return cmdLine;
    }

    public void setCmdLine(String cmdLine)
    {
        this.cmdLine = cmdLine;
    }

}
