package com.jjginga.SearchTechnique;

import com.jjginga.Result.SearchResult;
import com.jjginga.State.IState;

import java.util.concurrent.Callable;

public interface ISearchTechnique extends Callable<SearchResult> {
    public SearchResult search(IState initialState, int maxBorders);
}
