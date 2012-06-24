package sinbad.godutch.model;

import java.util.Date;

import sinbad.godutch.utility.DateTools;
import android.R.integer;

public class ModelAccountBook {

	private int mAccountBookID;
	
	private String mAccountBookName;
	
	private Date mCreateDate = new Date();
	
	private int mState = 1;
	
	private int mIsDefault;

	public int GetAccountBookID() {
		return mAccountBookID;
	}

	public void SetAccountBookID(int pAccountBookID) {
		this.mAccountBookID = pAccountBookID;
	}

	public String GetAccountBookName() {
		return mAccountBookName;
	}

	public void SetAccountBookName(String pAccountBookName) {
		this.mAccountBookName = pAccountBookName;
	}

	public Date GetCreateDate() {
		return mCreateDate;
	}

	public void SetCreateDate(Date pCreateDate) {
		this.mCreateDate = pCreateDate;
	}

	public int GetState() {
		return mState;
	}

	public void SetState(int pState) {
		this.mState = pState;
	}

	public int GetIsDefault() {
		return mIsDefault;
	}

	public void SetIsDefault(int pIsDefault) {
		this.mIsDefault = pIsDefault;
	}
		
	
}
