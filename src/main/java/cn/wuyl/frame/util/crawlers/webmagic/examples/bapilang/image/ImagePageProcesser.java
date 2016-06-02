package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang.image;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class ImagePageProcesser implements PageProcessor {
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(5).setSleepTime(2000);
    static String bianhao = "13";
    static int threadNum = 20;
    
    public void process(Page page) {
    	String curUrl = page.getUrl().toString();
    	if(page.getUrl().regex("(/tchy/\\w{1,6}/\\w{1,6}\\.html)").match() 
    			|| page.getUrl().regex("(/rzqhkb/\\w{1,6}\\.html)").match()){//遍历导航及明细链接
    		System.out.println("match:" + curUrl);
        	List<String> urls = page.getHtml().links().regex("(/tchy/\\w{1,6}/\\w{1,6}\\.html)").all();//导航
        	System.out.println("urls~~~"+ urls.size() + "~~~" + urls.toString());
	       	List<String> urls2 = page.getHtml().links().regex("(/rzqhkb/\\w{1,6}\\.html)").all();//明细
	    	System.out.println("urls2~~~"+ urls2.size() + "~~~" + urls2.toString());
        	List<String> urls3 = page.getHtml().links().all();//图片
        	urls3 = page.getHtml().xpath("//div[@class='center margintop border clear main']/div[@class='content']/img/@data-original").all();
        	System.out.println("urls3~~~"+ urls3.size() + "~~~" + urls3.toString());
        	urls.addAll(urls2);
        	urls.addAll(urls3);
        	page.addTargetRequests(urls3);
    	}else{//查看明细
//        	List<String> urls3 = page.getHtml().xpath("//div[@class='center margintop border clear main']/div[@class='content']/img/@data-original").all();
//        	System.out.println("urls3~~~"+ urls3.size() + "~~~" + urls3.toString());
//    		System.out.println("not match:" + curUrl);
            page.putField("title", page.getHtml().xpath("//div[@class='center margintop border clear main']/div[@class='title']/text()"));
            if (page.getResultItems().get("title")==null){
                //skip this page
                page.setSkip(true);
            }
            page.putField("content", page.getHtml().xpath("//div[@class='content']/allText()"));

            String imageStr = page.getRawText();
            String url = page.getRequest().getUrl();
            String filename = url.substring(url.lastIndexOf("/"), url.length());
            System.out.println("@@@@" + curUrl + "\r\n" + url);
            page.putField("url", url);
            page.putField("bianhao", UUID.randomUUID().toString());//bianhao);
            page.putField("filename", filename);//UUID.randomUUID().toString());//bianhao);
            page.putField("imageStr", imageStr);
    	}
    }

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
    	String url = "http://www.bapilang.link/rzqhkb/443411.html";//"http://www.bapilang.link/rzqhkb/432524.html";//"http://www.bapilang.link/tchy/13/1.html";
        Spider.create(new ImagePageProcesser()).addUrl(url).addPipeline(new ConsolePipeline())
		.addPipeline(new ImageToLocalPipeline("E:\\crawler\\data\\webmagic\\image\\"))
//		.setDownloader(new ImageDownloader())
		.thread(5).run();
    }

}
