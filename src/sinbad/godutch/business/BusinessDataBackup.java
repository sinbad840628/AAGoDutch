package sinbad.godutch.business;



import java.io.File;
import java.io.IOException;
import java.util.Date;

import sinbad.godutch.business.base.BusinessBase;
import sinbad.godutch.utility.FileUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class BusinessDataBackup extends BusinessBase {

	public BusinessDataBackup(Context p_Context) {
		super(p_Context);
	}

	public boolean DatabaseBackup(Date p_Backup) {
		boolean _Result = false;
		try {
			File _SourceFile = new File("/data/data/" + GetContext().getPackageName() + "/databases/GoDutchDataBase");
			
			if(_SourceFile.exists())
			{
				File _FileDir = new File("/sdcard/GoDutch/DataBaseBak/");
				if (!_FileDir.exists()) {
					_FileDir.mkdirs();
				}
				
				FileUtil.cp("/data/data/" + GetContext().getPackageName() + "/databases/GoDutchDataBase", "/sdcard/GoDutch/DataBaseBak/GoDutchDataBase");
				
			}			
			SaveDatabaseBackupDate(p_Backup.getTime());
			
			_Result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return _Result;
	}
	
	public boolean DatabaseRestore() {
		boolean _Result = false;
		try {
			long _DatabaseBackupDate = LoadDatabaseBackupDate();
			
			if(_DatabaseBackupDate != 0)
			{
				FileUtil.cp("/sdcard/GoDutch/DataBaseBak/GoDutchDataBase", "/data/data/Mobidever.GoDutch/databases/GoDutchDataBase");
			}
			
			_Result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return _Result;
	}
	
	public void SaveDatabaseBackupDate(long _Millise)
	{		
		//		MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中，可以使用MODE_APPEND
		//		MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
		//		MODE_WORLD_READABLE和MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件
		//		MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入
		
		//获取指定Key的SharedPreferences对象
		SharedPreferences _SP = GetContext().getSharedPreferences("DatabaseBackupDate",Context.MODE_PRIVATE);
		//获取编辑
		SharedPreferences.Editor _Editor = _SP.edit();
		//按照指定Key放入数据
		_Editor.putLong("DatabaseBackupDate", _Millise);
		//提交保存数据
		_Editor.commit();
	}
	
	public long LoadDatabaseBackupDate()
	{
		long _DatabaseBackupDate = 0;
		//获取指定Key的SharedPreferences对象
		SharedPreferences _SP = GetContext().getSharedPreferences("DatabaseBackupDate",Context.MODE_PRIVATE);
		//数据为空证明还不存在
		if (_SP != null) {
			//否则就获取指定Key的数据
			_DatabaseBackupDate = _SP.getLong("DatabaseBackupDate", 0);
		}
		
		return _DatabaseBackupDate;
	}
}
