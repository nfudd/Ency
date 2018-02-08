package com.xxx.ency.presenter;

import android.content.Context;
import android.widget.Toast;

import com.xxx.ency.base.BaseSubscriber;
import com.xxx.ency.base.RxPresenter;
import com.xxx.ency.contract.EyepetizerContract;
import com.xxx.ency.model.bean.DailyVideoBean;
import com.xxx.ency.model.http.EyepetizerApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2018/2/7.
 */

public class EyepetizerPresenter extends RxPresenter<EyepetizerContract.View> implements EyepetizerContract.Presenter {

    private EyepetizerApi eyepetizerApi;

    private Context context;

    @Inject
    public EyepetizerPresenter(EyepetizerApi eyepetizerApi, Context context) {
        this.eyepetizerApi = eyepetizerApi;
        this.context = context;
    }


    @Override
    public void getDailyVideo(int page, String udid) {
        addSubscribe(eyepetizerApi.getDailyVideo(page, udid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<DailyVideoBean>(context, null) {
                    @Override
                    public void onNext(DailyVideoBean dailyVideoBean) {
                        if (dailyVideoBean.isAdExist()) {
                            mView.showDailyVideoData(dailyVideoBean);
                        } else {
                            mView.failGetData();
                        }
                    }
                }));
    }
}