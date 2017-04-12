package com.maymeng.read.bean;

import java.util.List;

/**
 * Created by  leijiaxq
 * Date        2017/4/7 17:31
 * Describe
 */
public class ZhihuDetailBean extends BaseBean{
    /**
     * body : html格式的内容
     * image_source : 《帕特森》
     * title : 谁说普通人的生活就不能精彩有趣呢？
     * image : http://pic4.zhimg.com/e39083107b7324c6dbb725da83b1d7fb.jpg
     * share_url : http://daily.zhihu.com/story/9165434
     * js : []
     * ga_prefix : 012121
     * section : {"thumbnail":"http://pic1.zhimg.com/ffcca2b2853f2af791310e6a6d694e80.jpg","id":28,"name":"放映机"}
     * images : ["http://pic1.zhimg.com/ffcca2b2853f2af791310e6a6d694e80.jpg"]
     * type : 0
     * id : 9165434
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    public String body;
    public String image_source;
    public String title;
    public String image;
    public String share_url;
    public String ga_prefix;
    public SectionBean section;
    public int type;
    public int id;
    public List<?> js;
    public List<String> images;
    public List<String> css;

    public static class SectionBean {
        /**
         * thumbnail : http://pic1.zhimg.com/ffcca2b2853f2af791310e6a6d694e80.jpg
         * id : 28
         * name : 放映机
         */

        public String thumbnail;
        public int id;
        public String name;
    }

//    http://daily.zhihu.com/story/9165434",


}
