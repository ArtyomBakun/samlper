package com.arba.sample.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ThreadInfo
{
    private StringProperty name = new SimpleStringProperty();
    private ListProperty<String> state = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ListProperty<String> stacks = new SimpleListProperty<>(FXCollections.observableArrayList());

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
