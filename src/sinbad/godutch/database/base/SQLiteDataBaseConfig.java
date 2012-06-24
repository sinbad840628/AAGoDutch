package sinbad.godutch.database.base;


import java.util.ArrayList;

import sinbad.godutch.R;
import sinbad.godutch.configration.CommonConstants;
import android.content.Context;
import android.util.Log;


/**
 * No.18 新增数据库配置
 * */
public class SQLiteDataBaseConfig {

	//No.18 初始化变量
	private static final String DATABASE_NAME = "GoDutchDataBase";
	private static final int VERSION = 1;
	private static SQLiteDataBaseConfig INSTANCE;
	private static Context mContext;
	
	//No.18  构造函数
	private SQLiteDataBaseConfig()
	{
		
	}
	
	//No.18 单例模式：实例化
	public static SQLiteDataBaseConfig GetInstance(Context pContext)
	{
		Log.i("SQliteDataBaseConfig", "实例化 SQLiteDataBaseConfig 的实例");
		if (INSTANCE == null) {
			INSTANCE = new SQLiteDataBaseConfig();
			mContext = pContext;
		}
		
		return INSTANCE;
	}
	
	//No.18 调用数据库名
	public String GetDataBaseName()
	{
		Log.i("SQliteDataBaseConfig", " 返回 GetDataBaseName");
		return DATABASE_NAME;
	}
	
	//No.18 调用数据库版本
	public int GetVersion()
	{
		Log.i("SQliteDataBaseConfig", "返回 数据库版本VERSION");
		return VERSION;
	}
	
	//No.18 取得数据库的表名
	public ArrayList<String> GetTables() {
		Log.i("SQliteDataBaseConfig", "获取数据库的表名");
		//新建ArrayList数据，用于取得ArrayList
		ArrayList<String> _ArrayList = new ArrayList<String>();
		
		//No.25 加载配置文件
		//反射加载资源函数，抽取对应的表名
		String[] _SQLiteDALClassName = mContext.getResources().getStringArray(R.array.SQLiteDALClassName);
		
		//加上每一个表名的对应路径名
		String _PackagePath = mContext.getPackageName() + ".database.sqlitedal.";
		
		for (int i = 0; i < _SQLiteDALClassName.length; i++) {
			_ArrayList.add(_PackagePath + _SQLiteDALClassName[i]);
		}
		return _ArrayList;
	}
	
}
