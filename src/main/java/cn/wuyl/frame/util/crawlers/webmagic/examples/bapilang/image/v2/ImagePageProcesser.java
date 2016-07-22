package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang.image.v2;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wuyl.frame.util.crawlers.webmagic.downloader.HttpClientDownloader;
import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import cn.wuyl.frame.util.crawlers.webmagic.processor.abstractPageProcesser;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class ImagePageProcesser extends abstractPageProcesser implements PageProcessor {
    protected ILogger logger = new Logger(getClass());

    private static String count = "13";
    private static String domain = "http://www.bapilang.link";
    private static String fileDirectoryPath = "E:\\crawler\\data\\webmagic\\image\\";
    private static String startUrl = "http://www.bapilang.link/tchy/"+count+"/1.html";//"http://www.bapilang.link/rzqhkb/443411.html";//"http://www.bapilang.link/rzqhkb/432524.html";//"http://www.bapilang.link/rzqhkb/431074.html";//
	private static String chartSet = "utf-8";
	private static int threadNum = 3;
	private static int retryTimes = 3;
	private static int sleepTime = 1000;
	
    
	private Site site = Site.me().setDomain(domain).setCharset(chartSet).setRetryTimes(retryTimes).setSleepTime(sleepTime);

    private Map<String,String> url2TitleMap = new HashMap<String,String>();//一级导航及二级明细标题
    private Map<String,String> url2UrlMap = new HashMap<String,String>();//二级明细url对应一级导航
    
    @Override
    public void process(Page page) {
    	String currentUrl = page.getUrl().toString();
		logger.debug("currentUrl:" + currentUrl);
		String naviRegex = "(/tchy/"+count+"/\\w{1,6}\\.html)";
		String detailRegex = "(/rzqhkb/\\w{1,6}\\.html)";
		
    	if(page.getUrl().regex(naviRegex).match()){//遍历导航及明细链接
    		logger.log("["+currentUrl + "] is navi url match [" + naviRegex + "]");
        	List<String> naviUrls = page.getHtml().links().regex(naviRegex).all();//一级导航
        	logger.debug("discover navi urls~~~"+ naviUrls.size() + "~~~" + naviUrls.toString());
	       	List<String> detailUrls = page.getHtml().links().regex(detailRegex).all();//二级明细
	    	logger.debug("discover detail urls~~~"+ detailUrls.size() + "~~~" + detailUrls.toString());
	       	naviUrls.addAll(detailUrls);
        	page.addTargetRequests(naviUrls);//添加到爬取链接
        	//---------------------------------------------------------------------------------
        	for(String url:detailUrls){
        		url2UrlMap.put(url.indexOf("http")>0?url:domain+url, currentUrl);
        	}
        	//---------------------------------------------------------------------------------
        	//记录导航及明细页面标题
        	String title = page.getHtml().xpath("//div[@class='center margintop border clear main']/div[@class='title']/text()").toString();
        	if(title!=null && !title.equals("")){
        		url2TitleMap.put(currentUrl, title);
        	}
    	}else if(page.getUrl().regex(detailRegex).match()){//提取明细页中图片链接
    		logger.log("["+currentUrl + "] is detail url match [" + detailRegex + "]");
    		//记录导航及明细页面标题
        	String title = (String) page.getHtml().xpath("//div[@class='center margintop border clear main']/div[@class='title']/text()").toString();
        	if(title!=null && !title.equals("")){
        		url2TitleMap.put(currentUrl, title);
        	}
        	//---------------------------------------------------------------------------------
        	List<String> imageUrlList = page.getHtml().xpath("//div[@class='center margintop border clear main']/div[@class='content']/img/@data-original").all();//图片
        	logger.debug("discover image urls~~~"+ imageUrlList.size() + "~~~" + imageUrlList.toString());
        	
        	page.putField("currentUrl", currentUrl);
        	page.putField("title", title);
        	page.putField("url2UrlMap", url2UrlMap);
        	page.putField("url2TitleMap", url2TitleMap);
        	page.putField("imageUrl", imageUrlList);
    	}else{
    		logger.log("not match:" + currentUrl);
    	}
    }

    @Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        Spider.create(new ImagePageProcesser()).addUrl(startUrl)//.addPipeline(new ConsolePipeline())
		.addPipeline(new ImageToLocalPipeline(fileDirectoryPath))
//		.setDownloader(new HttpClientDownloader())
		.thread(threadNum).run();
    }

}
