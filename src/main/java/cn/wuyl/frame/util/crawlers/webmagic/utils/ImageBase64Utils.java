package cn.wuyl.frame.util.crawlers.webmagic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import sun.misc.BASE64Encoder;

public class ImageBase64Utils {
	public static String GetImageStr(byte[] data) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		 
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
 
    public static boolean GenerateImage(String path, String filename, String imgStr) {//对字节数组字符串进行Base64解码并生成图片
         if (imgStr == null) //图像数据为空
        {
            return false;
        }
//        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = imgStr.getBytes();//decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = path + filename;//新生成的图片
            File f = new File(imgFilePath);
            if(!f.exists()){
            	f.createNewFile();
            }
            OutputStream out = new FileOutputStream(f);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println("e:" + e.getMessage());
            return false;
        }
    }
    
    public static boolean GenerateImage2(String path, String filename, String imgStr){//对字节数组字符串进行Base64解码并生成图片
    	boolean rtn = true;
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        //这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
        HttpGet httpget = new HttpGet(imgStr);
        HttpResponse response;
        InputStream in = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			in = entity.getContent();
			//生成jpeg图片
			String imgFilePath = path + filename;//新生成的图片
			File file = new File(imgFilePath);
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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
        	rtn = false;
            try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
            	httpclient.close();
            } catch (IOException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
            }
        }
		return rtn;

    }
}
