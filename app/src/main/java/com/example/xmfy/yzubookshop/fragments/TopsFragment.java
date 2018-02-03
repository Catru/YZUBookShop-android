package com.example.xmfy.yzubookshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.BookSearchBean;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.module.buy.BookDetailActivity;
import com.example.xmfy.yzubookshop.module.buy.SearchResultsListAdapter;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.BookSearchAsyncTask;
import com.example.xmfy.yzubookshop.net.CollectionAsyncTask;
import com.example.xmfy.yzubookshop.utils.CommonUtils;
import com.example.xmfy.yzubookshop.utils.LoginUtils;
import com.example.xmfy.yzubookshop.widget.RichText;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by xmfy on 2018/1/3.
 */
public class TopsFragment extends Fragment {

    private View view;

    private Context context;

    private RecyclerView rc_tops;

    private SearchResultsListAdapter adapter;

    private SharedPreferences preferences;

    private Button btn_refresh;
    private TextView tv_refresh;

    private Drawable collected;
    private Drawable uncollected;

    public TopsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tops, container, false);
        context = getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        loadData();
        initClickEvents();
    }

    private void initClickEvents() {

        RefreshClickListener listener = new RefreshClickListener();
        btn_refresh.setOnClickListener(listener);
        tv_refresh.setOnClickListener(listener);

        adapter.setItemsOnClickListener(new SearchResultsListAdapter.OnItemClickListener() {
            @Override
            public void onClick(BookSearchBean book) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("book", new Gson().toJson(book));
                startActivity(intent);
            }
        });

        adapter.setCollectsClickListener(new SearchResultsListAdapter.OnCollectsClickListener() {
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

    private void loadData() {
        preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        int w = CommonUtils.dip2px(context, 20);
        collected = context.getResources().getDrawable(R.mipmap.icon_collected, context.getTheme());
        collected.setBounds(0, 0, w, w);
        uncollected = context.getResources().getDrawable(R.mipmap.icon_uncollected, context.getTheme());
        uncollected.setBounds(0, 0, w, w);

        adapter = new SearchResultsListAdapter(getContext());
        rc_tops.setLayoutManager(new LinearLayoutManager(context));
        rc_tops.setAdapter(adapter);

        loadBooks();
    }

    private void bindView() {
        rc_tops = view.findViewById(R.id.rc_tops);
        btn_refresh = view.findViewById(R.id.toolbar_right_btn);
        tv_refresh = view.findViewById(R.id.toolbar_right_tv);
    }

    private void loadBooks(){
        BookSearchAsyncTask task = new BookSearchAsyncTask(BookSearchAsyncTask.QUERY_HOT);
        task.setAsyncResponse(new AsyncResponse<List<BookSearchBean>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<BookSearchBean>> formedData) {
                if (formedData.isSuccess())
                    adapter.swapData(formedData.getData());
                else
                    Toast.makeText(context, formedData.getError(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(context, "网络连接异常,请检查相关设置!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(20 + "", LoginUtils.getAccount(preferences));
    }

    private class RefreshClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            loadBooks();
        }
    }

}
