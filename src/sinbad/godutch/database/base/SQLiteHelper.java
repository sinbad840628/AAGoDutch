package sinbad.godutch.database.base;

import java.util.ArrayList;

import sinbad.godutch.utility.Reflection;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * No.18 新建一个SQLiteHelper
 * */
public class SQLiteHelper extends SQLiteOpenHelper {

	
	//No.18 新建变量
	private static SQLiteDataBaseConfig SQLITE_DATEBASE_CONFIG;
	private static SQLiteHelper INSTANCE;
	private Context mContext;
	private Reflection mReflection;
	
	//No.21 新建两个接口，方便新建用户信息和更新用户信息
	public interface SQLiteDataTable
	{
		public void OnCreate(SQLiteDatabase pDataBase);
		public void OnUpgrade(SQLiteDatabase pDataBase);
	}
	
	
	
	//No.18 新建构造函数  私有
	private SQLiteHelper(Context pContext)
	{
		super(pContext, SQLITE_DATEBASE_CONFIG.GetDataBaseName(), null, SQLITE_DATEBASE_CONFIG.GetVersion());
		mContext = pContext;
		Log.i("SQLiteHelper", "SQLiteHelper(Context pContext) 构造函数");
	}
	
	/* No.18 自动实现函数 */
	public SQLiteHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		Log.i("SQLiteHelper", "SQLiteHelper(Context context, String name, CursorFactory factory,int version) 构造函数");
	}
	
	//No.18 新建一个单例实例
	public static SQLiteHelper GetInstance(Context pContext)
	{
		Log.i("SQLiteHelper", "GetInstance 取得一个实例");
		if (INSTANCE == null) {
			Log.i("SQLiteHelper", "如果没有，那么就新建一个");
			SQLITE_DATEBASE_CONFIG = SQLITE_DATEBASE_CONFIG.GetInstance(pContext);
			INSTANCE = new SQLiteHelper(pContext);
		}
		
		return INSTANCE;
	}
	
	/* No.18 自动调用函数 */
	@Override
	public void onCreate(SQLiteDatabase pDataBase) {
		Log.i("SQLiteHelper", "onCreate 创建函数");
		ArrayList<String> _ArrayList = SQLITE_DATEBASE_CONFIG.GetTables();
		
		//No.17 这时候我们需要反射机制了  我们在运行的时候不知道会有那些对象，新建一个Reflection
		/*_ArrayList.add("");
		return _ArrayList;*/
		
		mReflection = new Reflection();
		
		for(int i=0;i<_ArrayList.size();i++)
		{
			try {
				((SQLiteDataTable)mReflection.
						newInstance(_ArrayList.get(i), new Object[]{mContext}, new Class[]{Context.class})).OnCreate(pDataBase);
				Log.i("SQLiteHelper", "反射生成数据库表");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/* No.18 自动调用函数 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
