package com.maymeng.read.bean;

import java.util.List;

/**
 * Created by  leijiaxq
 * Date        2017/4/11 13:17
 * Describe
 */

public class BookDetailBean extends BaseBean {


    /**
     * rating : {"max":10,"numRaters":124271,"average":"9.1","min":0}
     * subtitle :
     * author : ["余华"]
     * pubdate : 1998-5
     * tags : [{"count":37791,"name":"余华","title":"余华"},{"count":24757,"name":"活着","title":"活着"},{"count":18065,"name":"小说","title":"小说"},{"count":14717,"name":"中国文学","title":"中国文学"},{"count":12361,"name":"人生","title":"人生"},{"count":6866,"name":"当代","title":"当代"},{"count":6279,"name":"文学","title":"文学"},{"count":5930,"name":"生活","title":"生活"}]
     * origin_title :
     * image : https://img3.doubanio.com/mpic/s23836852.jpg
     * binding : 平装
     * translator : []
     * catalog :
     * pages : 195
     * images : {"small":"https://img3.doubanio.com/spic/s23836852.jpg","large":"https://img3.doubanio.com/lpic/s23836852.jpg","medium":"https://img3.doubanio.com/mpic/s23836852.jpg"}
     * alt : https://book.douban.com/subject/1082154/
     * id : 1082154
     * publisher : 南海出版公司
     * isbn10 : 7544210960
     * isbn13 : 9787544210966
     * title : 活着
     * url : https://api.douban.com/v2/book/1082154
     * alt_title :
     * author_intro : 余华，浙江海盐人，1960年出生于浙江杭州，后来随父母迁居海盐县。中学毕业后，因父母是医生，余华曾当过牙医，五年后弃医从文，进入县文化馆和嘉兴文联，从此开始文学创作生涯。曾在北京鲁迅文学院与北师大中文系合办的研究生班深造，1984年开始发表小说。余华是中国先锋派小说的代表人物，与叶兆言、苏童等人齐名。作品有短篇小说集《十八岁出门远行》、《世事如烟》，长篇小说《在细雨中呼喊》、《战栗》及《兄弟》。
     * summary : 地主少爷福贵嗜赌成性，终于赌光了家业一贫如洗，穷困之中的福贵因为母亲生病前去求医，没想到半路上被国民党部队抓了壮丁，后被解放军所俘虏，回到家乡他才知道母亲已经去世，妻子家珍含辛茹苦带大了一双儿女，但女儿不幸变成了聋哑人，儿子机灵活泼……
     然而，真正的悲剧从此才开始渐次上演，每读一页，都让我们止不住泪湿双眼，因为生命里难得的温情将被一次次死亡撕扯得粉碎，只剩得老了的福贵伴随着一头老牛在阳光下回忆。
     * series : {"id":"16334","title":"余华作品"}
     * price : 12.00元
     */

    public RatingBean rating;
    public String subtitle;
    public String pubdate;
    public String origin_title;
    public String image;
    public String binding;
    public String catalog;
    public String pages;
    public ImagesBean images;
    public String alt;
    public String id;
    public String publisher;
    public String isbn10;
    public String isbn13;
    public String title;
    public String url;
    public String alt_title;
    public String author_intro;
    public String summary;
    public SeriesBean series;
    public String price;
    public List<String> author;
    public List<TagsBean> tags;
    public List<?> translator;

    public static class RatingBean {
        /**
         * max : 10
         * numRaters : 124271
         * average : 9.1
         * min : 0
         */

        public int max;
        public int numRaters;
        public String average;
        public int min;
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/spic/s23836852.jpg
         * large : https://img3.doubanio.com/lpic/s23836852.jpg
         * medium : https://img3.doubanio.com/mpic/s23836852.jpg
         */

        public String small;
        public String large;
        public String medium;
    }

    public static class SeriesBean {
        /**
         * id : 16334
         * title : 余华作品
         */

        public String id;
        public String title;
    }

    public static class TagsBean {
        /**
         * count : 37791
         * name : 余华
         * title : 余华
         */

        public int count;
        public String name;
        public String title;
    }
}
