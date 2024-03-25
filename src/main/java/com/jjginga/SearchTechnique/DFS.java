package com.jjginga.SearchTechnique;

import com.jjginga.Result.SearchResult;
import com.jjginga.State.IState;

import java.util.*;

/**
 * Implements the Depth-First Search (DFS) algorithm extending the AbstractSearchTechnique for searching through states.
 */
public class DFS extends AbstractSearchTechnique {

    /**
     * Constructs a DFS search with an initial state and an objective.
     *
     * @param initialState The state from which to start the search.
     * @param objective The search objective, define in terms of target state.
     */
    public DFS(IState initialState, int objective) {
        super(initialState, objective);
    }

    /**
     * Performs the actual DFS search.
     *
     * @param initialState The starting state of the search.
     * @param objective The objective to achieve.
     * @return A SearchResult object encapsulating the result of the search.
     */
    @Override
    protected SearchResult performSearch(IState initialState, int objective) {
        //in both cases access is O(1)
        Stack<IState> stack = new Stack<>();
        Set<IState> visited = new HashSet<>();

        //count of all states that have been generated
        int generatedStates = 0;
        //keeps track of the maximum depth of the search tree
        int maxDepth = 0;

        stack.add(initialState);
        visited.add(initialState);

        while(!stack.isEmpty()) {
            IState currentState = stack.pop();
            maxDepth = Math.max(currentState.getDepth(), maxDepth);

            if(Thread.currentThread().isInterrupted()) {
                captureCurrentState(currentState,generatedStates,maxDepth);
                latch.countDown();
            }

            //check if solution is found
            if(currentState.getObjectiveValue() <= objective) {
                return new SearchResult(currentState, true, generatedStates,currentState.getDepth());
            }

            //generate and add to queue all successor states
            List<IState> successors = generateSuccessors(currentState);
            generatedStates += successors.size();
            for(IState successor : successors) {
                if(!visited.contains(successor)) {
                    stack.add(successor);
                    visited.add(successor);
                }
            }

        }

        //all possible states have been generated
        return new SearchResult(null, false, generatedStates,maxDepth);
    }

    @Override
    public String toString() {
        return "Depth-First Search";
    }
}
