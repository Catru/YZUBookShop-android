package com.example.xmfy.yzubookshop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.module.buy.BaseBuyFragment;

/**
 * Created by xmfy on 2018/1/3.
 */
public class BuyFragment extends BaseBuyFragment {

    private FloatingSearchView mSearchView;

    private String mLastQuery = "";

    public BuyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = view.findViewById(R.id.floating_search_view);
        setupFloatingSearch();
    }

    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")){
                    mSearchView.clearSuggestions();
                }else {
                    mSearchView.showProgress();
                    
                }
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
            }
        });
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }
}
