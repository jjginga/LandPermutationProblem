package com.jjginga.SearchTechnique;

import com.jjginga.Result.SearchResult;
import com.jjginga.State.IState;

import java.util.*;

public class IterativeDDFS extends AbstractSearchTechnique {

    public IterativeDDFS(IState initialState, int objective) {
        super(initialState, objective);
    }

    int generatedStates = 0;

    @Override
    protected SearchResult performSearch(IState initialState, int objective) {
        int maxDepth = 0;
        SearchResult result = null;

        while(result == null) {
            result = deepLimitedSearch(initialState, objective, maxDepth);
            maxDepth++;
        }


        return result;
    }

    private SearchResult deepLimitedSearch(IState initialState, int objective, int depthLimit) {

        Map<Integer, Stack<IState>> map = new HashMap<>();
        Set<IState> visited = new HashSet<>();

        map.put(0, new Stack<>());
        map.get(0).add(initialState);
        visited.add(initialState);

        for(int level = 0; level < depthLimit; level++) {
            Stack<IState> stack = map.getOrDefault(level, new Stack<>());

            if(stack.isEmpty())
                return new SearchResult(null, false, generatedStates,level);

            while(!stack.isEmpty()) {
                IState currentState = stack.pop();

                if(currentState.getObjectiveValue() <= objective)
                    return new SearchResult(currentState, true, generatedStates,level);

                if(Thread.currentThread().isInterrupted()) {
                    captureCurrentState(currentState,generatedStates,level);
                    latch.countDown();
                }

                List<IState> successors = currentState.generateSucessors();
                generatedStates += successors.size();

                map.putIfAbsent(level+1, new Stack<>());
                Stack<IState> nextLevelStack = map.get(level+1);

                for(IState successor : successors) {
                    if(!visited.contains(successor)){
                        nextLevelStack.add(successor);
                        visited.add(successor);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Iterative Deep-First Search";
    }
}
