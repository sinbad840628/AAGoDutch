package sinbad.godutch.business.base;

import android.content.Context;

/**
 * No.23 新建BusinessBase基类
 * */
public class BusinessBase {

	private Context mContext;
	
	protected BusinessBase(Context pContext) {
		mContext = pContext;
	}
	
	protected Context GetContext() {
		return mContext;
	}
	
	protected String GetString(int pResID) {
		return mContext.getString(pResID);
	}
	
	protected String GetString(int pResID,Object[] pObject) {
		return mContext.getString(pResID, pObject);
	}
}
