package com.jjginga.SearchTechnique;

import com.jjginga.Result.SearchResult;
import com.jjginga.State.IState;

import java.util.*;

public class DFS extends AbstractSearchTechnique {

    public DFS(IState initialState, int objective) {
        super(initialState, objective);
    }

    @Override
    protected SearchResult performSearch(IState initialState, int objective) {
        Stack<IState> stack = new Stack<>();
        Set<IState> visited = new HashSet<>();

        int generatedStates = 0;
        int maxDepth = 0;

        stack.add(initialState);
        visited.add(initialState);

        while(!stack.isEmpty()) {
            IState currentState = stack.pop();
            maxDepth = Math.max(currentState.getDepth(), maxDepth);

            if(Thread.currentThread().isInterrupted()) {
                return captureCurrentState(currentState,generatedStates,maxDepth);
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
        return "Deapth-First Search";
    }
}
