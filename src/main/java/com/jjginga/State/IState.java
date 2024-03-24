package com.jjginga.State;

import java.util.List;

public interface IState {

    public List<IState> generateSucessors();

    public void evaluate();

    public int getObjectiveValue();

    public int getDepth();

}
