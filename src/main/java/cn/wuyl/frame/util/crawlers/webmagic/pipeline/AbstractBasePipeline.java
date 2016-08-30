package cn.wuyl.frame.util.crawlers.webmagic.pipeline;

import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public abstract class AbstractBasePipeline implements Pipeline {
    protected ILogger logger = new Logger(getClass());
	
	public abstract void process(ResultItems resultItems, Task task);

}
