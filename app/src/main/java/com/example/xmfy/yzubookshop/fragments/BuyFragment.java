package com.example.xmfy.yzubookshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.BookSearchBean;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.module.buy.BaseBuyFragment;
import com.example.xmfy.yzubookshop.module.buy.BookDetailActivity;
import com.example.xmfy.yzubookshop.module.buy.BookSearchHelper;
import com.example.xmfy.yzubookshop.module.buy.BookSuggestion;
import com.example.xmfy.yzubookshop.module.buy.SearchResultsListAdapter;
import com.example.xmfy.yzubookshop.module.buy.searchTab.CartActivity;
import com.example.xmfy.yzubookshop.module.buy.searchTab.ClassifyTab;
import com.example.xmfy.yzubookshop.module.buy.searchTab.SearchConditions;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.CartAsyncTask;
import com.example.xmfy.yzubookshop.net.CollectionAsyncTask;
import com.example.xmfy.yzubookshop.utils.CommonUtils;
import com.example.xmfy.yzubookshop.utils.LoginUtils;
import com.example.xmfy.yzubookshop.widget.RichText;
import com.google.gson.Gson;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeFloatingActionButton;

/**
 * Created by xmfy on 2018/1/3.
 */
public class BuyFragment extends BaseBuyFragment {

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 350;

    private static final int SEARCH_SIZE = 5;

    private int c1 = 0;
    private int c2 = 0;

    private View view;
    private Context context;
    private SharedPreferences preferences;

    private FloatingSearchView mSearchView;
    private RecyclerView mSearchResultsList;
    private SearchResultsListAdapter mSearchResultsAdapter;
    private BGABadgeFloatingActionButton fab_cart;

    private RichText rt_buy_sort;
    private RichText rt_buy_category;
    private RichText rt_buy_price;

    private SearchConditions searchConditions;

    private String mLastQuery = "";

    private Drawable collected;
    private Drawable uncollected;

    public BuyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        context = getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        initWidgets();
        setUpActionBtn();
        setupFloatingSearch();
        setupResultsList();
        setupDrawer();
        initDataList();
    }

    private void bindView() {
        mSearchView = view.findViewById(R.id.floating_search_view);
        mSearchResultsList = view.findViewById(R.id.search_results_list);
        rt_buy_sort = view.findViewById(R.id.rt_buy_sort);
        rt_buy_category = view.findViewById(R.id.rt_buy_category);
        rt_buy_price = view.findViewById(R.id.rt_buy_price);
        fab_cart = view.findViewById(R.id.fab_cart);
    }

    private void initWidgets() {
        //初始化排序列表
        ClassifyTab tab = new ClassifyTab(getContext(), rt_buy_sort, rt_buy_category, rt_buy_price);
        tab.load();
        searchConditions = new SearchConditions();
        searchConditions.setAccount(LoginUtils.getAccount(getContext().getSharedPreferences("User", Context.MODE_PRIVATE)));
        tab.setSearchConditions(searchConditions);

        collected = context.getResources().getDrawable(R.mipmap.icon_collected, context.getTheme());
        int w = CommonUtils.dip2px(context, 20);
        collected.setBounds(0, 0, w, w);
        uncollected = context.getResources().getDrawable(R.mipmap.icon_uncollected, context.getTheme());
        uncollected.setBounds(0, 0, w, w);

        preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);

        if (LoginUtils.isLogined(preferences)){
            CartAsyncTask<Integer> task = new CartAsyncTask<>(CartAsyncTask.TYPE_QUERY_COUNT, new AsyncResponse<Integer>() {
                @Override
                public void onDataReceivedSuccess(FormedData<Integer> formedData) {
                    fab_cart.showTextBadge(formedData.getData()+"");
                }

                @Override
                public void onDataReceivedFailed() {

                }
            });
            task.execute(LoginUtils.getAccount(preferences));
        }
    }

    private void setUpActionBtn(){
        fab_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginUtils.isLogined(preferences)){
                    Toast.makeText(context, "请您登录后再使用购物车!", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(context, CartActivity.class));
                }
            }
        });
    }

    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    BookSearchHelper.findSuggestions(getActivity(), newQuery, SEARCH_SIZE, c1, c2,
                            FIND_SUGGESTION_SIMULATED_DELAY, new BookSearchHelper.OnFindSuggestionsListener() {
                                @Override
                                public void onResults(List<BookSuggestion> results) {
                                    mSearchView.swapSuggestions(results);
                                    mSearchView.hideProgress();
                                }
                            });
                }
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                BookSuggestion bookSuggestion = (BookSuggestion) searchSuggestion;
                BookSearchHelper.findBooks(getActivity(), bookSuggestion.getType(), bookSuggestion.getBody(),
                        new BookSearchHelper.OnFindColorsListener() {
                            @Override
                            public void onResults(List<BookSearchBean> results) {
                                mSearchResultsAdapter.swapData(results);
                            }
                        });
                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                BookSearchHelper.findBooks(getActivity(), searchConditions,
                        new BookSearchHelper.OnFindColorsListener() {
                            @Override
                            public void onResults(List<BookSearchBean> results) {
                                mSearchResultsAdapter.swapData(results);
                            }
                        });
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                mSearchView.swapSuggestions(BookSearchHelper.getHistory(getActivity(), 3));
            }

            @Override
            public void onFocusCleared() {
                mSearchView.setSearchBarTitle(mLastQuery);
            }
        });

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_search) {
                    String query = mSearchView.getQuery();
                    BookSearchHelper.findBooks(getActivity(), searchConditions,
                            new BookSearchHelper.OnFindColorsListener() {
                                @Override
                                public void onResults(List<BookSearchBean> results) {
                                    mSearchResultsAdapter.swapData(results);
                                }
                            });
                    mLastQuery = query;
                    searchConditions.setValue(query);
                }
            }
        });

        mSearchView.setOnSuggestionsListHeightChanged(new FloatingSearchView.OnSuggestionsListHeightChanged() {
            @Override
            public void onSuggestionsListHeightChanged(float newHeight) {
                mSearchResultsList.setTranslationY(newHeight);
            }
        });

    }

    private void setupResultsList() {
        mSearchResultsAdapter = new SearchResultsListAdapter(getContext());
        mSearchResultsList.setAdapter(mSearchResultsAdapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchResultsAdapter.setItemsOnClickListener(new SearchResultsListAdapter.OnItemClickListener() {
            @Override
            public void onClick(BookSearchBean book) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("book", new Gson().toJson(book));
                startActivity(intent);
            }
        });
        mSearchResultsAdapter.setCollectsClickListener(new SearchResultsListAdapter.OnCollectsClickListener() {
            @Override
            public void onClick(RichText rt, BookSearchBean book) {
                String account = LoginUtils.getAccount(preferences);
                if (!account.equals("")) {
                    boolean isCollected = book.getIsCollected() != 0;
                    int collectedNum = Integer.parseInt(rt.getText().toString());
                    rt.setCompoundDrawables(isCollected ? uncollected : collected, null, null, null);
                    rt.setText((isCollected ? collectedNum - 1 : collectedNum + 1) + "");
                    book.setIsCollected(isCollected ? 0 : 1);
                    CollectionAsyncTask<Integer> task = new CollectionAsyncTask<>();
                    task.setType(CollectionAsyncTask.TYPE_CHANGE);
                    task.execute(account, book.getId() + "", isCollected ? "delete" : "add");
                } else {
                    Toast.makeText(context, "请先登录后再收藏!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initDataList(){
        BookSearchHelper.findBooks(getActivity(), searchConditions,
                new BookSearchHelper.OnFindColorsListener() {
                    @Override
                    public void onResults(List<BookSearchBean> results) {
                        mSearchResultsAdapter.swapData(results);
                    }
                });
    }

    @Override
    public boolean onActivityBackPress() {
        return mSearchView.setSearchFocused(false);
    }

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }
}
