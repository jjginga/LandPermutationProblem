package com.jjginga.state;

import java.util.List;

/**
 * Represents a state in the search space. Defines the operations necessary for
 * search algorithms to explore the space, including generating successors,
 * evaluating the current state, and accessing state-specific properties such as
 * its objective value and depth.
 */
public interface IState {

    /**
     * Generates and returns a list of successor states following from this state.
     * Successors represent possible next states in the search space.
     *
     * @return A list of IState objects representing successors.
     */
    public List<IState> generateSuccessors();

    /**
     * Evaluates the current state to update its internal values.
     * This method is intended to be called whenever the state changes.
     */
    public void evaluate();

    /**
     * Retrieves the objective value of the state, which is a measure used to
     * determine how close the state is to the goal state.
     *
     * @return The objective value of the state.
     */
    public int getObjectiveValue();

    /**
     * Retrieves the depth of this state in the search space, typically representing
     * the number of steps taken from the initial state to reach this state.
     *
     * @return The depth of the state.
     */
    public int getDepth();

}
