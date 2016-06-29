package cn.wuyl.frame.util.crawlers.webmagic.examples.bapilang.image;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import cn.wuyl.frame.util.crawlers.webmagic.utils.ImageBase64Utils;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class ImageDownloader extends HttpClientDownloader {
	@Override
    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
		String iageStr=null;
		try{
//			byte[] imageByte = EntityUtils.toByteArray(httpResponse.getEntity());
//			InputStream in = httpResponse.getEntity().getContent();
//			iageStr = ImageBase64Utils.convertStreamToString2(in);
			iageStr = httpResponse.getEntity().toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return iageStr;
    }
}
