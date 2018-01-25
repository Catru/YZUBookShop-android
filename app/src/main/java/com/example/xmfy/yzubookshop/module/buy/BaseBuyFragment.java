package com.example.xmfy.yzubookshop.module.buy;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;

/**
 * Created by ari on 8/16/16.
 */
public abstract class BaseBuyFragment extends Fragment {


    private BaseFragmentCallbacks mCallbacks;

    public interface BaseFragmentCallbacks{

        void onAttachSearchViewToDrawer(FloatingSearchView searchView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseFragmentCallbacks) {
            mCallbacks = (BaseFragmentCallbacks) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    protected void attachSearchViewActivityDrawer(FloatingSearchView searchView){
        if(mCallbacks != null){
            mCallbacks.onAttachSearchViewToDrawer(searchView);
        }
    }

    public abstract boolean onActivityBackPress();
}
