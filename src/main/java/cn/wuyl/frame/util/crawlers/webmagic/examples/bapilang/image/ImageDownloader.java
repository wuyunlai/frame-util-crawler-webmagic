package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang.image;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import cn.wuyl.frame.util.crawlers.webmagic.utils.ImageBase64Utils;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class ImageDownloader extends HttpClientDownloader {
	@Override
    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
		String iageStr=null;
		try{
			//byte[] imageByte = EntityUtils.toByteArray(httpResponse.getEntity());
			//iageStr = ImageBase64Utils.GetImageStr(imageByte);
			iageStr = httpResponse.getEntity().toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return iageStr;
    }
}
