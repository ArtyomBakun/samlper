package com.arba.sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemoryItem
{
    private StringProperty fullName;
    private StringProperty humanReadableName;
    private IntegerProperty bytes;
    private IntegerProperty instances;

    public MemoryItem(){
        this.fullName = new SimpleStringProperty();
        this.bytes = new SimpleIntegerProperty();
        this.instances = new SimpleIntegerProperty();
    }

    public MemoryItem(String fullName, int bytes, int instances)
    {
        this.fullName = new SimpleStringProperty(fullName);
        this.bytes = new SimpleIntegerProperty(bytes);
        this.instances = new SimpleIntegerProperty(instances);
    }
    
    public MemoryItem update(String line){
        String[] s = line.trim().split("\\s+");
        setFullName(s[3]);
        setBytes(Integer.parseInt(s[2]));
        setInstances(Integer.parseInt(s[1]));
        return this;
    }

    public String getFullName()
    {
        return fullName.get();
    }

    public StringProperty fullNameProperty()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName.set(fullName);
    }

    public String getHumanReadableName()
    {
        return humanReadableName.get();
    }

    public StringProperty humanReadableNameProperty()
    {
        return humanReadableName;
    }

    public void setHumanReadableName(String humanReadableName)
    {
        this.humanReadableName.set(humanReadableName);
    }

    public double getBytes()
    {
        return bytes.get();
    }

    public IntegerProperty bytesProperty()
    {
        return bytes;
    }

    public void setBytes(int bytes)
    {
        this.bytes.set(bytes);
    }

    public int getInstances()
    {
        return instances.get();
    }

    public IntegerProperty instancesProperty()
    {
        return instances;
    }

    public void setInstances(int instances)
    {
        this.instances.set(instances);
    }
}
