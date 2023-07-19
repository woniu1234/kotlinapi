package com.example.kotlinapidemo.base;

import androidx.lifecycle.MutableLiveData;

/**
 * 含有数据加载状态的LiveData 封装处理，原生的LiveData 没有状态信息
 */
public class StateLiveData<T> extends MutableLiveData<StateData<T>> {

    /**
     * 标示数据正在加载中
     */
    public void postLoading() {
        postValue(new StateData<T>().loading());
    }


    /**
     * 标示数据发送异常
     */
    public void postFailure(String errorMsg, int errorCode) {
        postValue(new StateData<T>().failure(errorMsg, errorCode));
    }


    /**
     * 标示成功的获取了数据
     */
    public void postSuccess(T data) {
        postValue(new StateData<T>().success(data));
    }

    /**
     * 仅标记成功
     */
    public void postSuccess() {
        postValue(new StateData<T>().success());
    }

    /**
     * 仅标记失败
     */
    public void postFailure() {
        postValue(new StateData<T>().failure());
    }


}