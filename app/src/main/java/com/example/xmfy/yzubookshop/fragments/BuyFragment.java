package com.example.xmfy.yzubookshop.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Book;
import com.example.xmfy.yzubookshop.module.buy.BaseBuyFragment;
import com.example.xmfy.yzubookshop.module.buy.BookSearchHelper;
import com.example.xmfy.yzubookshop.module.buy.BookSuggestion;
import com.example.xmfy.yzubookshop.module.buy.SearchResultsListAdapter;
import com.example.xmfy.yzubookshop.module.buy.sort.SortAdapter;
import com.example.xmfy.yzubookshop.module.buy.sort.SortBean;
import com.example.xmfy.yzubookshop.widget.RichText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xmfy on 2018/1/3.
 */
public class BuyFragment extends BaseBuyFragment {

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 350;

    private static final int SEARCH_SIZE = 5;

    private String[] sort_string = {"默认排序", "价格优先", "浏览量优先", "收藏量优先", "综合排序"};

    private SortAdapter sortAdapter;

    private List<SortBean> sortList = new ArrayList<>(Arrays.asList(new SortBean("默认排序", true),
            new SortBean("价格优先", false), new SortBean("浏览量优先", false), new SortBean("收藏量优先", false), new SortBean("综合排序", false)));

    private int c1 = 0;
    private int c2 = 0;

    private View view;

    private FloatingSearchView mSearchView;
    private RecyclerView mSearchResultsList;
    private SearchResultsListAdapter mSearchResultsAdapter;

    private RichText rt_buy_sort;
    private RichText rt_buy_category;
    private RichText rt_buy_price;

    private PopupWindow window;

    private String mLastQuery = "";

    public BuyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        initWidgets();
        setupFloatingSearch();
        setupResultsList();
        setupDrawer();
    }

    private void bindView() {
        mSearchView = view.findViewById(R.id.floating_search_view);
        mSearchResultsList = view.findViewById(R.id.search_results_list);
        rt_buy_sort = view.findViewById(R.id.rt_buy_sort);
        rt_buy_category = view.findViewById(R.id.rt_buy_category);
        rt_buy_price = view.findViewById(R.id.rt_buy_price);
    }

    private void initWidgets() {
        //初始化排序列表
        sortAdapter = new SortAdapter(getContext(), sortList);
        View popupView_sort = getActivity().getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
        ListView lsvMore = popupView_sort.findViewById(R.id.lsvMore);
        lsvMore.setAdapter(sortAdapter);
        lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sortList.get(i).getChecked()) {
                    for (int k = 0; k < sortList.size(); k++)
                        sortList.get(k).setChecked(i==k);
                    sortAdapter.update(sortList);
                }
            }
        });
        window = new PopupWindow(popupView_sort, 400, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setAnimationStyle(R.style.popup_window_anim);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        rt_buy_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (window.isShowing()){
                    window.dismiss();
                } else{
                    window.showAsDropDown(rt_buy_sort, 0, 50);
                    rt_buy_sort.setTextColor(getContext().getResources().getColor(R.color.menubar_active, getContext().getTheme()));
                    Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_arrow_orange, getContext().getTheme());
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rt_buy_sort.setCompoundDrawables(null, null, drawable, null);
                }
            }
        });
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rt_buy_sort.setTextColor(getContext().getResources().getColor(R.color.menubar_default, getContext().getTheme()));
                Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_arrow_black, getContext().getTheme());
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                Log.e("wh", drawable.getMinimumWidth()+ "  :  "+drawable.getMinimumHeight());
                rt_buy_sort.setCompoundDrawables(null, null, drawable, null);
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
                            public void onResults(List<Book> results) {
                                mSearchResultsAdapter.swapData(results);
                            }
                        });
                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                BookSearchHelper.findBooks(getActivity(), "", query,
                        new BookSearchHelper.OnFindColorsListener() {
                            @Override
                            public void onResults(List<Book> results) {
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
                    if (!query.equals(mLastQuery)) {
                        BookSearchHelper.findBooks(getActivity(), "", query,
                                new BookSearchHelper.OnFindColorsListener() {
                                    @Override
                                    public void onResults(List<Book> results) {
                                        mSearchResultsAdapter.swapData(results);
                                    }
                                });
                        mLastQuery = query;
                    }
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
            public void onClick(Book book) {
                Log.e("buy", book.toString());
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
