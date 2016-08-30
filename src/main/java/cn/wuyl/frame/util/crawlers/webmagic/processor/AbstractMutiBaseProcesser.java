package cn.wuyl.frame.util.crawlers.webmagic.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.processor.PageProcessor;

public abstract class AbstractMutiBaseProcesser extends AbstractBaseProcesser implements PageProcessor {
	protected String count = "13";
    protected String domain = "http://www.bapilang.link";
    protected String naviRegex = "(/tchy/"+count+"/\\w{1,6}\\.html)";
    protected String detailRegex = "(/rzqhkb/\\w{1,6}\\.html)";
    protected String chartSet = "utf-8";
    protected int threadNum = 3;
	protected int retryTimes = 3;
	protected int sleepTime = 1000;
	protected Site site = null;
	protected Map paramMap = new HashMap();

	/**
	 * 构造函数（简）
	 * @param domain 域名
	 */
	public AbstractMutiBaseProcesser(String domain) {
		super();
		this.domain = domain;
		this.site = Site.me().setDomain(domain).setCharset(chartSet).setRetryTimes(retryTimes).setSleepTime(sleepTime);
	}

	/**
	 * 构造函数（简）
	 * @param domain 域名
	 * @param naviRegex 导航正则表达式匹配
	 * @param detailRegex 明细正则表达式匹配
	 */
	public AbstractMutiBaseProcesser(String domain, String naviRegex, String detailRegex) {
		super();
		this.domain = domain;
		this.naviRegex = naviRegex;
		this.detailRegex = detailRegex;
		this.site = Site.me().setDomain(domain).setCharset(chartSet).setRetryTimes(retryTimes).setSleepTime(sleepTime);
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
	public AbstractMutiBaseProcesser(String domain, String naviRegex, String detailRegex,
			String chartSet, int threadNum, int retryTimes, int sleepTime) {
		super();
		this.domain = domain;
		this.naviRegex = naviRegex;
		this.detailRegex = detailRegex;
		this.chartSet = chartSet;
		this.threadNum = threadNum;
		this.retryTimes = retryTimes;
		this.sleepTime = sleepTime;
		this.site = Site.me().setDomain(domain).setCharset(chartSet).setRetryTimes(retryTimes).setSleepTime(sleepTime);
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
    	String currentUrl = page.getUrl().toString();
		logger.debug("[process] currentUrl:" + currentUrl);
    	if(page.getUrl().regex(naviRegex).match()){//遍历导航及明细链接
    		logger.log("[process] URL ["+currentUrl + "] is navi url match [" + naviRegex + "]");
        	List<String> naviUrls = page.getHtml().links().regex(naviRegex).all();//一级导航
        	logger.debug("[process] discover navi urls~~~"+ naviUrls.size() + "~~~" + naviUrls.toString());
	       	List<String> detailUrls = page.getHtml().links().regex(detailRegex).all();//二级明细
	    	logger.debug("[process] discover detail urls~~~"+ detailUrls.size() + "~~~" + detailUrls.toString());
	       	naviUrls.addAll(detailUrls);
	       	//logger.debug("[process] discover urls add to spider search list");
        	page.addTargetRequests(naviUrls);//添加到爬取链接
        	//---------------------------------------------------------------------------------
        	afterProcessDo(page,"navi",paramMap);
    	}else if(page.getUrl().regex(detailRegex).match()){//提取明细页中图片链接
    		logger.log("[process] URL ["+currentUrl + "] is detail url match [" + detailRegex + "]");
        	//---------------------------------------------------------------------------------
        	afterProcessDo(page,"detail",paramMap);
    	}else{
    		logger.log("[process] URL ["+currentUrl + "] is not matched!");
    	}
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	
	/**
	 * 在process方法执行后可自定义处理
	 * @param page 当前page
	 * @param type 当前地址类型："navi","导航地址";"detail","明细地址"
	 * @param parmMap 全局map对象
	 */
	public abstract void afterProcessDo(Page page,String type,Map parmMap);

}
