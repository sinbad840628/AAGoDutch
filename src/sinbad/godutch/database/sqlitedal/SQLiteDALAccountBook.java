package sinbad.godutch.database.sqlitedal;

import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import sinbad.godutch.R;
import sinbad.godutch.database.base.SQLiteDALBase;
import sinbad.godutch.model.ModelAccountBook;
import sinbad.godutch.utility.DateTools;

public class SQLiteDALAccountBook extends SQLiteDALBase {

	public SQLiteDALAccountBook(Context p_Context) {
		super(p_Context);
		Log.i("SQLiteDALAccountBook", "SQLiteDALAccountBook 构造函数");
	}
	
	public boolean InsertAccountBook(ModelAccountBook pBook)
	{
		Log.i("SQLiteDALAccountBook", "InsertAccountBook 插入AccountBook");
		ContentValues _ContentValues = CreatParms(pBook);
		Long pNewID = GetDataBase().insert("AccountBook", null, _ContentValues);
		pBook.SetAccountBookID(pNewID.intValue());
		return pNewID > 0;
	}
	
	public boolean DeleteAccountBook(String pCondition)
	{
		Log.i("SQLiteDALAccountBook", "DeleteAccountBook 删除AccountBook");
		return Delete(GetTableNameAndPK()[0], pCondition);
	}
	
	public boolean UpdateAccountBook(String p_Condition, ModelAccountBook p_Info)
	{
		Log.i("SQLiteDALAccountBook", "UpdateAccountBook(String p_Condition, ModelAccountBook p_Info) 更新AccountBook");
		ContentValues _ContentValues = CreatParms(p_Info);
		return GetDataBase().update("AccountBook", _ContentValues, p_Condition, null) > 0;
	}
	
	public boolean UpdateAccountBook(String p_Condition,ContentValues p_ContentValues)
	{
		Log.i("SQLiteDALAccountBook", "UpdateAccountBook(String p_Condition,ContentValues p_ContentValues) 更新AccountBook");
		return GetDataBase().update("AccountBook", p_ContentValues, p_Condition, null) > 0;
	}
	
	public List<ModelAccountBook> GetAccountBook(String pCondition)
	{
		Log.i("SQLiteDALAccountBook", "GetAccountBook 得到AccountBook");
		String _SqlText = "Select * From AccountBook Where  1=1 " + pCondition;
		return GetList(_SqlText);
	}
	
	protected ModelAccountBook FindModel(Cursor pCursor)
	{
		Log.i("SQLiteDALAccountBook", "FindModel 找到ModelAccountBook并且设置各种参数");
		ModelAccountBook _ModelAccountBook = new ModelAccountBook();
		_ModelAccountBook.SetAccountBookID(pCursor.getInt(pCursor.getColumnIndex("AccountBookID")));
		_ModelAccountBook.SetAccountBookName(pCursor.getString(pCursor.getColumnIndex("AccountBookName")));
		Date _CreateDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");	
		_ModelAccountBook.SetCreateDate(_CreateDate);
		_ModelAccountBook.SetIsDefault(pCursor.getInt(pCursor.getColumnIndex("IsDefault")));
		_ModelAccountBook.SetState((pCursor.getInt(pCursor.getColumnIndex("State"))));		
		return _ModelAccountBook;
		
	}
	
	private void InitDefaultData(SQLiteDatabase pDataBase)
	{
		Log.i("SQLiteDALAccountBook", "OnCreate 方法调用，初始化默认数据");
		String _AccountBook[] = GetContext().getResources().getStringArray(R.array.InitDefaultDataAccountBookName);
		ModelAccountBook _ModelAccountBook = new ModelAccountBook();
		_ModelAccountBook.SetAccountBookName(_AccountBook[0]);
		_ModelAccountBook.SetIsDefault(1);
	
		ContentValues _ContentValues = CreatParms(_ModelAccountBook);
		pDataBase.insert("AccountBook", null, _ContentValues);
	}

	public void OnCreate(SQLiteDatabase pDataBase) {
		Log.i("SQLiteDALAccountBook", "OnCreate 方法调用，创建AccountBook 表");
		StringBuilder s_CreateTableScript = new StringBuilder();		
		s_CreateTableScript.append("Create  TABLE AccountBook(");
		s_CreateTableScript.append("	[AccountBookID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("	,[AccountBookName] varchar(20) NOT NULL");
		s_CreateTableScript.append("	,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("	,[State] integer NOT NULL");
		s_CreateTableScript.append("	,[IsDefault] integer NOT NULL");
		s_CreateTableScript.append("	)");
		
		pDataBase.execSQL(s_CreateTableScript.toString());
		
		InitDefaultData(pDataBase);

	}

	public void OnUpgrade(SQLiteDatabase pDataBase) {
		// TODO Auto-generated method stub
		Log.i("SQLiteDALAccountBook", "OnCreate 方法调用，OnUpgrade 更新数据");
	}

	@Override
	protected String[] GetTableNameAndPK() {
		Log.i("SQLiteDALAccountBook", "OnCreate 方法调用，得到表名和主键");
		return new String[]{"AccountBook","AccountBookID"};
	}

	
	public ContentValues CreatParms(ModelAccountBook p_Info) {
		Log.i("SQLiteDALAccountBook", "CreatParms 创建参数");
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("AccountBookName", p_Info.GetAccountBookName());
		_ContentValues.put("CreateDate",DateTools.getFormatDateTime(p_Info.GetCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		_ContentValues.put("State",p_Info.GetState());
		_ContentValues.put("IsDefault",p_Info.GetIsDefault());
		return _ContentValues;
	}

}
