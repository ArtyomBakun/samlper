package com.arba.sample.model;

import java.util.ArrayList;
import java.util.List;

public class ThreadInfo
{
    private String name;
    private List<String> state = new ArrayList<>();
    private List<String> samples = new ArrayList<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<String> getState()
    {
        return state;
    }

    public void setState(List<String> state)
    {
        this.state = state;
    }

    public List<String> getSamples()
    {
        return samples;
    }

    public void setSamples(List<String> samples)
    {
        this.samples = samples;
    }
}
