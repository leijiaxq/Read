package com.maymeng.read.bean;

import java.util.List;

/**
 * Created by  leijiaxq
 * Date        2017/4/7 15:51
 * Describe
 */

public class ZhihuBean extends BaseBean{


    /**
     * date : 20170407
     * stories : [{"title":"科幻电影里，这些设计都已经变成了常客","ga_prefix":"040715","images":["https://pic3.zhimg.com/v2-33ffca2af426f0703f44a440f09d30be.jpg"],"multipic":true,"type":0,"id":9342833},{"images":["https://pic3.zhimg.com/v2-4b415157778cbb44e8f26e8716b391fe.jpg"],"type":0,"id":9342863,"ga_prefix":"040715","title":"媲美前作，《荒野之息》是人类能做出来的最好的游戏之一"},{"title":"意面中间的小白芯，就像是「西瓜最中间那一口」","ga_prefix":"040713","images":["https://pic3.zhimg.com/v2-42e7c28a3673a2e733b62bca24186d56.jpg"],"multipic":true,"type":0,"id":9337803},{"images":["https://pic2.zhimg.com/v2-332b60c1ec2ecbfd6b68bb84f68bfc2d.jpg"],"type":0,"id":9342398,"ga_prefix":"040712","title":"大误 · 丧心病狂，唐僧肉到底该怎么吃？"},{"images":["https://pic2.zhimg.com/v2-96b62d39ab1c644b97a4b34a1d1be8f9.jpg"],"type":0,"id":9340188,"ga_prefix":"040711","title":"用科学来解释「一夜白头」，其实是这么回事"},{"images":["https://pic1.zhimg.com/v2-70052b77d28b4406898db59a69105964.jpg"],"type":0,"id":9341354,"ga_prefix":"040710","title":"什么样的老师才是好老师？"},{"images":["https://pic2.zhimg.com/v2-c66392e788b4fb101229cf3b2abb6071.jpg"],"type":0,"id":9341571,"ga_prefix":"040709","title":"买房合同都签了，房主又要涨价，你有 3 种办法"},{"images":["https://pic3.zhimg.com/v2-f273c04e244b43a592b632596bffb9fa.jpg"],"type":0,"id":9341357,"ga_prefix":"040708","title":"技术进步真的会减少岗位数量吗？"},{"images":["https://pic3.zhimg.com/v2-1ab80f77b7636ad5622628c50628381a.jpg"],"type":0,"id":9340511,"ga_prefix":"040707","title":"有了这份手册，完全不会日语也能在日本点菜"},{"images":["https://pic1.zhimg.com/v2-0b9a275633035bfbd9a8d8bf7fdc7864.jpg"],"type":0,"id":9340652,"ga_prefix":"040707","title":"特斯拉和谷歌搞自动驾驶，传统车企当然不会坐以待毙"},{"title":"《三体 3》入围的雨果奖，跟很多熟悉的电影美剧都有关系","ga_prefix":"040707","images":["https://pic4.zhimg.com/v2-4ab5de5c76cef87bb2dee4ce2a32aa33.jpg"],"multipic":true,"type":0,"id":9340644},{"images":["https://pic3.zhimg.com/v2-5c1d6baee92afd490672df88be385fd6.jpg"],"type":0,"id":9339802,"ga_prefix":"040706","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic3.zhimg.com/v2-c9d320564207ff5df10e9e944cff00ce.jpg","type":0,"id":9342863,"ga_prefix":"040715","title":"媲美前作，《荒野之息》是人类能做出来的最好的游戏之一"},{"image":"https://pic3.zhimg.com/v2-43172adf4d6a931ec0cf0e045c95bb0e.jpg","type":0,"id":9341354,"ga_prefix":"040710","title":"什么样的老师才是好老师？"},{"image":"https://pic3.zhimg.com/v2-048b0f11937d50d380cf4e7263120a8a.jpg","type":0,"id":9340511,"ga_prefix":"040707","title":"有了这份手册，完全不会日语也能在日本点菜"},{"image":"https://pic2.zhimg.com/v2-8a77b3b20b5e92c574821b32dab6f7e5.jpg","type":0,"id":9340644,"ga_prefix":"040707","title":"《三体 3》入围的雨果奖，跟很多熟悉的电影美剧都有关系"},{"image":"https://pic1.zhimg.com/v2-ce45d7f0bf5139e33a1c2198146d2984.jpg","type":0,"id":9340102,"ga_prefix":"040614","title":"人工智能要和人类打德州扑克，这场比赛注定很精彩"}]
     */

    public String date;
    public List<StoriesBean> stories;
    public List<TopStoriesBean> top_stories;

    public static class StoriesBean {
        /**
         * title : 科幻电影里，这些设计都已经变成了常客
         * ga_prefix : 040715
         * images : ["https://pic3.zhimg.com/v2-33ffca2af426f0703f44a440f09d30be.jpg"]
         * multipic : true
         * type : 0
         * id : 9342833
         */

        public String title;
        public String ga_prefix;
        public boolean multipic;
        public int type;
        public int id;
        public List<String> images;
    }

    public static class TopStoriesBean {
        /**
         * image : https://pic3.zhimg.com/v2-c9d320564207ff5df10e9e944cff00ce.jpg
         * type : 0
         * id : 9342863
         * ga_prefix : 040715
         * title : 媲美前作，《荒野之息》是人类能做出来的最好的游戏之一
         */

        public String image;
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
    }
}
