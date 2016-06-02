package cn.wuyl.frame.util.crawlers.webmagic.processor;

import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public abstract class abstractPageProcesser implements PageProcessor {
    protected ILogger logger = new Logger(getClass());

	public abstract void process(Page page);

	public abstract Site getSite();

	public void log(String msg){
		logger.log(msg);
	}
	
	public void log(String msg,String logType){
		logger.log(msg,logType);
	}
}
