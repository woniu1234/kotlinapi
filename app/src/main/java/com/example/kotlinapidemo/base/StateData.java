package com.example.kotlinapidemo.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 带有状态的HttpResult包装类
 */
public class StateData<T> {

    private DataStatus status;

    @Nullable
    private T data;

    private int errorCode;    //从failureMsg拆分出来
    @Nullable
    private String errorMsg;   //从failureMsg拆分出来


    public StateData() {
        this.data = null;
    }

    public StateData<T> loading() {
        this.status = DataStatus.LOADING;
        this.data = null;
        return this;
    }

    public StateData<T> success(@NonNull T data) {
        this.status = DataStatus.SUCCESS;
        this.data = data;
        return this;
    }


    public StateData<T> failure(@NonNull String errorMsg, int errorCode) {
        this.status = DataStatus.ERROR;
        this.data = null;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        return this;
    }

    /**
     * 仅标志成功与否
     */
    public StateData<T> success() {
        this.status = DataStatus.SUCCESS;
        return this;
    }


    public StateData<T> failure() {
        this.status = DataStatus.ERROR;
        return this;
    }


    @NonNull
    public DataStatus getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Nullable
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(@Nullable String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public enum DataStatus {
        SUCCESS,
        ERROR,
        LOADING
    }

}