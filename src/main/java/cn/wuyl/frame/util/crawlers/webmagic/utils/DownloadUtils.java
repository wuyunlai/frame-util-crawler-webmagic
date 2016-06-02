package cn.wuyl.frame.util.crawlers.webmagic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import cn.wuyl.frame.util.crawlers.webmagic.log.ILogger;
import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;
import sun.util.logging.resources.logging;

public class DownloadUtils{
	static Logger logger = new Logger(DownloadUtils.class);
	
    public static boolean GenerateImage(String fileStorePath, String filename, String fileUrl){//对字节数组字符串进行Base64解码并生成图片
    	boolean rtn = true;
		//这里通过httpclient下载之前抓取到的网址，并放在对应的文件中
    	CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(fileUrl);
		HttpResponse response = null;
		InputStream in = null;
		try {
			response = httpclient.execute(httpget);
			if(response !=null){
				HttpEntity entity = response.getEntity();
				try {
					in = entity.getContent();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					log(e.getMessage(),"ERROR");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log(e.getMessage(),"ERROR");
				}
			}
			
			StringBuffer sb = new StringBuffer();
			StringBuffer imgFileDirectory =sb.append(fileStorePath);
			File fileDirectory = new File(imgFileDirectory.toString());
            if (!fileDirectory.exists()) {
            	fileDirectory.mkdirs();
            }
            
			String extName=com.google.common.io
					.Files.getFileExtension(fileUrl);
			StringBuffer imgFileName = imgFileDirectory.append(filename).append(".").append(extName);
			
	        			File file = new File(imgFileName.toString());
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp,0,l);
			}
			fout.flush();
			fout.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log(e.getMessage(),"ERROR");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log(e.getMessage(),"ERROR");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log(e.getMessage(),"ERROR");
			}
		}

		return rtn;

    }
    
    private static void log(String msg){
    	logger.log(msg);
    }
    private static void log(String msg,String logLevel){
    	logger.log(msg,logLevel);
    }
}
