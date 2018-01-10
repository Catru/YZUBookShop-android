package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/1/4.
 */
public class FormedData<T> {

    private boolean success;

    private T data;

    private String error;

    public FormedData(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public FormedData(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "FormedData{" +
                "success=" + success +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }

}
