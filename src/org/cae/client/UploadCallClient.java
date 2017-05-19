package org.cae.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cae.common.IConstant;
import org.cae.common.UploadResult;
import org.cae.common.Util;
import org.cae.p2h.client.TransformProxy;

public class UploadCallClient {

	private Log logger=LogFactory.getLog(this.getClass());
	
	private String destDir;
	
	public void start(){
		new TransformProxy().transform();
		getHtmlPath();
		String zipPath=Util.zip(destDir);
		UploadResult result=upload(zipPath);
		deleteFile(zipPath);
		handleResult(result);
	}
	
	private void getHtmlPath(){
		Properties properties = null;
		try {
			InputStream in = this.getClass().getResourceAsStream("/p2h.properties");
			properties = new Properties();
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		destDir=properties.getProperty("file.destdir");
	}
	
	@SuppressWarnings("finally")
	private UploadResult upload(String path){
		UploadResult theResult = null;
		String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            // 统一资源
            URL url = new URL(IConstant.UPLOAD_SERVER_URL);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置请求内容类型
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            String uploadFile = path;
            String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data;name=\"callVersion\""+end);
            ds.writeBytes(end);
            ds.writeBytes(IConstant.CALL_VERSION);
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\"" + filename
                    + "\";callVersion=100" + end);
            ds.writeBytes("Content-Type: application/octet-stream"+ end);
            ds.writeBytes(end);
            FileInputStream fStream = new FileInputStream(uploadFile);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            /* close streams */
            fStream.close();
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            ds.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                tempLine = null;
                resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
                JSONObject result=JSONObject.fromObject(resultBuffer.toString());
                if(result.getBoolean("successed")){
                	theResult=new UploadResult();
                }
                else{
                	theResult=new UploadResult(result.getBoolean("successed"), (List<String>)result.get("result"),result.getString("errInfo"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                try {
                    ds.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return theResult;
        }
	}
	
	private void deleteFile(String zipPath){
		File file=new File(destDir);
		if(file.isDirectory()){
			File[] htmls=file.listFiles();
			for(File html:htmls){
				html.delete();
			}
			file.delete();
		}
		file=new File(zipPath);
		file.delete();
	}
	
	private void handleResult(UploadResult result) {
		if(result.isSuccessed()){
			logger.info("上传成功");
		}
		else{
			logger.error("上传失败,失败原因:"+result.getErrInfo());
			if(result.getFailList()!=null){
				String str="";
				List<String> failList=result.getFailList();
				for(int i=0;i<failList.size();i++){
					if(i==failList.size()-1){
						str+=failList.get(i);
					}
					else{
						str+=failList.get(i)+",";
					}
				}
				logger.error("失败歌曲名单:"+str);
			}
		}
	}
	
	public static void main(String[] args) {
		new UploadCallClient().start();
	}

}
