package com.jjginga.searchtechnique;

import com.jjginga.result.SearchResult;
import com.jjginga.state.IState;

import java.util.*;

/**
 * Implements the Breadth-First Search (BFS) algorithm, extending AbstractSearchTechnique.
 * BFS explores the nearest nodes first before moving to the next level of nodes. This method
 * is particularly useful for finding the shortest path on unweighted graphs or for searching
 * through state spaces where all actions have the same cost.
 */
public class BFS extends AbstractSearchTechnique {

    public BFS(IState initialState, int objective) {
        super(initialState, objective);
    }

    /**
     * Executes the Breadth-First Search algorithm from the provided initialState towards
     * achieving the specified objective. Utilizes a queue to manage the search frontier,
     * ensuring that states are explored in a breadth-first manner. A set is used to track
     * visited states, avoiding redundant exploration and potential cycles.
     *
     * @param initialState The initial state from where the BFS search starts.
     * @param objective The goal state or condition to achieve, such as minimizing the number of borders.
     * @return A SearchResult object encapsulating the outcome of the search.
     */
    @Override
    protected SearchResult performSearch(IState initialState, int objective) {

        Queue<IState> queue = new LinkedList<>();
        Set<IState> visited = new HashSet<>();

        //count of all states that have been generated
        int generatedStates = 0;
        //keeps track of the maximum depth of the search tree
        int maxDepth = 0;

        queue.add(initialState);
        visited.add(initialState);

        while(!queue.isEmpty()) {
            IState currentState = queue.poll();
            maxDepth = Math.max(currentState.getDepth(), maxDepth);

            //interruption check for externally managed timeouts or cancellations.
            if (Thread.currentThread().isInterrupted()) {
                //capture the current search
                captureCurrentState(currentState, generatedStates, maxDepth);
                latch.countDown();
            }

            //check if solution is found
            if (currentState.getObjectiveValue() <= objective) {
                //found a solution meeting the objective.
                return new SearchResult(currentState, true, generatedStates, currentState.getDepth());
            }

            //generate and add to queue all successor states
            List<IState> successors = generateSuccessors(currentState);
            generatedStates += successors.size();
            for (IState successor : successors) {
                if (!visited.contains(successor)) {
                    queue.add(successor);
                    visited.add(successor);
                }
            }
        }

        //search space was fully explored without finding a solution.
        return new SearchResult(null, false, generatedStates,maxDepth);
    }

    @Override
    public String toString() {
        return "Breath First Search";
    }
}
