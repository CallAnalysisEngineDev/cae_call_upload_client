package org.cae.common;

public class IConstant {

	// 上传call表时服务器的URL
	// public final static String UPLOAD_SERVER_URL =
	// "http://localhost:81/cae_call_upload_server/upload";
	public final static String UPLOAD_SERVER_URL = "http://123.207.27.244/cae_call_upload_server/upload";

	// 上传call表的版本号
	public final static Integer CALL_VERSION = 32;

	// 上传、下载时产生的临时压缩文件名
	public final static String ZIP_NAME = "result.zip";

	// 下载call表时服务器的URL
	public final static String DOWNLOAD_SERVER_URL = "http://123.207.27.244/cae_call_upload_server/download";

	// 下载的call表覆盖到本地的路径
	public final static String DOWNLOAD_HTML_PATH = "D:\\develop\\nginx-1.12.1\\cae\\resource\\html\\aqours";
}
