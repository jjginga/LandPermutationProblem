package com.jjginga.searchtechnique;

import com.jjginga.result.SearchResult;
import com.jjginga.state.IState;

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
     * @param initialState The starting state of the search  from the provided initialState towards achieving the specified objective.
     * DFS is an algorithm for traversing or searching tree or graph data structures. It starts at the root (selecting
     * some arbitrary node as the root in the case of a graph) and explores as far as possible along each branch before
     * backtracking. This method is particularly suited for scenarios where a complete exploration of one branch is
     * preferred before moving to another branch.
     *
     * The use of a Stack data structure enables the algorithm to adopt a Last-In-First-Out (LIFO) approach to exploring
     * states. This means the most recently discovered state is explored first, allowing the algorithm to dive deep
     * into one part of the search space before exploring alternatives. This characteristic is what enables the
     * depth-first exploration pattern.
     *
     * A Set is used to keep track of visited states to prevent the algorithm from cycling indefinitely in case of
     * loops within the search space. By checking if a newly generated state has already been visited, the algorithm
     * ensures it only explores new, unique states, thus preventing infinite loops and reducing redundant computations.
     *
     * @param objective The objective to achieve.
     * @return A SearchResult object that encapsulates whether a solution meeting the objective was found, the solution
     *         state (if found), the total number of states generated during the search, and the maximum depth reached
     *         in the search space. In cases where the search is interrupted (e.g., due to a timeout), the method
     *         captures the current state of the search and returns it, allowing for partial results to be analyzed.
     */
    @Override
    protected SearchResult performSearch(IState initialState, int objective) {
        //in both cases access is O(1)
        Deque<IState> deque = new ArrayDeque<>(); //LIFO exploration
        Set<IState> visited = new HashSet<>(); //to keep track of visited states

        //count of all states that have been generated
        int generatedStates = 0;
        //keeps track of the maximum depth of the search tree
        int maxDepth = 0;

        deque.add(initialState);
        visited.add(initialState);

        while(!deque.isEmpty()) {
            IState currentState = deque.pop();
            maxDepth = Math.max(currentState.getDepth(), maxDepth);

            //interruption check for externally managed timeouts or cancellations.
            if(Thread.currentThread().isInterrupted()) {
                //capture the current search
                captureCurrentState(currentState,generatedStates,maxDepth);
                latch.countDown();
            }

            //check if solution is found
            if(currentState.getObjectiveValue() <= objective) {
                //found a solution meeting the objective.
                return new SearchResult(currentState, true, generatedStates,currentState.getDepth());
            }

            //generate and add to queue all successor states
            List<IState> successors = generateSuccessors(currentState);
            generatedStates += successors.size();
            for(IState successor : successors) {
                if(!visited.contains(successor)) {
                    deque.add(successor);
                    visited.add(successor);
                }
            }
        }

        //search space was fully explored without finding a solution.
        return new SearchResult(null, false, generatedStates,maxDepth);
    }

    @Override
    public String toString() {
        return "Depth-First Search";
    }
}
