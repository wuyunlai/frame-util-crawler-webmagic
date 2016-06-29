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
    static String charset = "UTF-8";
    static int threadNum = 5;
    static int sleepTime = 2000;
    private Site site = Site.me().setCharset(charset).setRetryTimes(threadNum).setSleepTime(sleepTime);
    String regex1 = "(/act/\\w{1,20}\\.html)";//"(/tchy/\\w{1,6}/\\w{1,6}\\.html)"//
    String regex2 = "//img[@class='J_imgLazyload']/@src";//"//div[@class='center margintop border clear main']/div[@class='content']/img/@data-original";
    
    public void process(Page page) {
    	String curUrl = page.getUrl().toString();
    	if(page.getUrl().regex(regex1).match() 
    			|| page.getUrl().regex(regex2).match()){//遍历导航及明细链接
    		System.out.println("match:" + curUrl);
        	List<String> urls = page.getHtml().links().regex(regex1).all();//导航
        	System.out.println("urls~~~"+ urls.size() + "~~~" + urls.toString());
//	       	List<String> urls2 = page.getHtml().links().regex("(/rzqhkb/\\w{1,6}\\.html)").all();//明细
//	    	System.out.println("urls2~~~"+ urls2.size() + "~~~" + urls2.toString());
        	List<String> urls3 = page.getHtml().links().all();//图片
        	urls3 = page.getHtml().xpath(regex2).all();
        	System.out.println("urls3~~~"+ urls3.size() + "~~~" + urls3.toString());
//        	urls.addAll(urls2);
        	urls.addAll(urls3);
        	page.addTargetRequests(urls3);
    	}else{//图片
            String imageStr = page.getRawText();
            String url = page.getRequest().getUrl();
			String extName=com.google.common.io
					.Files.getFileExtension(url);//文件类型后缀
            String filename = url.substring(url.lastIndexOf("/")+1, url.length());
//            filename = filename==null ? "" : (url).replaceAll("[\\/:*?\"<>|.]", "");
            filename = (url).replaceAll("[\\/:*?\"<>|.]", "");
            filename = filename + "." + extName;
            System.out.println("@@@@curUrl:" + curUrl + "\r\nurl:" + url + "\r\nfilename:" + filename);
            page.putField("url", url);
            page.putField("filename", filename);//UUID.randomUUID().toString());//bianhao);
            page.putField("imageStr", imageStr);
    	}
    }

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
    	String url = "http://sale.jd.com/act/JrwqAk8oGmWQCsB.html";//"http://www.bapilang.link/rzqhkb/443411.html";//"http://www.bapilang.link/rzqhkb/432524.html";//"http://www.bapilang.link/tchy/13/1.html";
        Spider.create(new ImagePageProcesser()).addUrl(url).addPipeline(new ConsolePipeline())
		.addPipeline(new ImageToLocalPipeline("E:\\crawler\\data\\webmagic\\image\\"))
		.setDownloader(new ImageDownloader())
		.thread(5).run();
    }

}
