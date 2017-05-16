package org.cae.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.cae.common.IConstant;
import org.cae.common.Util;

public class DownloadCallClient {

	public static void main(String[] args) {
		downloadFile();
	}

	public static void downloadFile() {
        URL url;
		try {
			url = new URL(IConstant.DOWNLOAD_SERVER_URL);
			HttpURLConnection conn =(HttpURLConnection)url.openConnection();
			if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
				InputStream inputStream=conn.getInputStream();
				FileOutputStream fos=new FileOutputStream(new File(IConstant.ZIP_NAME));
				byte[] b=new byte[1024];
	            int len=0;
	            while((len=inputStream.read(b))!=-1){
	                fos.write(b, 0, len);
	            }
	            inputStream.close();
	            fos.flush();
	            fos.close();
	            Util.unzip();
	            new File(IConstant.ZIP_NAME).delete();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }
}
