package cn.wuyl.frame.util.crawlers.webmagic.examples.baidubaike;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class BaiduBaikePageProcessor implements PageProcessor{
    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    public void process(Page page) {
        page.putField("name", page.getHtml().css("h1.title div.lemmaTitleH1","text").toString());
        page.putField("description", page.getHtml().xpath("//div[@id='lemmaContent-0']//div[@class='para']/allText()"));
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(2);
        String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
        ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate, "姘村姏鍙戠數"));
        System.out.println(resultItems);

        //multidownload
        List<String> list = new ArrayList<String>();
        list.add(String.format(urlTemplate,"椋庡姏鍙戠數"));
        list.add(String.format(urlTemplate,"澶槼鑳�"));
        list.add(String.format(urlTemplate,"鍦扮儹鍙戠數"));
        list.add(String.format(urlTemplate,"鍦扮儹鍙戠數"));
        List<ResultItems> resultItemses = spider.<ResultItems>getAll(list);
        for (ResultItems resultItemse : resultItemses) {
            System.out.println(resultItemse.getAll());
        }
        spider.close();
    }
}
