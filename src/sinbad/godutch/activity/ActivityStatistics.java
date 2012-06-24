package sinbad.godutch.activity;

import sinbad.godutch.R;
import sinbad.godutch.activity.base.ActivityFrame;
import sinbad.godutch.adapter.AdapterAccountBookSelect;
import sinbad.godutch.business.BusinessAccountBook;
import sinbad.godutch.business.BusinessStatistics;
import sinbad.godutch.controls.SlideMenuItem;
import sinbad.godutch.controls.SlideMenuView.OnSlideMenuListener;
import sinbad.godutch.model.ModelAccountBook;
import android.R.string;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityStatistics extends ActivityFrame implements OnSlideMenuListener {

	private TextView tvStatisticsResult;
	private BusinessStatistics mBusinessStatistics;
	private ModelAccountBook mAccountBook;
	private BusinessAccountBook mBusinessAccountBook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ActivityStatistics", "onCreate 创建");
		AppendMainBody(R.layout.statistics);
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		SetTitle();
		CreateSlideMenu(R.array.SlideMenuStatistics);
	}
	
	private void SetTitle() {

		Log.i("ActivityStatistics", "SetTitle 创建");
		String _Titel = getString(R.string.ActivityTitleStatistics, new Object[]{mAccountBook.GetAccountBookName()});
		SetTopBarTitle(_Titel);
	}

	protected void InitView() {
		Log.i("ActivityStatistics", "InitView 创建View组件");
		tvStatisticsResult = (TextView) findViewById(R.id.tvStatisticsResult);
	}

	protected void InitListeners() {
	}
	
	protected void InitVariable() {
		Log.i("ActivityStatistics", "InitVariable 创建变量");
		mBusinessStatistics = new BusinessStatistics(ActivityStatistics.this);
		mBusinessAccountBook = new BusinessAccountBook(ActivityStatistics.this);
		mAccountBook = mBusinessAccountBook.GetDefaultModelAccountBook();
	}

	protected void BindData()
	{
		Log.i("ActivityStatistics", "BindData 绑定数据");
		ShowProgressDialog(R.string.StatisticsProgressDialogTitle, R.string.StatisticsProgressDialogWaiting);
		new BindDataThread().start();
	}
	
	private class BindDataThread extends Thread
	{
		@Override
		public void run() {
			String _Result = mBusinessStatistics.GetPayoutUserIDByAccountBookID(mAccountBook.GetAccountBookID());
			Message _Message = new Message();
			_Message.obj = _Result;
			_Message.what = 1;
			mHandler.sendMessage(_Message);
		}
	}
	
	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			Log.i("ActivityStatistics", "Handler  handleMessage 处理信息");
			switch (msg.what) {
			case 1:
				String _Result = (String) msg.obj;
				tvStatisticsResult.setText(_Result);
				DismissProgressDialog();
				break;
			default:
				break;
			}
		}
	};

	public void onSlideMenuItemClick(View p_View, SlideMenuItem p_SlideMenuItem) {
		Log.i("ActivityStatistics", "onSlideMenuItemClick");
		SlideMenuToggle();
		if (p_SlideMenuItem.getItemID() == 0) {
			ShowAccountBookSelectDialog();
		}
		if(p_SlideMenuItem.getItemID() == 1) {
			ExportData();
		}
	}
	
	private void ShowAccountBookSelectDialog()
	{
		Log.i("ActivityStatistics", "ShowAccountBookSelectDialog");
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
		ListView _ListView = (ListView)_View.findViewById(R.id.ListViewSelect);
		AdapterAccountBookSelect _AdapterAccountBookSelect = new AdapterAccountBookSelect(this);
		_ListView.setAdapter(_AdapterAccountBookSelect);

		_Builder.setTitle(R.string.ButtonTextSelectAccountBook);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ListView.setOnItemClickListener(new OnAccountBookItemClickListener(_AlertDialog));
	}
	
	private class OnAccountBookItemClickListener implements AdapterView.OnItemClickListener
	{
		private AlertDialog m_AlertDialog;
		public OnAccountBookItemClickListener(AlertDialog p_AlertDialog)
		{
			Log.i("ActivityStatistics", "OnAccountBookItemClickListener OnAccountBookItemClickListener");
			m_AlertDialog = p_AlertDialog;
		}

		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position,long arg3) {
			Log.i("ActivityStatistics", "OnAccountBookItemClickListener onItemClick");
			mAccountBook = (ModelAccountBook)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
			BindData();
			m_AlertDialog.dismiss();
		}
	}
	
	
	private void ExportData() {
		Log.i("ActivityStatistics", "ExportData 导出数据");
		String _Result = "";
		try {
			_Result = mBusinessStatistics.ExportStatistics(mAccountBook.GetAccountBookID());
		} catch (Exception e) {
			_Result = "导出失败！";
		}
		Toast.makeText(this, _Result, Toast.LENGTH_LONG).show();
	}
}
