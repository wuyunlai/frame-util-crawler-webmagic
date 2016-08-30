package cn.wuyl.frame.util.crawlers.webmagic.pipeline;

import cn.wuyl.frame.util.crawlers.webmagic.Constant;
import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public abstract class AbstractFilePipeline extends FilePersistentBase implements Pipeline {
    protected ILogger logger = new Logger(getClass());
	
    protected String fileStorePath;

    protected String fileSubFolder = "";

    public AbstractFilePipeline() {
    	this.fileStorePath = Constant.FILE_STORE_PATH;
        setPath(fileStorePath);
    }
 
    public AbstractFilePipeline(String path) {
    	this.fileStorePath = path;
        setPath(fileStorePath);
    }
    
    public AbstractFilePipeline(String path,String fileSubFolder) {
    	this.fileStorePath = path;
    	this.fileSubFolder = fileSubFolder;
        setPath(fileStorePath);
    }
    
	public abstract void process(ResultItems resultItems, Task task);

	public String getFileStorePath() {
		return fileStorePath;
	}

	public void setFileStorePath(String fileStorePath) {
		this.fileStorePath = fileStorePath;
	}
	
}
