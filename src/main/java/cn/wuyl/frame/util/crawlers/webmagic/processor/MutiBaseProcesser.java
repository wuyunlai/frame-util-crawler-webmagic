package cn.wuyl.frame.util.crawlers.webmagic.processor;

import java.util.List;
import java.util.Map;

import cn.wuyl.frame.util.crawlers.webmagic.Constant;
import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import cn.wuyl.frame.util.crawlers.webmagic.pipeline.BasePipeline;
import cn.wuyl.frame.util.crawlers.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class MutiBaseProcesser extends AbstractMutiBaseProcesser implements PageProcessor {
	protected String titleRegex = "//div[@class='center margintop border clear main']/div[@class='title']/text()";
    protected String fileRegex = "//div[@class='center margintop border clear main']/div[@class='content']/img/@data-original";
    
	/**
	 * 构造函数（简）
	 * @param domain 域名
	 */
	public MutiBaseProcesser(String domain) {
		super(domain);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 构造函数（简）
	 * @param domain 域名
	 * @param naviRegex 导航正则表达式匹配
	 * @param detailRegex 明细正则表达式匹配
	 */
	public MutiBaseProcesser(String domain, String naviRegex, String detailRegex) {
		super(domain, naviRegex, detailRegex);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 构造函数
	 * @param domain 域名
	 * @param naviRegex 导航正则表达式匹配
	 * @param detailRegex 明细正则表达式匹配
	 * @param chartSet 字符集
	 * @param threadNum 线程数
	 * @param retryTimes 重试次数
	 * @param sleepTime 睡眠时间
	 */
	public MutiBaseProcesser(String domain, String naviRegex, String detailRegex, String chartSet,
			int threadNum, int retryTimes, int sleepTime) {
		super(domain, naviRegex, detailRegex, chartSet, threadNum, retryTimes, sleepTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afterProcessDo(Page page, String type, Map parmMap) {
		// TODO Auto-generated method stub
		page.putField(Constant.CURRENT_URL,page.getUrl().toString());
		if(type.equals("navi")){
			
		}else if(type.equals("detail")){
			String title = (String) page.getHtml().xpath(titleRegex).toString();
        	if(title!=null && !title.equals("")){
        		page.putField(Constant.FILE_TITLE, title);
        	}
        	//---------------------------------------------------------------------------------
        	List<String> fileUrlList = page.getHtml().xpath(fileRegex).all();//图片
        	if(fileUrlList.size()>0){
        		logger.debug("[process][afterProcessDo] discover file urls~~~"+ fileUrlList.size() + "~~~" + fileUrlList.toString());
        		page.putField(Constant.FILE_LIST, fileUrlList);
        	}else{
        		logger.debug("[process][afterProcessDo] haven't found file urls~~~"+ fileUrlList.size() + "~~~" + fileUrlList.toString());
        	}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Spider.create(new MutiBaseProcesser("http://www.bapilang.link"
        		, "(/tchy/13/\\w{1,6}\\.html)"
        		, "(/rzqhkb/\\w{1,6}\\.html)"
        		, "UTF-8"
        		, 3
        		, 3
        		, 10*1000))
        .addUrl("http://www.bapilang.link/tchy/13/1.html")
        //.addPipeline(new BasePipeline())
        .addPipeline(new FilePipeline("E:\\crawler\\data\\webmagic\\file\\","变态另类"))
        .run();
	}

}
