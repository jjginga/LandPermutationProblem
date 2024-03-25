package com.jjginga.Result;

import com.jjginga.State.IState;

public class SearchResult {
    private IState state;
    boolean solutionFound;
    int depth;
    int generatedStates;

    long executionTime;

    public SearchResult(IState state, boolean solutionFound, int generatedStates, int depth) {
        this.solutionFound = solutionFound;
        this.state = state;
        this.generatedStates = generatedStates;
        this.depth = depth;
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

    @Override
    public String toString() {
        return String.format("depth = %d, generatedStates = %d, executionTime = %.4f s", depth, generatedStates, (executionTime/1_000_000_000.0));
    }
}
