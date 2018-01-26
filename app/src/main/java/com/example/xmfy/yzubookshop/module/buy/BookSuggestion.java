package com.example.xmfy.yzubookshop.module.buy;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by xmfy on 2018/1/26.
 */
public class BookSuggestion implements SearchSuggestion {

    private int id;
    private String name;
    private boolean isHistory = false;

    public BookSuggestion(String suggestion){
        this.name = suggestion.toLowerCase();
    }

    public BookSuggestion(Parcel parcel){
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.isHistory = parcel.readInt() != 0;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    @Override
    public String getBody() {
        return name;
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
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(isHistory? 1 : 0);
    }
}
