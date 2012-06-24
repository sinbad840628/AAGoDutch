package sinbad.godutch.database.sqlitedal;

import java.util.Date;
import java.util.List;

import sinbad.godutch.R;
import sinbad.godutch.configration.CommonConstants;
import sinbad.godutch.database.base.SQLiteDALBase;
import sinbad.godutch.model.ModelUser;
import sinbad.godutch.utility.DateTools;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * No.21 新建SQLite处理用户 
 * No.21 
 * */
public class SQLiteDALUser extends SQLiteDALBase{

	/* No.21 自动实现的方法  */
	public SQLiteDALUser(Context p_Context) {
		super(p_Context);
	}
	
	//No.21 新建 创建参数的方法
	public ContentValues CreateParms(ModelUser pInfo)
	{
		Log.i("SQLiteDALUser", "SQLiteDALUser CreateParms方法开始使用");
		ContentValues _ContentValues = new ContentValues();		
		_ContentValues.put("UserName", pInfo.getUserName());		
		_ContentValues.put("CreateDate",DateTools.getFormatDateTime(pInfo.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		_ContentValues.put("State",pInfo.getState());
		return _ContentValues;
	}


	//No.21 新建 插入方法	
	public boolean InsertUser(ModelUser pModelUser) {
		Log.i("SQLiteDALUser", "SQLiteDALUser InsertUser方法开始使用");
		ContentValues _ContentValues = CreateParms(pModelUser);
		//No.22  追加逻辑
		long _NewID =  GetDataBase().insert(GetTableNameAndPK()[0], null, _ContentValues);
		pModelUser.setUserID((int)_NewID);
		//插入成功为真，插入为假
		return _NewID > 0;
	}
	
	//No.22 新建 删除用户方法	
	public boolean DeleteUser(String pCondition) {
		Log.i("SQLiteDALUser", "SQLiteDALUser DeleteUser方法开始使用");
		return Delete(GetTableNameAndPK()[0],pCondition);
	}
	
	//No.22 更新特定用户方法
	public boolean UpdateUser(String pCondition,ModelUser pModelUser) {
		Log.i("SQLiteDALUser", "SQLiteDALUser UpdateUser 1 方法开始使用");
		ContentValues _ContentValues = CreateParms(pModelUser);
		return GetDataBase().update(GetTableNameAndPK()[0], _ContentValues, pCondition, null) > 0;
	}
	
	//No.22 更新用户方法
	public boolean UpdateUser(String pCondition,ContentValues pContentValues)
	{
		Log.i("SQLiteDALUser", "SQLiteDALUser UpdateUser 2 方法开始使用");
		return GetDataBase().update("User", pContentValues, pCondition, null) > 0;
	}
	
	//No.22 新建List方法
	public List<ModelUser> GetUser(String pCondition)
	{
		Log.i("SQLiteDALUser", "SQLiteDALUser GetUser方法开始使用");
		String _SqlText = "Select * From User Where 1=1 " + pCondition;
		return GetList(_SqlText);
	}
	
	/* No.21 自动实现的方法  */
	//No.22  追加返回值
	@Override
	protected String[] GetTableNameAndPK() {
		Log.i("SQLiteDALUser", "SQLiteDALUser GetTableNameAndPK方法开始使用");
		return new String[]{"User","UserID"};
	}

	/* No.21 自动实现的方法  */
	//No.23    更新：找到模型用户类实例
	@Override
	protected Object FindModel(Cursor pCursor) {
		Log.i("SQLiteDALUser", "SQLiteDALUser FindModel方法开始使用");
		ModelUser _ModelUser = new ModelUser();		
		//设置用户ID
		_ModelUser.setUserID(pCursor.getInt(pCursor.getColumnIndex("UserID")));
		//设置用户名
		_ModelUser.setUserName(pCursor.getString(pCursor.getColumnIndex("UserName")));
		//设置创建时间
		Date _CreateDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");		
		_ModelUser.setCreateDate(_CreateDate);
		//设置状态
		_ModelUser.setState((pCursor.getInt(pCursor.getColumnIndex("State"))));
		
		return _ModelUser;
	}
	
	//No.23 初始化默认用户
	private void InitDefaultData(SQLiteDatabase pDatabase)
	{
		Log.i("SQLiteDALUser", "SQLiteDALUser 初始化默认用户");
		ModelUser _ModelUser = new ModelUser();
		
		String _UserName[] = GetContext().getResources().getStringArray(R.array.InitDefaultUserName);
		
		for (int i = 0; i < _UserName.length; i++) {
			_ModelUser.setUserName(_UserName[i]);
			//实例化对象
			ContentValues _ContentValues = CreateParms(_ModelUser);
			
			pDatabase.insert(GetTableNameAndPK()[0], null, _ContentValues);
		}
	}

	/* No.21 自动实现的方法  接口为：*/
	//No.21 实现 新建数据库的功能
	public void OnCreate(SQLiteDatabase p_DataBase) {
		Log.i("SQLiteDALUser", "SQLiteDALUser OnCreate方法开始使用");
		StringBuilder s_CreateTableScript = new StringBuilder();		
		s_CreateTableScript.append("Create  TABLE User(");
		s_CreateTableScript.append("	[UserID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("	,[UserName] varchar(10) NOT NULL");
		s_CreateTableScript.append("	,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("	,[State] integer NOT NULL");
		s_CreateTableScript.append("	)");
		Log.v("SQLiteDALUser", "创建数据库" + s_CreateTableScript);
		p_DataBase.execSQL(s_CreateTableScript.toString());		
		InitDefaultData(p_DataBase);
	}
	
	/* No.21 自动实现的方法  */
	public void OnUpgrade(SQLiteDatabase p_DataBase) {
		// TODO Auto-generated method stub
		
	}
	

}
