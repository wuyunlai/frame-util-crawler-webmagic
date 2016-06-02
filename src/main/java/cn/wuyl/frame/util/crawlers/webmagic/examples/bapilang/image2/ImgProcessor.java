package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang.image2;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

public class ImgProcessor implements PageProcessor {

	private String urlPattern;
	 
    private Site site;
 
    public ImgProcessor(String startUrl, String urlPattern) {
        this.site = Site.me().setDomain(UrlUtils.getDomain(startUrl));
        this.urlPattern= urlPattern;
    }
 
    public void process(Page page) {
 
        String imgRegex = "http://www.meizitu.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{1,4}/[0-9]{1,4}/[0-9]{1,4}.jpg";
        List<String> requests = page.getHtml().links().regex(urlPattern).all();
        page.addTargetRequests(requests);
        String imgHostFileName = page.getHtml().xpath("//title/text()").toString().replaceAll("[|\\pP‘’“”\\s(妹子图)]", "");
        List<String> listProcess = page.getHtml().$("div#picture").regex(imgRegex).all();
        //此处将标题一并抓取，之后提取出来作为文件名
        listProcess.add(0, imgHostFileName);
        page.putField("img", listProcess);
 
    }
 
    public Site getSite() {
        return site;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileStorePath = "E:\\crawler\\data\\webmagic\\image\\";
        String urlPattern = "http://www.meizitu.com/[a-z]/[0-9]{1,4}.html";
        ImgProcessor imgspider=new ImgProcessor("http://www.meizitu.com/",urlPattern);
 
        //webmagic采集图片代码演示，相关网站仅做代码测试之用,请勿过量采集
        Spider.create(imgspider)
                .addUrl("http://www.meizitu.com/")
                .addPipeline(new ImgPipeline(fileStorePath))
                .thread(10)       //此处线程数可调节
                .run();
	}

}
