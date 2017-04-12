package com.maymeng.read.api;


import com.maymeng.read.bean.BookBean;
import com.maymeng.read.bean.BookDetailBean;
import com.maymeng.read.bean.HotMoviesBean;
import com.maymeng.read.bean.MoviesDetailBean;
import com.maymeng.read.bean.TopMoviesBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Create by  leijiaxq
 * Date       2017/3/2 16:57
 * Describe
 */

public interface DouBanService {

    //   获取正在上映的电影
    @GET("v2/movie/in_theaters")
    Observable<HotMoviesBean> getHotMoviesNet();

    //   获取Top250电影
    @GET("v2/movie/top250")
    Observable<TopMoviesBean> getTop250MoviesNet(@Query("start") int start,@Query("count") int count);

    //   获取即将上映的电影
    @GET("v2/movie/coming_soon")
    Observable<TopMoviesBean> getSoonMoviesNet(@Query("start") int start,@Query("count") int count);

  //   获取电影详情
    @GET("v2/movie/subject/{id}")
    Observable<MoviesDetailBean> getMoviesDetailNet(@Path("id") String id);


  //   根据tag获取书籍
    @GET("v2/book/search")
    Observable<BookBean> getBooksByTag(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    //   获取电影详情
    @GET("/v2/book/{id}")
    Observable<BookDetailBean> getBookDetailNet(@Path("id") String id);

}
