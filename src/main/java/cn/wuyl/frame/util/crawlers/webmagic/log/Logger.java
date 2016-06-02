package cn.wuyl.frame.util.crawlers.webmagic.log;

import org.slf4j.LoggerFactory;

import cn.wuyl.frame.util.crawlers.webmagic.Constant;

public class Logger extends cn.wuyl.frame.core.log.Logger implements ILogger{
    private static String logType = Constant.LOGGER_LEVLE;

    public Logger(Class cls) {
    	super(cls,logType);
		// TODO Auto-generated constructor stub
	}

    public Logger(Class cls,String logType) {
    	super(cls,logType);
		// TODO Auto-generated constructor stub
	}

}
