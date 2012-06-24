package sinbad.godutch.business;

import java.util.ArrayList;
import java.util.List;

import sinbad.godutch.business.base.BusinessBase;
import sinbad.godutch.database.sqlitedal.SQLiteDALUser;
import sinbad.godutch.model.ModelUser;
import android.content.ContentValues;
import android.content.Context;

/**
 * No.23 新建BusinessUser类
 * */
public class BusinessUser extends BusinessBase {

	// No.23 声明变量
	private SQLiteDALUser mSQLiteDALUser;

	// No.23 新建构造函数
	public BusinessUser(Context pContext) {
		super(pContext);
		mSQLiteDALUser = new SQLiteDALUser(pContext);
	}
	
	// No.24 新建用户
	public boolean InsertUser(ModelUser pInfo) {
		boolean _Result = mSQLiteDALUser.InsertUser(pInfo);

		return _Result;
	}

	// No.24 删除用户
	public boolean DeleteUserByUserID(int pUserID) {
		String _Condition = " And UserID = " + pUserID;
		boolean _Result = mSQLiteDALUser.DeleteUser(_Condition);

		return _Result;
	}
	
	// No.24   更新用户
	public boolean UpdateUserByUserID(ModelUser pModelUser) {
		String _Condition = " UserID = " + pModelUser.getUserID();
		boolean _Result = mSQLiteDALUser.UpdateUser(_Condition, pModelUser);
		
		return _Result;
	}
	
	// No.25 取得没有被逻辑删除的用户
	public List<ModelUser> GetNotHideUser()
	{
		return  mSQLiteDALUser.GetUser(" And State = 1");
	}

	// No.24 得到用户 GetUser
	private List<ModelUser> GetUser(String pCondition) {
		return mSQLiteDALUser.GetUser(pCondition);
	}
	
	// No.24 得到一个用户实体类
	public ModelUser GetModelUserByUserID(int pUserID) {
		List<ModelUser> _List = mSQLiteDALUser.GetUser(" And UserID = "
				+ pUserID);
		if (_List.size() == 1) {
			return _List.get(0);
		} else {
			return null;
		}
	}

	// No.24 取得用户 ByUserID
	public List<ModelUser> GetUserListByUserID(String pUserID[]) {
		List<ModelUser> _List = new ArrayList<ModelUser>();
		for (int i = 0; i < pUserID.length; i++) {
			_List.add(GetModelUserByUserID(Integer.valueOf(pUserID[i])));
		}

		return _List;
	}

	// No.29  是否存在已有用户名
	public boolean IsExistUserByUserName(String pUserName,Integer pUserID)
	{
		String _Condition = " And UserName = '" + pUserName + "'";
		if(pUserID != null)
		{
			_Condition += " And UserID <> " + pUserID;
		}
		List _List = mSQLiteDALUser.GetUser(_Condition);
		if (_List.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// No.29   隐藏用户
	public Boolean HideUserByUserID(int p_UserID)
	{
		String _Condition = " UserID = " + p_UserID;
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("State",0);
		Boolean _Result = mSQLiteDALUser.UpdateUser(_Condition, _ContentValues);

		if(_Result)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public String GetUserNameByUserID(String p_UserID)
	{
		List<ModelUser> _List = GetUserListByUserID(p_UserID.split(","));
		String _Name = "";
		
		for(int i=0;i<_List.size();i++)
		{
			_Name += _List.get(i).getUserName() + ",";
		}
		return _Name;
	}

	

}
