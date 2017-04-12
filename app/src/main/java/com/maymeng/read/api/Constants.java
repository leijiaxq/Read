package com.maymeng.read.api;

/**
 * Created by hcc on 2016/11/20 21:32
 * 100332338@qq.com
 * <p>
 * API常量类
 */

public class Constants {

    public static final String BASE_URL_ZHIHU = "http://news-at.zhihu.com/";
    public static final String BASE_URL_DOUBAN = "https://api.douban.com/";
    public static final String BASE_URL = "https://api.douban.com/";

    public static final String IMAGE_MOVIES_URL = "http://ojyz0c8un.bkt.clouddn.com/one_01.png";
    public static final String IMAGE_MOVIES_URL2 = "https://img3.doubanio.com/spic/s1634671.jpg";



    public static final String OK = "ok";


    public static final String FAILURE = "请检查网络设置";

    public static final String ERROR = "数据错误";

    public static final int SIZE = 10;


    public static final int TYPE_ONE   = 1;
    public static final int TYPE_TWO   = 2;
    public static final int TYPE_THREE = 3;

    public static final int TYPE_FOOT = 4;


    /**
     * 用户信息的存键
     */
    //用户ID
    public static final String USERID        = "userID";
    //用户名称
    public static final String USERNAME      = "username";
    //用户昵称
    public static final String NICKNAME      = "nickname";
    //头像url
    public static final String HEADURL       = "headurl";
    //手机密码
    public static final String PASSWORD       = "password";
    //手机号码
    public static final String PHONE         = "phone";
    //注册时间
    public static final String REGISTERDATE  = "registerdate";
    //最后登录时间
    public static final String LASTLOGINDATE = "lastlogindate";
    public static final String ISSTATUS      = "isstatus";
    //第三方登录OpenID
    public static final String OPENID        = "openid";
    public static final String ISID          = "isid";
    //性别
    public static final String SEX           = "sex";
    public static final String REMARK        = "remark";

    // 过渡图的图片链接
//    private static final String TRANSITION_URL_01 = "http://ojyz0c8un.bkt.clouddn.com/b_1.jpg";
    private static final String TRANSITION_URL_02 = "http://ojyz0c8un.bkt.clouddn.com/b_2.jpg";
    private static final String TRANSITION_URL_03 = "http://ojyz0c8un.bkt.clouddn.com/b_3.jpg";
    private static final String TRANSITION_URL_04 = "http://ojyz0c8un.bkt.clouddn.com/b_4.jpg";
    private static final String TRANSITION_URL_05 = "http://ojyz0c8un.bkt.clouddn.com/b_5.jpg";
    private static final String TRANSITION_URL_06 = "http://ojyz0c8un.bkt.clouddn.com/b_6.jpg";
    private static final String TRANSITION_URL_07 = "http://ojyz0c8un.bkt.clouddn.com/b_7.jpg";
    private static final String TRANSITION_URL_08 = "http://ojyz0c8un.bkt.clouddn.com/b_8.jpg";
    private static final String TRANSITION_URL_09 = "http://ojyz0c8un.bkt.clouddn.com/b_9.jpg";
    private static final String TRANSITION_URL_10 = "http://ojyz0c8un.bkt.clouddn.com/b_10.jpg";
    public static final String[] TRANSITION_URLS = new String[]{
           /* TRANSITION_URL_01,*/ TRANSITION_URL_02, TRANSITION_URL_03
            , TRANSITION_URL_04, TRANSITION_URL_05, TRANSITION_URL_06
            , TRANSITION_URL_07, TRANSITION_URL_08, TRANSITION_URL_09
            , TRANSITION_URL_10
    };

}
