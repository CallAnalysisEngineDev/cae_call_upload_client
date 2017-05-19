package org.cae.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cae.common.IConstant;
import org.cae.common.Util;

public class DownloadCallClient{

	private Log logger=LogFactory.getLog(this.getClass());
	
	public static void main(String[] args) {
		DownloadCallClient client=new DownloadCallClient();
		client.downloadFile();
	}

	public void downloadFile() {
		try {
			URL url = new URL(IConstant.DOWNLOAD_SERVER_URL);
			HttpURLConnection conn =(HttpURLConnection)url.openConnection();
			if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
				int fileLength=conn.getContentLength();
				logger.info("下载开始,文件大小为"+fileLength/(1024*1024)+"M");
				InputStream inputStream=conn.getInputStream();
				FileOutputStream fos=new FileOutputStream(new File(IConstant.ZIP_NAME));
				byte[] b=new byte[2048];
				int read=0;
	            int len=0;
	            String previous = "0.0";
	            while((len=inputStream.read(b))!=-1){
	            	read+=len;
	                fos.write(b, 0, len);
	                DecimalFormat df = new DecimalFormat("0");
	                String current=df.format(((double)read/(double)fileLength)*100);
	                if(!previous.equals(current)){
	                	logger.info("目前下载进度为"+current+"%");
	                }
	                previous=current;
	            }
	            inputStream.close();
	            fos.flush();
	            fos.close();
	            logger.info("下载call表结束,开始解压zip包");
	            Util.unzip(logger);
	            logger.info("解压完成,即将删除zip包");
	            new File(IConstant.ZIP_NAME).delete();
	            logger.info("全部完成");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }
	
}
