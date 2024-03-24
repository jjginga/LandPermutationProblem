package com.jjginga.SearchTechnique;

import com.jjginga.Result.SearchResult;
import com.jjginga.State.IState;

import java.util.*;

public class BFS extends AbstractSearchTechnique {

    public BFS(IState initialState, int objective) {
        super(initialState, objective);
    }

    @Override
    protected SearchResult performSearch(IState initialState, int objective) {
        Queue<IState> queue = new LinkedList<>();
        Set<IState> visited = new HashSet<>();
        int generatedStates = 0;
        int maxDepth = 0;

        queue.add(initialState);
        visited.add(initialState);

        while(!queue.isEmpty()) {
            IState currentState = queue.poll();
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
                    queue.add(successor);
                    visited.add(successor);
                }
            }


        }

        //all possible states have been generated
        return new SearchResult(null, false, generatedStates,maxDepth);
    }

    @Override
    public String toString() {
        return "Breath First Search";
    }
}
