package sinbad.godutch.controls;

import sinbad.godutch.configration.CommonConstants;
import android.util.Log;

/**
 * No.12 新建SlideMenuItem这个实体类
 * */
public class SlideMenuItem {
	
	private int mItemID;
	private String mTitle;
	
	//No.16 追加构造函数，实例化的时候省事情。
	public SlideMenuItem(int pItemID,String pTitle)
	{
		mItemID = pItemID;
		mTitle = pTitle;
	}
	
	public int getItemID() {
		return mItemID;
	}
	
	public void setItemID(int pItemID) {
		this.mItemID = pItemID;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setmTitle(String pTitle) {
		this.mTitle = pTitle;
	}
}
