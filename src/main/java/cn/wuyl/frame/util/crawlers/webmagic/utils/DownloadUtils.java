package cn.wuyl.frame.util.crawlers.webmagic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import cn.wuyl.frame.util.crawlers.webmagic.log.Logger;

public class DownloadUtils{
	static Logger logger = new Logger(DownloadUtils.class);
	
    @SuppressWarnings("deprecation")
	public static boolean GenerateImage(String fileStorePath, String filename, String fileUrl){//对字节数组字符串进行Base64解码并生成图片
    	boolean rtn = true;
		//这里通过httpclient下载之前抓取到的网址，并放在对应的文件中
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpResponse response = null;
        int statusCode=0;
        InputStream in = null;
		HttpGet httpget = new HttpGet(fileUrl);
		try {
//			HttpParams params = httpclient.getParams();
//			HttpConnectionParams.setSoTimeout(params, 3 * 1000);// 设定连接等待时间
//			HttpConnectionParams.setConnectionTimeout(params, 3 * 1000);// 设定超时时间
			response = httpclient.execute(httpget);
			statusCode = response.getStatusLine().getStatusCode();
			if (response != null && statusCode == 200) {// 请求被接受
				HttpEntity entity = response.getEntity();
				try {
					in = entity.getContent();//获取文件流
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					logger.error(e.getClass().getName()+" : "+e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e.getClass().getName()+" : "+e.getMessage());
				}
				if(in!=null){//如果得到文件流
					StringBuffer sb = new StringBuffer();
					StringBuffer imgFileDirectory =sb.append(fileStorePath);
					File fileDirectory = new File(imgFileDirectory.toString());
					if (!fileDirectory.exists()) {//文件路径不存在
						fileDirectory.mkdirs();//创建文件夹
					}
					
					String extName=com.google.common.io
							.Files.getFileExtension(fileUrl);//文件类型后缀
					StringBuffer imgFileName = imgFileDirectory.append(filename).append(".").append(extName);
					
					File file = new File(imgFileName.toString());
					if(!file.exists()){//文件不存在
						file.createNewFile();//创建文件
					}
					
					FileOutputStream fout = new FileOutputStream(file);
					int l = -1;
					byte[] tmp = new byte[1024];
					while ((l = in.read(tmp)) != -1) {
						fout.write(tmp,0,l);
					}
					fout.flush();
					fout.close();
				}
			}else{
				logger.error("statusCode["+statusCode+"]:fileUrl:"+fileUrl);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error(e.getClass().getName()+" : "+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getClass().getName()+" : "+e.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getClass().getName()+" : "+e.getMessage());
			}
		}

		return rtn;

    }
    
	public static boolean GenerateImage2(String fileStorePath, String filename, String imgStr){//对字节数组字符串进行Base64解码并生成图片
    	boolean rtn = true;
		try {
				if(imgStr!=null){//如果得到文件流
					StringBuffer sb = new StringBuffer();
					StringBuffer imgFileDirectory =sb.append(fileStorePath);
					File fileDirectory = new File(imgFileDirectory.toString());
					if (!fileDirectory.exists()) {//文件路径不存在
						fileDirectory.mkdirs();//创建文件夹
					}
					
					StringBuffer imgFileName = imgFileDirectory.append(filename);
					
					File file = new File(imgFileName.toString());
					if(!file.exists()){//文件不存在
						file.createNewFile();//创建文件
					}
					FileOutputStream fout = new FileOutputStream(file);
	            	PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(fout,"UTF-8"));
	            	printWriter.println(imgStr);
	            	printWriter.close();

					fout.flush();
					fout.close();
				}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error(e.getClass().getName()+" : "+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getClass().getName()+" : "+e.getMessage());
		} finally {
		}

		return rtn;

    }
    
}
