package com.jjginga.searchtechnique;

import com.jjginga.result.SearchResult;
import com.jjginga.state.IState;

import java.util.concurrent.Callable;

/**
 * Defines the interface for search techniques, extending Callable to allow searches to be executed in threads.
 * This interface is responsible for initiating a search with a given initial state and objective.
 */
public interface ISearchTechnique extends Callable<SearchResult> {

    /**
     * Initiates the search from an initial state towards achieving a given objective.
     *
     * @param initialState The starting state of the search.
     * @param objective The objective to achieve.
     * @return SearchResult encapsulating the outcome of the search.
     */
    SearchResult search(IState initialState, int objective);
}
