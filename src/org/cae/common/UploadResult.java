package org.cae.common;

public class UploadResult {

	private boolean successed;
	private Object result;
	private String errInfo;
	public UploadResult(){
		this.successed=true;
	}
	public UploadResult(boolean successed,Object result,String errInfo){
		this.successed=successed;
		this.result=result;
		this.errInfo=errInfo;
	}
	public boolean isSuccessed() {
		return successed;
	}
	public void setSuccessed(boolean successed) {
		this.successed = successed;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getErrInfo() {
		return errInfo;
	}
	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}
	@Override
	public String toString() {
		if(successed){
			return "successed:"+successed+"\n";
		}
		else{
			return "successed:"+successed+"\n"
					+ "result:"+result+"\n"
					+ "errInfo:"+errInfo+"\n";
		}
	}
	
}
