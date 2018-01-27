package com.example.xmfy.yzubookshop.module.buy;

import android.os.Bundle;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by xmfy on 2018/1/26.
 */
public class BookSuggestion implements SearchSuggestion {

    private String type;
    private String value;
    private boolean isHistory = false;

    public BookSuggestion(String type, String value){
        this.type = type;
        this.value = value.toLowerCase();
    }

    public BookSuggestion(String type, String value, boolean isHistory) {
        this.type = type;
        this.value = value;
        this.isHistory = isHistory;
    }

    public BookSuggestion(Parcel parcel){
        Bundle bundle = parcel.readBundle(getClass().getClassLoader());
        this.type = bundle.getString("type");
        this.value = bundle.getString("value");
        this.isHistory = bundle.getBoolean("isHistory");
//        this.isHistory = parcel.readInt() != 0;
    }

    public String getType() {
        return type;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    @Override
    public String getBody() {
        return value;
    }

    public static final Creator<BookSuggestion> CREATOR = new Creator<BookSuggestion>() {
        @Override
        public BookSuggestion createFromParcel(Parcel parcel) {
            return new BookSuggestion(parcel);
        }

        @Override
        public BookSuggestion[] newArray(int i) {
            return new BookSuggestion[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("value", value);
        bundle.putBoolean("isHistory", isHistory);
    }

    @Override
    public String toString() {
        return "BookSuggestion{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", isHistory=" + isHistory +
                '}';
    }
}
