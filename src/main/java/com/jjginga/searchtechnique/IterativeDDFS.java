package com.jjginga.searchtechnique;

import com.jjginga.result.SearchResult;
import com.jjginga.state.IState;

import java.util.*;

/**
 * Implements the Iterative Deepening Depth-First Search (IDDFS) algorithm.
 * IDDFS combines the space-efficiency of Depth-First Search (DFS) with the
 * optimal depth-level search of Breadth-First Search (BFS), making it effective
 * for searching in spaces where the solution depth is unknown.
 */

 public class IterativeDDFS extends AbstractSearchTechnique {

    public IterativeDDFS(IState initialState, int objective) {
        super(initialState, objective);
    }

    //keeps track of the number of generated states
    int generatedStates = 0;

    /**
     * Performs the IDDFS algorithm, incrementally increasing the depth limit
     * until the objective is met or the search space is fully explored.
     *
     * @param initialState The starting state of the search.
     * @param objective The goal to be achieved, often a target state.
     * @return SearchResult containing the outcome of the search.
     */
    @Override
    protected SearchResult performSearch(IState initialState, int objective) {
        int maxDepth = 0;
        SearchResult result = null;

        //iteratively increase the depth limit until a solution if found
        while(result == null) {
            result = deepLimitedSearch(initialState, objective, maxDepth);
            maxDepth++;
        }


        return result;
    }

    /**
     * Conducts a depth-limited search from the initial state up to a specified depth limit.
     * This method is called repeatedly with increasing depth limits by performSearch.
     *
     * @param initialState The state from which the depth-limited search starts.
     * @param objective The search objective.
     * @param depthLimit The maximum depth for this iteration of the search.
     * @return SearchResult indicating success or failure to find a solution within the depth limit.
     */
    private SearchResult deepLimitedSearch(IState initialState, int objective, int depthLimit) {

        //maps each depth level to its stack of states
        Map<Integer, Deque<IState>> map = new HashMap<>();
        //tracks visited states to avoid cycles
        Set<IState> visited = new HashSet<>();

        map.put(0, new ArrayDeque<>());
        map.get(0).add(initialState);
        visited.add(initialState);

        for(int level = 0; level < depthLimit; level++) {
            Deque<IState> deque = map.getOrDefault(level, new ArrayDeque<>());

            //all the states have been explored and no solution has been foind
            if(deque.isEmpty())
                return new SearchResult(null, false, generatedStates,level);

            while(!deque.isEmpty()) {
                IState currentState = deque.pop();

                //check if a solution is foind
                if(currentState.getObjectiveValue() <= objective)
                    //found a solution meeting the objective
                    return new SearchResult(currentState, true, generatedStates,level);

                if(Thread.currentThread().isInterrupted()) {
                    captureCurrentState(currentState,generatedStates,level);
                    latch.countDown();
                }

                //generates successors and puts them in the map
                List<IState> successors = currentState.generateSuccessors();
                generatedStates += successors.size();

                map.putIfAbsent(level+1, new ArrayDeque<>());
                Deque<IState> nextLevelDeque = map.get(level+1);

                for(IState successor : successors) {
                    if(!visited.contains(successor)){
                        nextLevelDeque.add(successor);
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
