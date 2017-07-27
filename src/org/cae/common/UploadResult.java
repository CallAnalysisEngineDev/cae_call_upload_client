package org.cae.common;

import java.util.List;

public class UploadResult {

	private boolean successed;
	private List<String> failList;
	private String errInfo;

	public UploadResult() {
		this.successed = true;
	}

	public UploadResult(boolean successed, List<String> failList, String errInfo) {
		this.successed = successed;
		this.failList = failList;
		this.errInfo = errInfo;
	}

	public boolean isSuccessed() {
		return successed;
	}

	public void setSuccessed(boolean successed) {
		this.successed = successed;
	}

	public List<String> getFailList() {
		return failList;
	}

	public void setFailList(List<String> failList) {
		this.failList = failList;
	}

	public String getErrInfo() {
		return errInfo;
	}

	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}

	@Override
	public String toString() {
		if (successed) {
			return "successed:" + successed + "\n";
		} else {
			if (failList != null) {
				String str = "";
				for (int i = 0; i < failList.size(); i++) {
					if (i == failList.size() - 1) {
						str += failList.get(i);
					} else {
						str += failList.get(i) + ",";
					}
				}
				return "successed:" + successed + "\n" + "errInfo:" + errInfo
						+ "\n" + "failList:" + str + "\n";
			} else {
				return "successed:" + successed + "\n" + "errInfo:" + errInfo
						+ "\n";
			}
		}
	}

}
