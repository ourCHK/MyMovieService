package bean;

import java.util.ArrayList;
import java.util.List;

public class PicListInfo {
	
	List<PicInfo> picList;
	
	
	public PicListInfo() {
		picList = new ArrayList<PicInfo>();
		for(int i=0; i<14; i++) {
			PicInfo picInfo = new PicInfo();
			picInfo.setPicName("pic"+i);
			picInfo.setPicPath("/home/chk/Downloads/pic/p"+i+".jpg");
			picList.add(picInfo);
		}
	}
	
	public List<PicInfo> getPicList() {
		return picList;
	}

	public void setPicList(List<PicInfo> picList) {
		this.picList = picList;
	}
	
	
}
