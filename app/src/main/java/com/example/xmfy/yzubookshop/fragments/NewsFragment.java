package com.example.xmfy.yzubookshop.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.News;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.CustomedAsync;
import com.example.xmfy.yzubookshop.model.Carousel;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.DownloadUtils;
import com.example.xmfy.yzubookshop.module.banner.CarouselImageLoader;
import com.example.xmfy.yzubookshop.module.news.NewsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xmfy on 2018/1/3.
 */
public class NewsFragment extends Fragment {
    private View view;
    private Banner banner;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private int BANNER_SIZE = 5;
    private int NEWS_SIZE = 10;
    private String STORAGE_FOLDER = "banner";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        banner.startAutoPlay();
        super.onResume();
    }

    @Override
    public void onPause() {
        banner.stopAutoPlay();
        super.onPause();
    }

    private void initView() {
        initBanner();
        initNewsList();
    }

    private void initBanner() {
        //创建轮播图缓存文件夹
        File banner_folder = new File(getContext().getFilesDir(), STORAGE_FOLDER);
        if (!banner_folder.exists())
            banner_folder.mkdirs();
        //初始化轮播图
        banner = view.findViewById(R.id.CarouselBanner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new CarouselImageLoader());
        //okhttp访问后台得到资源list
        CustomedAsync customedAsync = new CustomedAsync();
        customedAsync.execute(AppConstants.BASE_ADDRESS + "/banner?size=" + BANNER_SIZE);
        customedAsync.setOnAsyncResponse(new AsyncResponse() {
            @Override
            public void onDataReceivedSuccess(FormedData formedData) {
                List<Carousel> carouselList = (List<Carousel>) formedData.getData();
                //到底轮播图资源list加载完成
                //开始加载资源
                loadResources(carouselList);
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(getContext(),"轮播图片加载失败", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * 加载轮播图资源
     * @param list 从服务器得到轮播图及标题的信息
     */
    private void loadResources(List<Carousel> list) {
        List<File> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        File image;
        for (Carousel c : list) {
            titles.add(c.getTitle());
            if (images.size() >= BANNER_SIZE)
                break;
            String target = getContext().getFilesDir() + File.separator + STORAGE_FOLDER + File.separator + c.getId() + ".jpg";
            image = new File(target);
            if (image.exists()) {
                images.add(image);
            } else {
                String url = AppConstants.BASE_ADDRESS + c.getPath();
                File file = DownloadUtils.downloadFile(url, target);
                images.add(file);
            }
        }
        banner.setImages(images);
        banner.setBannerTitles(titles);
        banner.setDelayTime(2000);
        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.start();
    }

    private void initNewsList() {
        new NewsAsyncTask().execute(AppConstants.NEWS_ADDRESS + "?size=" + NEWS_SIZE);
    }

    private String getNewsList(String url){
        String data = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            data = response.body().string();
        }catch (IOException e){
            Log.e("News", "获取新闻列表失败");
        }
        return data;
    }


    class NewsAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            return getNewsList(strings[0]);
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            Gson gson = new Gson();
            FormedData<List<News>> formedData = gson.fromJson(data, new TypeToken<FormedData<List<News>>>(){}.getType());
            mRecyclerView = view.findViewById(R.id.view_newslist);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_rc_divider));
            mRecyclerView.addItemDecoration(divider);
            mAdapter = new NewsAdapter(getActivity(), formedData.getData(), mRecyclerView);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
