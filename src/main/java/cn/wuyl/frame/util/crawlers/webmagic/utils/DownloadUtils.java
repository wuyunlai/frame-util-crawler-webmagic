package cn.wuyl.frame.util.crawlers.webmagic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;

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
	static int max_download_time = 10*1000;
	
    @SuppressWarnings("deprecation")
	public static boolean GenerateImage(String fileStorePath, String filename, String fileUrl){//对字节数组字符串进行Base64解码并生成图片
    	boolean rtn = true;
		//这里通过httpclient下载之前抓取到的网址，并放在对应的文件中
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpResponse response = null;
        int statusCode=0;
        InputStream in = null;
        FileOutputStream fout = null;
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
					in.close();
					in = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e.getClass().getName()+" : "+e.getMessage());
					in.close();
					in = null;
				}
				if(in!=null){//如果得到文件流
					StringBuffer sb = new StringBuffer();
					StringBuffer imgFileDirectory =sb.append(fileStorePath);
					/*File fileDirectory = new File(imgFileDirectory.toString());
					if (!fileDirectory.exists()) {//文件路径不存在
						fileDirectory.mkdirs();//创建文件夹
					}*/
					
					String extName=com.google.common.io
							.Files.getFileExtension(fileUrl);//文件类型后缀
					StringBuffer imgFileName = imgFileDirectory.append(filename).append(".").append(extName);
					
					/*File file = new File(imgFileName.toString());
					if(!file.exists()){//文件不存在
						file.createNewFile();//创建文件
					}*/
					File file = getFile(imgFileName.toString());
					
					fout = new FileOutputStream(file);
					outputFile(in,fout);
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
				if(in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getClass().getName()+" : "+e.getMessage());
			}
			try {
				if(fout != null)
					fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getClass().getName()+" : "+e.getMessage());
			}
		}

		return rtn;

    }
    
/*	public static boolean GenerateImage2(String fileStorePath, String filename, String imgStr){//对字节数组字符串进行Base64解码并生成图片
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

    }*/
	
	public static boolean outputFile(InputStream in,OutputStream out) throws IOException{
		boolean rtn = true;
		Calendar ca = Calendar.getInstance();
		long time = ca.getTimeInMillis();
		logger.log("[DownloadUtils][outputFile] begin at " + ca.getTime().toLocaleString());
		int l = -1;
		byte[] tmp = new byte[1024];
		while ((l = in.read(tmp)) != -1) {
			out.write(tmp,0,l);
			long pass = System.currentTimeMillis()-time;
			if(pass > max_download_time){
				logger.log("[DownloadUtils][outputFile] break after [" + pass +  "] at " + ca.getTime().toLocaleString());
				rtn = false;
				//in.close();
				break;
			}
			logger.debug("[DownloadUtils][outputFile] read continue [" + pass +  "] at " + ca.getTime().toLocaleString());
		}
		if(rtn){
			out.flush();
			out.close();
			in.close();
		}
		logger.log("[DownloadUtils][outputFile] finished..." + ca.getTime().toLocaleString());
		return rtn;
	}
    
	//---copy from us.codecraft.webmagic.utils.FilePersistentBase-----------------------------------------------------------------------------------------
    public static String PATH_SEPERATOR = "/";
	
    /**
     * 得到文件对象同时检查并创建目录
     * @param fullName 带路径文件名
     * @return
     */
    public static File getFile(String fullName) {
        checkAndMakeParentDirecotry(fullName);
        return new File(fullName);
    }

    /**
     * 检查并创建目录
     * @param fullName 文件名或者文件路径
     */
    public static void checkAndMakeParentDirecotry(String fullName) {
        int index = fullName.lastIndexOf(PATH_SEPERATOR);
        if (index > 0) {
            String path = fullName.substring(0, index);
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

}
