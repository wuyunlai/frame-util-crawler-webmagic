package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang.image.v2;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.wuyl.frame.util.crawlers.webmagic.Constant;
import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import cn.wuyl.frame.util.crawlers.webmagic.utils.DownloadUtils;
import cn.wuyl.frame.util.crawlers.webmagic.utils.abstractFilePersistentBase;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class ImageToLocalPipeline extends abstractFilePersistentBase implements Pipeline {

    protected ILogger logger = new Logger(getClass());

    public ImageToLocalPipeline() {
        setPath(Constant.DATA_PATH);
    }
 
    public ImageToLocalPipeline(String path) {
        setPath(path);
    }
 
    public void process(ResultItems resultItems, Task task) {
        String fileStorePath = this.path;
        
        String title = resultItems.get("title");
        logger.log("check title [" + title + "] begin...");
        if(title != null && !title.equals("") && (title.indexOf("衫浦则夫")>0||title.indexOf("捆")>0
        		||title.indexOf("绑")>0||title.indexOf("缚")>0||title.indexOf("绳")>0||title.indexOf("调教")>0
        		||title.indexOf("重口")>0||title.indexOf("奴")>0||title.indexOf("犬")>0||title.indexOf("辱")>0
        		||title.toLowerCase().indexOf("sm")>0||title.toLowerCase().indexOf("bondage")>0
        		||title.toLowerCase().indexOf("shibary")>0||title.toLowerCase().indexOf("kb")>0
        		||title.indexOf("GIF")>0||title.toLowerCase().indexOf("gif")>0
        		||title.indexOf("动图")>0||title.indexOf("动态图")>0
        		||title.indexOf("虐")>0||title.indexOf("禁")>0||title.indexOf("兽")>0||title.indexOf("触手")>0||title.indexOf("崩坏")>0
//        		||title.indexOf("阿里布达")>0||title.indexOf("妖")>0||title.indexOf("变装")>0
//        		||title.indexOf("间谍")>0||title.indexOf("教师")>0||title.indexOf("护士")>0||title.indexOf("空姐")>0
        		)){
            logger.log("check title [" + title + "] success!");
        	String currentUrl = resultItems.get("currentUrl");
        	title = title==null ? title : (title+currentUrl).replaceAll("[\\/:*?\"<>|.]", "");
        	Map<String,String> url2UrlMap = resultItems.get("url2UrlMap");
        	Map<String,String> url2TitleMap = resultItems.get("url2TitleMap");
        	List<String> imageUrlList = resultItems.get("imageUrl");
        	String type = "";
        	if(url2UrlMap!=null && url2TitleMap!=null){
        		logger.log("currentUrl [" + currentUrl + "（" + title + "）]");
        		String parentUrl = url2UrlMap.get(currentUrl);
        		if(parentUrl!=null){
        			type = url2TitleMap.get(parentUrl);
        			logger.log("currentUrl [" + currentUrl + "（" + title + "）] 's parentUrl is [" + parentUrl + "（" + type + "）]");
        		}
        	}
        	String fileStoreDirectory = fileStorePath + (type==null || type.equals("")?"":type + "\\") + (title==null || title.equals("") ? "" : title + "\\");
        	String[] files = null;
        	File file = new File(fileStoreDirectory);
        	int fileCount = 0;
        	if(file.exists()){
        		files=file.list();
        		if(files!=null){
        			fileCount = files.length;
        		}
        	}
        	if(fileCount < imageUrlList.size()){
        		logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is not download complete! images[" + imageUrlList.size() + "] download begin...");
        		int i = 1;
        		for(String imageUrl : imageUrlList){
        			logger.log("directory[" + fileStoreDirectory + "] 's download images[" + imageUrl + "] download begin...");
        			String extName=com.google.common.io
        					.Files.getFileExtension(imageUrl);
        			try{
        				//这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
        				DownloadUtils.GenerateImage(fileStoreDirectory
        						, i+"", imageUrl);
            			logger.log("directory[" + fileStoreDirectory + "] 's download images[" + imageUrl + "] to [" + i + "." + extName +"] download end!");
        			}catch(Exception e){
        				logger.log(e.getMessage(),"ERROR");
        			}
        			i ++;
        		}
        		logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is not download complete! images[" + imageUrlList.size() + "] download begin...");
        	}else{
        		logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is download completed! images[" + imageUrlList.size() + "] cancel download!");
        	}
        }else{
            logger.log("check title [" + title + "] failed!");
        }

    }

}
