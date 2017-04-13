package com.arba.sample.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ThreadInfo
{
    private StringProperty name = new SimpleStringProperty();
    private BooleanProperty daemon = new SimpleBooleanProperty();
    private ListProperty<String> state = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ListProperty<String> stacks = new SimpleListProperty<>(FXCollections.observableArrayList());

    public ThreadInfo()
    {
    }

    public ThreadInfo(String name)
    {
        this.name.setValue(name);
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public boolean getDaemon()
    {
        return daemon.get();
    }

    public BooleanProperty daemonProperty()
    {
        return daemon;
    }

    public void setDaemon(boolean daemon)
    {
        this.daemon.set(daemon);
    }

    public ObservableList<String> getState()
    {
        return state.get();
    }

    public ListProperty<String> stateProperty()
    {
        return state;
    }

    public void setState(ObservableList<String> state)
    {
        this.state.set(state);
    }

    public ObservableList<String> getStacks()
    {
        return stacks.get();
    }

    public ListProperty<String> stacksProperty()
    {
        return stacks;
    }

    public void setStacks(ObservableList<String> stacks)
    {
        this.stacks.set(stacks);
    }
}
