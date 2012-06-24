package sinbad.godutch.activity;


import java.util.Date;

import sinbad.godutch.R;
import sinbad.godutch.activity.base.ActivityFrame;
import sinbad.godutch.adapter.AdapterAppGrid;
import sinbad.godutch.business.BusinessDataBackup;
import sinbad.godutch.controls.SlideMenuItem;
import sinbad.godutch.controls.SlideMenuView.OnSlideMenuListener;
import sinbad.godutch.service.ServiceDatabaseBackup;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


/**
 * No.7 将继承类更新为自定义的ActivityFrame类
 * 这样做的好处就是：我自定义Frame架构的格式和类型。
 * 如果Frame类型有变化，直接在父类Frame中
 * No.17 追加 implements 接口
 * */
public class ActivityMain extends ActivityFrame implements OnSlideMenuListener,OnClickListener{
	    
	private GridView gvAppGrid;
	public BusinessDataBackup mBusinessDataBackup;
	private AdapterAppGrid mAdapterAppGrid;
	private AlertDialog mDatabaseBackupDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                                
        Log.i("ActivityMain", "onCreate 创建");
        AppendMainBody(R.layout.main_body);
        Log.i("ActivityMain", "main_body 布局界面生成");
        InitVariable();
        InitView();
        InitListeners();        
        BindData();
        CreateSlideMenu(R.array.SlideMenuActivityMain);
        StartService();
    }
    
    private void StartService() {
		Intent _Intent = new Intent(this, ServiceDatabaseBackup.class);
		startService(_Intent);
	}    
    
    //No.10 变量实例化
    public void InitVariable()
    {
    	Log.i("ActivityMain", "InitVariable 初始化变量");
    	mAdapterAppGrid = new AdapterAppGrid(this);
    	mBusinessDataBackup = new BusinessDataBackup(this);
    }
    
    //No.10  初始化View
    public void InitView()
    {
    	Log.i("ActivityMain", "InitView 初始化View组件");
    	gvAppGrid = (GridView) findViewById(R.id.gvAppGrid);
    }
    
    public void InitListeners()
    {
    	Log.i("ActivityMain", "InitListeners 初始化监听器");
    	gvAppGrid.setOnItemClickListener(new onAppGridItemClickListener());
    }
    
    private class onAppGridItemClickListener implements OnItemClickListener
    {
		public void onItemClick(AdapterView pAdapter, View pView, int pPosition,long arg3) {
			String _MenuName = (String)pAdapter.getAdapter().getItem(pPosition);
			if(_MenuName.equals(getString(R.string.appGridTextUserManage)))
			{
				OpenActivity(ActivityUser.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextAccountBookManage)))
			{
				Log.i("ActivityMain", "点击账本管理图标，OpenActivity");
				OpenActivity(ActivityAccountBook.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextCategoryManage)))
			{
				Log.i("ActivityMain", "点击类别管理图标，ActivityCategory");
				OpenActivity(ActivityCategory.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextPayoutAdd)))
			{
				Log.i("ActivityMain", "点击消费记录图标，ActivityPayoutAddOrEdit");
				OpenActivity(ActivityPayoutAddOrEdit.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextPayoutManage)))
			{
				Log.i("ActivityMain", "点击查询消费图标，ActivityPayout");
				OpenActivity(ActivityPayout.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextStatisticsManage)))
			{
				Log.i("ActivityMain", "点击统计管理图标，ActivityStatistics");
				OpenActivity(ActivityStatistics.class);
				return;
			}
		}
	}
    
    //No. 10 绑定数据
    public void BindData()
    {
    	Log.i("ActivityMain", "BindData 方法绑定数据");
    	gvAppGrid.setAdapter(mAdapterAppGrid);
    }
    
    public void onClick(View v) {
    	Log.i("ActivityMain", "onClick 点击事件");
		switch (v.getId()) {
		case R.id.btnDatabaseBackup:
			DatabaseBackup();
			break;
		case R.id.btnDatabaseRestore:
			DatabaseRestore();
			break;
		default:
			break;
		}		
	}

    //No.17 新追加一个接口方法
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {	
		Log.i("ActivityMain", "onSlideMenuItemClick 点击事件");
		SlideMenuToggle();
		if (pSlideMenuItem.getItemID() == 0) {
			ShowDatabaseBackupDialog();
		}
	}
	
	private void ShowDatabaseBackupDialog()
	{
		Log.i("ActivityMain", "ShowDatabaseBackupDialog 显示数据库备份对话框");
		LayoutInflater _LayoutInflater = LayoutInflater.from(this);
		
		View _View = _LayoutInflater.inflate(R.layout.database_backup, null);
		
		Button _btnDatabaseBackup = (Button)_View.findViewById(R.id.btnDatabaseBackup);
		Button _btnDatabaseRestore = (Button)_View.findViewById(R.id.btnDatabaseRestore);
		
		_btnDatabaseBackup.setOnClickListener(this);
		_btnDatabaseRestore.setOnClickListener(this);
		
		String _Title = getString(R.string.DialogTitleDatabaseBackup);
		
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		_Builder.setTitle(_Title);
		_Builder.setView(_View);
		_Builder.setIcon(R.drawable.database_backup);
		_Builder.setNegativeButton(getString(R.string.ButtonTextBack), null);
		mDatabaseBackupDialog = _Builder.show();
	}
	
	private void DatabaseBackup()
	{
		Log.i("ActivityMain", "DatabaseBackup 数据备份");
		if(mBusinessDataBackup.DatabaseBackup(new Date()))
		{
			ShowMsg(R.string.DialogMessageDatabaseBackupSucceed);
		}
		else {
			ShowMsg(R.string.DialogMessageDatabaseBackupFail);
		}
		
		mDatabaseBackupDialog.dismiss();
	}
	
	private void DatabaseRestore()
	{
		Log.i("ActivityMain", "DatabaseRestore 数据还原");
		if(mBusinessDataBackup.DatabaseRestore())
		{
			ShowMsg(R.string.DialogMessageDatabaseRestoreSucceed);
		}
		else {
			ShowMsg(R.string.DialogMessageDatabaseRestoreFail);
		}
		
		mDatabaseBackupDialog.dismiss();
	}

   
}