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
    String fileStorePath = this.path;

    public ImageToLocalPipeline() {
        setPath(Constant.DATA_PATH);
        fileStorePath = this.path;
    }
 
    public ImageToLocalPipeline(String path) {
        setPath(path);
        fileStorePath = this.path;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        
    	String currentUrl = resultItems.get("currentUrl");
        String title = resultItems.get("title");
        if(currentUrl != null && !currentUrl.equals("")){
        	logger.debug("check title [" + currentUrl + "][" + title + "] ...");
        	if(title != null && !title.equals("") && (title.indexOf("衫浦则夫")>0||title.indexOf("捆")>0
        			||title.indexOf("绑")>0||title.indexOf("缚")>0||title.indexOf("绳")>0||title.indexOf("调教")>0
        			||title.indexOf("链")>0||title.indexOf("铐")>0||title.indexOf("锁")>0||title.indexOf("胶衣")>0||title.indexOf("女王")>0
        			||title.indexOf("重口")>0||title.indexOf("奴")>0||title.indexOf("犬")>0||title.indexOf("蜘蛛")>0||title.indexOf("辱")>0
        			||title.toLowerCase().indexOf("sm")>0||title.toLowerCase().indexOf("bondage")>0
        			||title.toLowerCase().indexOf("shibary")>0||title.toLowerCase().indexOf("kb")>0
        			||title.toLowerCase().indexOf("houseoftaboo")>0||title.toLowerCase().indexOf("kink")>0
        			||title.toLowerCase().indexOf("house")>0||title.toLowerCase().indexOf("taboo")>0
        			||title.toLowerCase().indexOf("coffee")>0||title.toLowerCase().indexOf("hardtied")>0
        			||title.toLowerCase().indexOf("fuckmachine")>0||title.toLowerCase().indexOf("choye")>0
        			||title.toLowerCase().indexOf("latex")>0||title.toLowerCase().indexOf("zlata")>0
//        		||title.indexOf("动图")>0||title.indexOf("动态图")>0
//        		||title.indexOf("GIF")>0||title.toLowerCase().indexOf("gif")>0
        			||title.indexOf("虐")>0||title.indexOf("禁")>0||title.indexOf("兽")>0||title.indexOf("触手")>0||title.indexOf("崩坏")>0
//        		||title.indexOf("阿里布达")>0||title.indexOf("妖")>0||title.indexOf("变装")>0
//        		||title.indexOf("间谍")>0||title.indexOf("教师")>0||title.indexOf("护士")>0||title.indexOf("空姐")>0
        			)){
        		logger.log("check title [" + currentUrl + "][" + title + "] success!");
        		title = title==null ? title : (title+currentUrl).replaceAll("[\\/:*?\"<>|.]", "");
        		Map<String,String> url2UrlMap = resultItems.get("url2UrlMap");
        		Map<String,String> url2TitleMap = resultItems.get("url2TitleMap");
        		List<String> imageUrlList = resultItems.get("imageUrl");
        		String type = "";
        		if(url2UrlMap!=null && url2TitleMap!=null){
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
        			logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is download complete! images totol has [" + imageUrlList.size() + "]. download begin...");
        			logger.log("directory[" + fileStoreDirectory + "] 's files["+(imageUrlList.size()-fileCount)+"] is not download complete! download start at " + fileCount);
        			for(int i = (fileCount==0?fileCount:fileCount-1) ; i < imageUrlList.size(); i ++){
        				String imageUrl = imageUrlList.get(i);
        				String extName=com.google.common.io
        						.Files.getFileExtension(imageUrl);
        				try{
        					logger.log("directory[" + fileStoreDirectory + "] 's download images[" + imageUrl + "] download begin...");
        					//这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
        					DownloadUtils.GenerateImage(fileStoreDirectory
        							, i+"", imageUrl);
        					logger.log("directory[" + fileStoreDirectory + "] 's download images[" + imageUrl + "] to [" + i + "." + extName +"] download success!");
        				}catch(Exception e){
        					logger.log(e.getMessage(),"ERROR");
        				}
        			}
        			logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is not download complete! images[" + imageUrlList.size() + "] download begin...");
        		}else{
        			logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is download completed! images[" + imageUrlList.size() + "] cancel download!");
        		}
        	}
        }
    }

}
