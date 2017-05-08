package org.cae.client;

import java.io.InputStream;
import java.util.Properties;

import org.cae.common.UploadResult;
import org.cae.common.Util;
import org.cae.p2h.client.TransformProxy;

public class UploadCallClient {

	public void start(){
		new TransformProxy().transform();
		String htmlPath=getHtmlPath();
		String zipPath=Util.zip(htmlPath);
		UploadResult result=upload(zipPath);
		handleResult(result);
	}
	
	private String getHtmlPath(){
		Properties properties = null;
		try {
			InputStream in = this.getClass().getResourceAsStream("/p2h.properties");
			properties = new Properties();
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties.getProperty("file.destdir");
	}
	
	private UploadResult upload(String path){
		// TODO Auto-generated method stub
		return null;
	}
	
	private void handleResult(UploadResult result) {
		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args) {
		new UploadCallClient().start();
	}

}
