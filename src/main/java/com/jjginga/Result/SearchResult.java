package com.jjginga.Result;

import com.jjginga.State.IState;

public class SearchResult {
    private IState state;
    boolean solutionFound;
    int depth;
    int generatedStates;

    long executionTime;

    public SearchResult() {
        this.solutionFound = false;
    }

    public SearchResult(IState state, boolean solutionFound, int generatedStates, int depth) {
        this.solutionFound = solutionFound;
        this.state = state;
        this.generatedStates = generatedStates;
        this.depth = depth;
    }


    public boolean isSolutionFound() {
        return solutionFound;
    }

    public void setSolutionFound(boolean solutionFound) {
        this.solutionFound = solutionFound;
    }

    public int getGeneratedStates() {
        return generatedStates;
    }

    public void setGeneratedStates(int generatedStates) {
        this.generatedStates = generatedStates;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "depth= " + depth +
                ", generatedStates= " + generatedStates +
                ", executionTime= " + executionTime;
    }
}
