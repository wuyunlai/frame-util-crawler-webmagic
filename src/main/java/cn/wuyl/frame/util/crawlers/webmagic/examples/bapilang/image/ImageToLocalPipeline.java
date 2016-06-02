package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang.image;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wuyl.frame.util.crawlers.webmagic.utils.ImageBase64Utils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public class ImageToLocalPipeline extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private PrintWriter printWriter;
 
//    UrlDao urlDao = new UrlDaoSupport();
 
    public ImageToLocalPipeline(String path) throws UnsupportedEncodingException, FileNotFoundException {
        setPath(path);
    }
 
    public void process(ResultItems resultItems, Task task) {
        String imageStr = resultItems.get("imageStr");
        String bianhao = resultItems.get("bianhao");
        String filename = resultItems.get("filename");
        String url = resultItems.get("url");
        String path = this.path + task.getUUID() + "\\";
        checkAndMakeParentDirecotry(path);
		System.out.println("==================================image_url:"+url);
        if(bianhao != null && imageStr !=null && filename !=null){
        	boolean saveSucess = ImageBase64Utils.GenerateImage2(path, filename, imageStr);
        	if (saveSucess) {
        		System.out.println("==================================成功");
        	}
        }
    }

}
