package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

public class FilePipelineEXT extends FilePipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String titleKey = "title";
    private String contentKey = "content";

    public FilePipelineEXT() {
        super.setPath("/data/webmagic/");
    }

    public FilePipelineEXT(String path) {
        super.setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        String url = resultItems.getRequest().getUrl();
        String title = "";
        String content = "";
       try {
//            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html")),"UTF-8"));
//            printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
//                    printWriter.println(entry.getKey() + ":");
                    System.out.println("@@" + entry.getKey() + ":" + entry.getValue());
                    if(entry.getKey().equals(titleKey)){
                    	title = entry.getValue().toString();
                    }else if(entry.getKey().equals(contentKey)){
                    	content = entry.getValue().toString();
                    }
                    for (Object o : value) {
//                        printWriter.println(o);
                    }
                } else {
//                    printWriter.println(entry.getKey() + ":\t" + entry.getValue());
                    System.out.println("@@##" + entry.getKey() + ":" + entry.getValue());
                    if(entry.getKey().equals(titleKey)){
                    	title = entry.getValue().toString();
                    }else if(entry.getKey().equals(contentKey)){
                    	content = entry.getValue().toString();
                    	content.replace(" 　　", "\r\n");
                    }
                }
            }
            String filePath = path + title + ".txt";
            System.out.println("@@filePath:" + filePath);
            if(!title.equals("")&&!content.equals("")&&(title.indexOf("调教")>0||title.indexOf("绳")>0||title.indexOf("重口")>0||title.indexOf("奴")>0||title.indexOf("犬")>0||title.indexOf("辱")>0||title.indexOf("虐")>0||title.indexOf("禁")>0||title.indexOf("兽")>0||title.indexOf("阿里布达")>0||title.indexOf("缚")>0||title.indexOf("触手")>0||title.indexOf("崩坏")>0||title.indexOf("妖")>0||title.indexOf("变装")>0||title.indexOf("间谍")>0||title.indexOf("教师")>0||title.indexOf("护士")>0||title.indexOf("空姐")>0)){
            	PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + title + ".txt")),"UTF-8"));
            	printWriter.println(title);
            	printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            	printWriter.println(content);
            	printWriter.close();
            }
        } catch (IOException e) {
            logger.warn("write file error", e);
        }
    }
}
