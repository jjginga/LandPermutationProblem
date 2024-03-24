package com.jjginga.SearchTechnique;

import com.jjginga.Result.SearchResult;
import com.jjginga.State.IState;

import java.util.List;

public abstract class AbstractSearchTechnique implements ISearchTechnique{

    protected long executionTime;
    private IState initialState;
    private int objective;

    protected SearchResult notFoundResult;
    public AbstractSearchTechnique() {
    }

    public AbstractSearchTechnique(IState initialState, int objective) {
        this.initialState = initialState;
        this.objective = objective;
        this.executionTime = 0;
    }

    @Override
    public SearchResult call()  {
        return search(this.initialState, this.objective);
    }

    protected abstract SearchResult performSearch(IState initialState, int objective);

    @Override
    public SearchResult search(IState initialState, int objective) {
        long startTime = System.nanoTime();
        SearchResult result = performSearch(initialState, objective);
        this.executionTime = System.nanoTime() - startTime;

        result.setExecutionTime(this.getExecutionTime());

        return result;
    }


    protected SearchResult captureCurrentState(IState state, int generatedStates, int depth) {
        this.notFoundResult = new SearchResult(state, false, generatedStates, depth);
        return this.notFoundResult;
    }

    protected List<IState> generateSuccessors(IState currentState) {
        return currentState.generateSucessors();

    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public IState getInitialState() {
        return initialState;
    }

    public void setInitialState(IState initialState) {
        this.initialState = initialState;
    }

    public int getObjective() {
        return objective;
    }

    public void setObjective(int objective) {
        this.objective = objective;
    }

    public SearchResult getNotFoundResult() {
        return notFoundResult;
    }

    public void setNotFoundResult(SearchResult notFoundResult) {
        this.notFoundResult = notFoundResult;
    }
}
