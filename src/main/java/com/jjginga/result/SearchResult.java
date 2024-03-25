package com.jjginga.result;

import com.jjginga.state.IState;

/**
 * Represents the result of a search operation, encapsulating the final state,
 * whether a solution was found, the depth reached, the number of generated states,
 * and the execution time of the search.
 */
public class SearchResult {
    private IState state;//final state reached by the search
    boolean solutionFound;//flag that indicates whether a solution satisfying the objective was found
    int depth;//maximum depth reached during the search
    int generatedStates;//total number of generated states

    long executionTime;//total execution time

    /**
     * Constructs a SearchResult with the specified properties.
     *
     * @param state The final state reached by the search.
     * @param solutionFound A boolean indicating whether a solution was found.
     * @param generatedStates The number of states generated during the search.
     * @param depth The maximum depth reached in the search space.
     */
    public SearchResult(IState state, boolean solutionFound, int generatedStates, int depth) {
        this.solutionFound = solutionFound;
        this.state = state;
        this.generatedStates = generatedStates;
        this.depth = depth;
    }

    /**
     * Sets the execution time of the search.
     *
     * @param executionTime The execution time in nanoseconds.
     */
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * Returns the final state reached by the search.
     *
     * @return The final state.
     */
    public IState getState() {
        return state;
    }

    /**
     * Sets the final state reached by the search.
     *
     * @param state The final state.
     */
    public void setState(IState state) {
        this.state = state;
    }

    /**
     * Returns a string representation of the search result, detailing the depth reached,
     * the number of generated states, and the execution time in seconds.
     *
     * @return A formatted string representation of the search result.
     */
    @Override
    public String toString() {
        return String.format("depth = %d, generatedStates = %d, executionTime = %.4f s", depth, generatedStates, (executionTime/1_000_000_000.0));
    }
}
