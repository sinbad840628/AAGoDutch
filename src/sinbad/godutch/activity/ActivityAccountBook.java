package sinbad.godutch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import sinbad.godutch.R;
import sinbad.godutch.activity.base.ActivityFrame;
import sinbad.godutch.adapter.AdapterAccountBook;
import sinbad.godutch.business.BusinessAccountBook;
import sinbad.godutch.controls.SlideMenuItem;
import sinbad.godutch.controls.SlideMenuView.OnSlideMenuListener;
import sinbad.godutch.model.ModelAccountBook;
import sinbad.godutch.utility.RegexTools;

public class ActivityAccountBook extends ActivityFrame implements OnSlideMenuListener {

	//声明成员变量
	private ListView lvAccountBookList;	
	private AdapterAccountBook mAdapterAccountBook;
	private BusinessAccountBook mBusinessAccountBook;
	private ModelAccountBook mSelectModlAccountBook;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Log.i("ActivityAccountBook", "调用 onCreate函数");
		AppendMainBody(R.layout.account_book);
		Log.i("ActivityAccountBook", "载入account_book界面布局");
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        CreateSlideMenu(R.array.SlideMenuAccountBook);
	}
	
	public void InitVariable()
	{
		Log.i("ActivityAccountBook", "初始化BusinessAccountBook变量");
		mBusinessAccountBook = new BusinessAccountBook(this);
	}
	
	public void InitView()
    {
		Log.i("ActivityAccountBook", "初始化AccountBookList的ListView");
    	lvAccountBookList = (ListView) findViewById(R.id.lvAccountBookList);
    }
	
	public void InitListeners()
    {
		Log.i("ActivityAccountBook", "初始化registerForContextMenu的监听器");
    	registerForContextMenu(lvAccountBookList);
    }
	
	public void BindData()
    {
		Log.i("ActivityAccountBook", "绑定数据");
    	mAdapterAccountBook = new AdapterAccountBook(this);
    	lvAccountBookList.setAdapter(mAdapterAccountBook);
    	SetTitle();
    }

	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		Log.i("ActivityAccountBook", "当用户点击Slide滑块的时候，调用SlideMenuToggle()方法");
		SlideMenuToggle();
		if (pSlideMenuItem.getItemID() == 0) {
			ShowAccountBookAddOrEditDialog(null);
		}

	}
	
	private void SetTitle() {
		Log.i("ActivityAccountBook", "设置Title");
		int _Count = mAdapterAccountBook.getCount();
		String _Titel = getString(R.string.ActivityTitleAccountBook, new Object[]{_Count});
		SetTopBarTitle(_Titel);
	}
	
	private void ShowAccountBookAddOrEditDialog(ModelAccountBook pModelAccountBook)
	{
		Log.i("ActivityAccountBook", "显示添加账本，还是编辑账本的对话框");
		View _View = GetLayouInflater().inflate(R.layout.account_book_add_or_edit, null);
		
		EditText _etAccountBookName = (EditText) _View.findViewById(R.id.etAccountBookName);
		CheckBox _chkIsDefault = (CheckBox)_View.findViewById(R.id.chkIsDefault);
		
		if (pModelAccountBook != null) {
			Log.i("ActivityAccountBook", "如果AccountBook存在，那么设置AccountBookName");
			_etAccountBookName.setText(pModelAccountBook.GetAccountBookName());
		}
		
		String _Title;
		
		if(pModelAccountBook == null)
		{
			Log.i("ActivityAccountBook", "如果AccountBook为空，那么添加AccountBook标题");
			_Title = getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.TitleAdd)});
		}
		else {
			Log.i("ActivityAccountBook", "如果AccountBook不为空，那么编辑AccountBook标题");
			_Title = getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.TitleEdit)});
		}
		Log.i("ActivityAccountBook", "弹出弹出框");
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		_Builder.setTitle(_Title)
				.setView(_View)
				.setIcon(R.drawable.account_book_big_icon)
				.setNeutralButton(getString(R.string.ButtonTextSave), new OnAddOrEditAccountBookListener(pModelAccountBook,_etAccountBookName,_chkIsDefault,true))
				.setNegativeButton(getString(R.string.ButtonTextCancel), new OnAddOrEditAccountBookListener(null,null,null,false))
				.show();
	}
	
	private class OnAddOrEditAccountBookListener implements DialogInterface.OnClickListener
	{
		private ModelAccountBook mModelAccountBook;
		private EditText etAccountBookName;
		private boolean mIsSaveButton;
		private CheckBox chkIsDefault;
		
		public OnAddOrEditAccountBookListener(ModelAccountBook pModelAccountBook,EditText petAccountBookName,CheckBox pchkIsDefault,Boolean pIsSaveButton)
		{
			mModelAccountBook = pModelAccountBook;
			etAccountBookName = petAccountBookName;
			mIsSaveButton = pIsSaveButton;
			chkIsDefault = pchkIsDefault;
		}
		
		public void onClick(DialogInterface dialog, int which) {
			if(mIsSaveButton == false)
			{
				SetAlertDialogIsClose(dialog, true);
				return;
			}
			
			if (mModelAccountBook == null) {
				mModelAccountBook = new ModelAccountBook();
			}
			
			String _AccountBookName = etAccountBookName.getText().toString().trim();
			
			boolean _CheckResult = RegexTools.IsChineseEnglishNum(_AccountBookName);
			
			if (!_CheckResult) {
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextChineseEnglishNum,new Object[]{etAccountBookName.getHint()}), SHOW_TIME).show();
				SetAlertDialogIsClose(dialog, false);
				return;
			}
			else {
				SetAlertDialogIsClose(dialog, true);
			}
			
			_CheckResult = mBusinessAccountBook.IsExistAccountBookByAccountBookName(_AccountBookName, mModelAccountBook.GetAccountBookID());
			
			if (_CheckResult) {
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextAccountBookExist), SHOW_TIME).show();
				
				SetAlertDialogIsClose(dialog, false);
				return;
			}
			else {
				SetAlertDialogIsClose(dialog, true);
			}
			
			mModelAccountBook.SetAccountBookName(etAccountBookName.getText().toString());
			
			mModelAccountBook.SetAccountBookName(String.valueOf(etAccountBookName.getText().toString().trim()));
			if(chkIsDefault.isChecked())
			{
				mModelAccountBook.SetIsDefault(1);
			}
			else {
				mModelAccountBook.SetIsDefault(0);
			}
			
			if(mModelAccountBook.GetAccountBookID() > 0)
			{
				mModelAccountBook.SetIsDefault(1);
			}
			
			boolean _Result = false;
			
			if (mModelAccountBook.GetAccountBookID() == 0) {
				_Result = mBusinessAccountBook.InsertAccountBook(mModelAccountBook);
			}
			else {
				_Result = mBusinessAccountBook.UpdateAccountBookByAccountBookID(mModelAccountBook);
			}
			
			if (_Result) {
				BindData();
			}
			else {
				Toast.makeText(ActivityAccountBook.this, getString(R.string.TipsAddFail), 1).show();
			}
		}
		
	}
	
	private void Delete() {
		Log.i("ActivityAccountBook", "调用Delete 方法 ");
		String _Message = getString(R.string.DialogMessageAccountBookDelete,new Object[]{mSelectModlAccountBook.GetAccountBookName()});
		ShowAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener());
	}
	
	private class OnDeleteClickListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialog, int which) {
			boolean _Result = mBusinessAccountBook.DeleteAccountBookByAccountBookID(mSelectModlAccountBook.GetAccountBookID());
			
			if (_Result == true) {
				BindData();
			}
		}
	}

}
