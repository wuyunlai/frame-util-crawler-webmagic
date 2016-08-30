package cn.wuyl.frame.util.crawlers.webmagic.pipeline;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.wuyl.frame.util.crawlers.webmagic.Constant;
import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import cn.wuyl.frame.util.crawlers.webmagic.utils.DownloadUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class FilePipeline extends AbstractFilePipeline implements Pipeline {
	protected ILogger logger = new Logger(getClass());

	public FilePipeline() {
		super();
	}

	public FilePipeline(String path) {
		super(path);
	}

    public FilePipeline(String path, String fileSubFolder) {
		super(path, fileSubFolder);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
    	Map<String,Object> resultMap = resultItems.getAll();
    	resultMap.entrySet();
    	if(resultMap.containsKey(Constant.CURRENT_URL) && resultMap.containsKey(Constant.FILE_TITLE) && resultMap.containsKey(Constant.FILE_LIST)){
        	String currentUrl = resultItems.get(Constant.CURRENT_URL);
            String title = resultItems.get(Constant.FILE_TITLE);
            if(currentUrl != null && !currentUrl.equals("")){
            	logger.debug("check title [" + currentUrl + "][" + title + "] ...");
            	if(checkKeyWord(title)){
            		logger.log("check title [" + currentUrl + "][" + title + "] success!");
            		title = title==null ? title : (title+"("+currentUrl+")").replaceAll("[\\/:*?\"<>|.]", "");
            		List<String> fileList = resultItems.get(Constant.FILE_LIST);
//            		Map<String,String> url2UrlMap = resultItems.get("url2UrlMap");
//            		Map<String,String> url2TitleMap = resultItems.get("url2TitleMap");
            		String type = fileSubFolder;
//            		if(url2UrlMap!=null && url2TitleMap!=null){
//            			String parentUrl = url2UrlMap.get(currentUrl);
//            			if(parentUrl!=null){
//            				type = url2TitleMap.get(parentUrl);
//            				logger.log("currentUrl [" + currentUrl + "（" + title + "）] 's parentUrl is [" + parentUrl + "（" + type + "）]");
//            			}
//            		}
            		String fileStoreDirectory = this.getFileStorePath() + (type==null || type.equals("")?"":type + "\\") + (title==null || title.equals("") ? "" : title + "\\");
            		String[] files = null;
            		File file = getFile(fileStoreDirectory);//new File(fileStoreDirectory);
            		int fileCount = 0;
            		if(file.exists()){
            			files=file.list();
            			if(files!=null){
            				fileCount = files.length;
            			}
            		}
            		if(fileCount < fileList.size()){
            			logger.log("directory[" + fileStoreDirectory + "] 's files[" + fileList.size() + "] totol has [" + fileList.size() + "]. download begin...");
            			logger.log("directory[" + fileStoreDirectory + "] 's files["+(fileList.size()-fileCount)+"] is not download complete! download start at " + fileCount);
            			for(int i = (fileCount==0?fileCount:fileCount-1) ; i < fileList.size(); i ++){
            				String fileUrl = fileList.get(i);
            				String extName=com.google.common.io
            						.Files.getFileExtension(fileUrl);
            				try{
            					logger.log("directory[" + fileStoreDirectory + "] 's download files[" + fileUrl + "] to [" + i + "." + extName +"] download begin...");
            					//这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
            					DownloadUtils.GenerateImage(fileStoreDirectory
            							, i+"", fileUrl);
            					logger.log("directory[" + fileStoreDirectory + "] 's download files[" + fileUrl + "] to [" + i + "." + extName +"] download success!");
            				}catch(Exception e){
            					logger.log(e.getMessage(),"ERROR");
            					logger.log("directory[" + fileStoreDirectory + "] 's download files[" + fileUrl + "] to [" + i + "." + extName +"] download skip!");
            					break;
            				}
            			}
            			logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is not download complete! files[" + fileList.size() + "] download begin...");
            		}else{
            			logger.log("directory[" + fileStoreDirectory + "] 's files["+fileCount+"] is download completed! files[" + fileList.size() + "] cancel download!");
            		}
            	}
            }
    	}
	}

	private boolean checkKeyWord(String title){
		if(title != null && !title.equals("") && (title.indexOf("衫浦则夫")>0||title.indexOf("捆")>0
    			||title.indexOf("绑")>0||title.indexOf("缚")>0||title.indexOf("绳")>0||title.indexOf("调教")>0
    			||title.indexOf("链")>0||title.indexOf("铐")>0||title.indexOf("锁")>0||title.indexOf("胶衣")>0||title.indexOf("女王")>0
    			||title.indexOf("重口")>0||title.indexOf("奴")>0||title.indexOf("犬")>0||title.indexOf("蜘蛛")>0||title.indexOf("辱")>0
    			||title.toLowerCase().indexOf("sm")>0||title.toLowerCase().indexOf("bondage")>0||title.indexOf("X-City")>0
    			||title.toLowerCase().indexOf("shibary")>0||title.toLowerCase().indexOf("kb")>0||title.toLowerCase().indexOf("x-city")>0
    			||title.toLowerCase().indexOf("houseoftaboo")>0||title.toLowerCase().indexOf("kink")>0
    			||title.toLowerCase().indexOf("house")>0||title.toLowerCase().indexOf("taboo")>0
    			||title.toLowerCase().indexOf("coffee")>0||title.toLowerCase().indexOf("hardtied")>0
    			||title.toLowerCase().indexOf("fuckmachine")>0||title.toLowerCase().indexOf("choye")>0
    			||title.toLowerCase().indexOf("latex")>0||title.toLowerCase().indexOf("zlata")>0
    		||title.indexOf("动图")>0||title.indexOf("动态图")>0
    		||title.indexOf("GIF")>0||title.toLowerCase().indexOf("gif")>0
			||title.indexOf("虐")>0||title.indexOf("禁")>0||title.indexOf("兽")>0||title.indexOf("触手")>0||title.indexOf("崩坏")>0
			||title.indexOf("宅配便")>0||title.indexOf("仆")>0||title.indexOf("僕")>0||title.indexOf("纳屋")>0||title.indexOf("对魔忍")>0
			||title.indexOf("牛奶猎人")>0||title.indexOf("怪物猎人")>0||title.indexOf("地下奸禁")>0||title.indexOf("好胜的女人")>0||title.indexOf("害羞的女佣")>0
			||title.indexOf("牧部")>0||title.indexOf("S女子会")>0||title.indexOf("肉便器")>0||title.indexOf("强制")>0||title.indexOf("绝顶装置")>0
			||title.indexOf("女骑士")>0||title.indexOf("捕缚凌辱")>0||title.indexOf("白的欲望")>0||title.indexOf("堀江耽闺")>0||title.indexOf("堕天使")>0
			||title.indexOf("饭尾嘉和")>0||title.indexOf("灰司")>0||title.indexOf("少年复仇调教计划")>0||title.indexOf("凛")>0||title.indexOf("夜欲棒逆")>0
			||title.indexOf("人妻里子")>0||title.indexOf("牝")>0||title.indexOf("东云龙")>0||title.indexOf("百花凌乱")>0||title.indexOf("触装天使")>0
			||title.indexOf("Lost In An ApeWorld")>0||title.indexOf("闷絕")>0||title.indexOf("堕妻")>0||title.indexOf("小2小7")>0||title.indexOf("高津")>0
			||title.indexOf("淫荡痴女公主主导的性游戏")>0||title.toLowerCase().indexOf("inairi kinzou")>0||title.indexOf("后藤晶")>0||title.indexOf("少年的时间")>0||title.indexOf("铃谷拘辱")>0
			||title.indexOf("雷神会")>0||title.indexOf("马辱夫人")>0||title.indexOf("桃屋")>0||title.indexOf("U.R.C")>0||title.indexOf("真空病栋")>0
			||title.indexOf("东方")>0||title.indexOf("飞燕")>0||title.toLowerCase().indexOf("shizuka")>0||title.indexOf("調教")>0||title.indexOf("新道一")>0
			||title.indexOf("魔界留学")>0||title.indexOf("淫肉悦聲")>0||title.indexOf("秋神")>0||title.indexOf("小邑紗希")>0||title.indexOf("M女")>0
			||title.indexOf("痴女之巢")>0||title.indexOf("井上")>0||title.indexOf("乳胶")>0||title.indexOf("钢铁")>0||title.indexOf("地狱")>0
			||title.indexOf("监狱")>0||title.toLowerCase().indexOf("naburu")>0||title.indexOf("淫獸")>0||title.indexOf("淫虐")>0||title.indexOf("甘竹朱郎")>0
			||title.indexOf("让人能随时做爱的神器")>0||title.indexOf("请你在强奸我一次")>0||title.indexOf("東方")>0||title.indexOf("満珍楼")>0||title.indexOf("紫恩")>0
			||title.indexOf("蟲")>0||title.indexOf("特殊性癖")>0||title.indexOf("特殊器械")>0
    		||title.indexOf("阿里布达")>0||title.indexOf("妖")>0||title.indexOf("变装")>0
    		||title.indexOf("间谍")>0||title.indexOf("教师")>0||title.indexOf("护士")>0||title.indexOf("空姐")>0
    		))
		{
			return true;
		}else{
			return false;
		}
	}
}
