package sinbad.godutch.database.sqlitedal;

import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import sinbad.godutch.R;
import sinbad.godutch.database.base.SQLiteDALBase;
import sinbad.godutch.model.ModelCategory;
import sinbad.godutch.utility.DateTools;

public class SQLiteDALCategory extends SQLiteDALBase {

	public SQLiteDALCategory(Context p_Context) {
		super(p_Context);
		// TODO Auto-generated constructor stub
	}
	
	public ContentValues CreatParms(ModelCategory p_Info) {
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("CategoryName", p_Info.GetCategoryName());
		_ContentValues.put("TypeFlag", p_Info.GetTypeFlag());
		_ContentValues.put("ParentID", p_Info.GetParentID());
		_ContentValues.put("Path", p_Info.GetPath());
		_ContentValues.put("CreateDate",DateTools.getFormatDateTime(p_Info.GetCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		_ContentValues.put("State",p_Info.GetState());
		return _ContentValues;
	}
	
	public boolean InsertCategory(ModelCategory pInfo) {
		ContentValues _ContentValues = CreatParms(pInfo);
		Long p_NewID = GetDataBase().insert("Category", null, _ContentValues);
		pInfo.SetCategoryID(p_NewID.intValue());
		return p_NewID > 0;
	}
	
	public Boolean DeleteCategory(String p_Condition)
	{
		return Delete(GetTableNameAndPK()[0], p_Condition);
	}
	
	public Boolean UpdateCategory(String pCondition, ModelCategory pInfo)
	{
		ContentValues _ContentValues = CreatParms(pInfo);
		return GetDataBase().update("Category", _ContentValues, pCondition, null) > 0;
	}
	
	public Boolean UpdateCategory(String pCondition,ContentValues pContentValues)
	{
		return GetDataBase().update("Category", pContentValues, pCondition, null) > 0;
	}
	
	public List<ModelCategory> GetCategory(String p_Condition)
	{
		String _SqlText = "Select * From Category Where  1=1 " + p_Condition;
		return GetList(_SqlText);
	}

	protected ModelCategory FindModel(Cursor pCursor)
	{
		ModelCategory _ModelCategory = new ModelCategory();
		_ModelCategory.SetCategoryID(pCursor.getInt(pCursor.getColumnIndex("CategoryID")));
		_ModelCategory.SetCategoryName(pCursor.getString(pCursor.getColumnIndex("CategoryName")));
		_ModelCategory.SetTypeFlag(pCursor.getString(pCursor.getColumnIndex("TypeFlag")));
		_ModelCategory.SetParentID(pCursor.getInt(pCursor.getColumnIndex("ParentID")));
		_ModelCategory.SetPath(pCursor.getString(pCursor.getColumnIndex("Path")));
		Date _CreateDate = DateTools.getDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");	
		_ModelCategory.SetCreateDate(_CreateDate);
		_ModelCategory.SetState((pCursor.getInt(pCursor.getColumnIndex("State"))));
		
		return _ModelCategory;
	}

	private void InitDefaultData(SQLiteDatabase p_DataBase)
	{
		ModelCategory _ModelCategory = new ModelCategory();
		
		_ModelCategory.SetTypeFlag(GetContext().getString((R.string.PayoutTypeFlag)));
		_ModelCategory.SetPath("");
		_ModelCategory.SetParentID(0);
		String _InitDefaultCategoryNameArr[] = GetContext().getResources().getStringArray(R.array.InitDefaultCategoryName);
		for(int i=0;i<_InitDefaultCategoryNameArr.length;i++)
		{
			_ModelCategory.SetCategoryName(_InitDefaultCategoryNameArr[i]);
			
			ContentValues _ContentValues = CreatParms(_ModelCategory);
			Long _NewID = p_DataBase.insert("Category", null, _ContentValues);
			
			_ModelCategory.SetPath(_NewID.intValue() + ".");
			_ContentValues = CreatParms(_ModelCategory);
			p_DataBase.update("Category", _ContentValues, " CategoryID = " + _NewID.intValue(), null);
		}
	}
	
	public void OnCreate(SQLiteDatabase pDataBase) {
		// TODO Auto-generated method stub
		StringBuilder s_CreateTableScript = new StringBuilder();
		
		s_CreateTableScript.append("	Create  TABLE Category(");
		s_CreateTableScript.append("		[CategoryID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		s_CreateTableScript.append("		,[CategoryName] varchar(20) NOT NULL");
		s_CreateTableScript.append("		,[TypeFlag] varchar(20) NOT NULL");
		s_CreateTableScript.append("		,[ParentID] integer NOT NULL");
		s_CreateTableScript.append("		,[Path] text NOT NULL");
		s_CreateTableScript.append("		,[CreateDate] datetime NOT NULL");
		s_CreateTableScript.append("		,[State] integer NOT NULL");
		s_CreateTableScript.append("		)");
		
		pDataBase.execSQL(s_CreateTableScript.toString());
		InitDefaultData(pDataBase);

	}
	
	
	public void OnUpgrade(SQLiteDatabase pDataBase) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String[] GetTableNameAndPK() {
		// TODO Auto-generated method stub
		return new String[]{"Category","CategoryID"};
	}


}
