package sinbad.godutch.database.base;

import java.util.ArrayList;
import java.util.List;

import sinbad.godutch.database.base.SQLiteHelper.SQLiteDataTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * No.18 新建一个SQLiteBase类，封装共通函数
 * No.21 追加  implements SQLiteDataTable，因为是抽象类，可以不在本方法内实现
 * */
public abstract class SQLiteDALBase implements SQLiteDataTable{

	//No.18 声明变量
	private Context mContext;
	private SQLiteDatabase mDataBase;
	
	//No.18 构造函数
	public SQLiteDALBase(Context pContext)
	{
		mContext = pContext;
	}
	
	//No.18 取得Context实例
	protected Context GetContext()
	{
		return mContext;
	}
	
	//No.18  取得DataBase实例
	public SQLiteDatabase GetDataBase()
	{
		//Log.i("SQLiteDALBase", "GetDataBase 得到数据库");
		if(mDataBase == null)
		{
			//Log.i("SQLiteDALBase", "GetDataBase 数据库为空，那么进行写数据库的操作");
			mDataBase = SQLiteHelper.GetInstance(mContext).getWritableDatabase();
		}
		
		return mDataBase;
	}
	
	//No.18 开始事务
	public void BeginTransaction()
	{
		Log.i("SQLiteDALBase", "BeginTransaction 开始事务");
		mDataBase.beginTransaction();
	}
	
	//No.18 设置事务成功
	public void SetTransactionSuccessful()
	{
		Log.i("SQLiteDALBase", "SetTransactionSuccessful 设置事务成功");
		mDataBase.setTransactionSuccessful();
	}
	
	//No.18 结束事务
	public void EndTransaction()
	{
		Log.i("SQLiteDALBase", "EndTransaction 结束事务");
		mDataBase.endTransaction();
	}
	
	//No.18 取得数量
	public int GetCount(String pCondition)
	{
		String _String[] = GetTableNameAndPK();
		Cursor _Cursor = ExecSql("Select " + _String[1] + " From " + _String[0] + " Where 1=1 " + pCondition);
		Log.i("SQLiteDALBase", "Select " + _String[1] + " From " + _String[0] + " Where 1=1 " + pCondition);
		int _Count = _Cursor.getCount();
		_Cursor.close();
		return _Count;
	}
	
	//No.18 取得数量
	public int GetCount(String pPK,String pTableName, String pCondition)
	{
		Cursor _Cursor = ExecSql("Select " + pPK + " From " + pTableName + " Where 1=1 " + pCondition);
		Log.i("SQLiteDALBase", "Cursor ： Select " + pPK + " From " + pTableName + " Where 1=1 " + pCondition );
		int _Count = _Cursor.getCount();
		_Cursor.close();
		return _Count;
	}
	
	//No.18  删除数据
	protected Boolean Delete(String pTableName, String pCondition)
	{
		Log.i("SQLiteDALBase", "Delete 删除 " + pTableName + "where 1 = 1 " + pCondition);
		return GetDataBase().delete(pTableName, " 1=1 " + pCondition, null) >= 0;
	}
	
	protected abstract String[] GetTableNameAndPK();
	
	//No.18 得到list
	protected List GetList(String pSqlText)
	{
		Log.i("SQLiteDALBase", "GetList 取得List：" + pSqlText);
		Cursor _Cursor = ExecSql(pSqlText);
		return CursorToList(_Cursor);
	}
	
	protected abstract Object FindModel(Cursor pCursor);
	
	//No.18 得到list
	protected List CursorToList(Cursor pCursor)
	{
		Log.i("SQLiteDALBase", "CursorToList 游标指定List");
		List _List = new ArrayList();
		while(pCursor.moveToNext())
		{
			Object _Object = FindModel(pCursor);
			_List.add(_Object);
		}
		pCursor.close();
		return _List;
	}
	
	//No.18 执行Query
	public Cursor ExecSql(String pSqlText)
	{
		Log.i("SQLiteDALBase", "ExecSql 执行SQL：" + pSqlText );
		return GetDataBase().rawQuery(pSqlText, null);
	}
}
