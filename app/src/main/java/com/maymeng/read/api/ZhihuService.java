package com.maymeng.read.api;


import com.maymeng.read.bean.ZhihuBean;
import com.maymeng.read.bean.ZhihuDetailBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Create by  leijiaxq
 * Date       2017/3/2 16:57
 * Describe
 */

public interface ZhihuService {

    //    获取最新的消息
    @GET("api/4/news/latest")
    Observable<ZhihuBean> getNewZhihuBeanNet();

    //    获取过往的消息
    @GET("api/4/news/before/{time}")
    Observable<ZhihuBean> getOldZhihuBeanNet(@Path("time") String time);

    //    获取消息详情数据
    @GET("api/4/news/{id}")
    Observable<ZhihuDetailBean> getZhihuDetailNet(@Path("id") int id);


}
