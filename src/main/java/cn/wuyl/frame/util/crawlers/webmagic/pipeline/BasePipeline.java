package cn.wuyl.frame.util.crawlers.webmagic.pipeline;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class BasePipeline extends AbstractBasePipeline implements Pipeline {
    protected ILogger logger = new Logger(getClass());

    @Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
    	Map<String,Object> resultMap = resultItems.getAll();
    	resultMap.entrySet();
    	for(Entry<String, Object> entry : resultMap.entrySet()){
    		logger.log("[process] "+entry.getKey()+":"+entry.getValue().toString());
    	}
	}

}
