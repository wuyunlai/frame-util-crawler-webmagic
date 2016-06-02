package cn.wuyl.frame.util.crawlers.webmagic.utils;

import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public abstract class abstractFilePersistentBase extends FilePersistentBase implements Pipeline {
	
	public abstract void process(ResultItems resultItems, Task task);

}
