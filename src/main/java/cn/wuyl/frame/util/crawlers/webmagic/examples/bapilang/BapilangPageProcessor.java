package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class BapilangPageProcessor implements PageProcessor{
    private Site site = Site.me().setDomain("www.bapilang.link")//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(100);//.setUseGzip(true);
    
    private Map<String,String> naviMap = new HashMap<String,String>();

    public void process(Page page) {
        //page.addTargetRequests(page.getHtml().links().regex("(http://www\\.bapilang\\.link/tchy/12/\\w{1,3}\\.html)").all());
    	String curUrl = page.getUrl().toString();
//    	Selectable naviStr = page.getHtml().xpath("//div[@class='center margintop border clear menu']/a");
    	if(page.getUrl().regex("(/tchy/\\w{1,6}/\\w{1,6}\\.html)").match()
    			||page.getUrl().regex("(/rzqhkb/\\w{1,6}\\.html)").match()){//遍历导航及明细链接
    		System.out.println("match:" + curUrl);
        	List<String> urls = page.getHtml().links().regex("(/tchy/\\w{1,6}/\\w{1,6}\\.html)").all();//导航
        	List<String> urls2 = page.getHtml().links().regex("(/rzqhkb/\\w{1,6}\\.html)").all();//明细
        	List<String> urls3 = page.getHtml().links().regex(".*(\\.(bmp|gif|jpe?g|png|tiff?))$").all();//图片
        	urls.addAll(urls2);
        	urls.addAll(urls3);
        	page.addTargetRequests(urls);
    	}else{//查看明细
    		System.out.println("not match:" + curUrl);
            page.putField("title", page.getHtml().xpath("//div[@class='center margintop border clear main']/div[@class='title']/text()"));
            if (page.getResultItems().get("title")==null){
                //skip this page
                page.setSkip(true);
            }
            page.putField("content", page.getHtml().xpath("//div[@class='content']/allText()"));
    	}
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
//    	String urlTemplate = "http://www.bapilang.link/tchy/14/1.html";
        Spider.create(new BapilangPageProcessor())
//        .addUrl("http://www.bapilang.link/tchy/14/1.html","http://www.bapilang.link/tchy/15/1.html"
//        		,"http://www.bapilang.link/tchy/16/1.html","http://www.bapilang.link/tchy/17/1.html"
//        		,"http://www.bapilang.link/tchy/18/1.html","http://www.bapilang.link/tchy/19/1.html"
//        		,"http://www.bapilang.link/tchy/20/1.html","http://www.bapilang.link/tchy/21/1.html")
        .addUrl("http://www.bapilang.link/tchy/22/1.html")
        .addPipeline(new ConsolePipeline()).addPipeline(new FilePipelineEXT("E:\\crawler\\data\\webmagic\\")).thread(5).run();
//        Spider.create(new BapilangPageProcessor()).addUrl(urlTemplate).addPipeline(new ConsolePipeline()).thread(5).run();
    }
}
