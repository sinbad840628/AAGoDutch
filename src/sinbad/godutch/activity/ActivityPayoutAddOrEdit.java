package sinbad.godutch.activity;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sinbad.godutch.R;
import sinbad.godutch.activity.base.ActivityFrame;
import sinbad.godutch.adapter.AdapterAccountBookSelect;
import sinbad.godutch.adapter.AdapterCategory;
import sinbad.godutch.adapter.AdapterUser;
import sinbad.godutch.business.BusinessAccountBook;
import sinbad.godutch.business.BusinessCategory;
import sinbad.godutch.business.BusinessPayout;
import sinbad.godutch.business.BusinessUser;
import sinbad.godutch.controls.NumberDialog;
import sinbad.godutch.controls.NumberDialog.OnNumberDialogListener;
import sinbad.godutch.model.ModelAccountBook;
import sinbad.godutch.model.ModelCategory;
import sinbad.godutch.model.ModelPayout;
import sinbad.godutch.model.ModelUser;
import sinbad.godutch.utility.DateTools;
import sinbad.godutch.utility.RegexTools;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ActivityPayoutAddOrEdit extends ActivityFrame implements android.view.View.OnClickListener,OnNumberDialogListener
{
	
	private Button btnSave;
	private Button btnCancel;
	
	private ModelPayout mModelPayout;
	private BusinessPayout mBusinessPayout;
	private Integer mAccountBookID;
	private Integer mCategoryID;
	private String mPayoutUserID;
	private String mPayoutTypeArray[];
		
	private EditText etAccountBookName;
	private EditText etAmount;
	private AutoCompleteTextView actvCategoryName;
	private EditText etPayoutDate;
	private EditText etPayoutType;
	private EditText etPayoutUser;
	private EditText etComment;
	private Button btnSelectCategory;
	private Button btnSelectUser;
	private Button btnSelectAccountBook;
	private Button btnSelectAmount;
	private Button btnSelectPayoutDate;
	private Button btnSelectPayoutType;
	
	private BusinessAccountBook mBusinessAccountBook;
	private BusinessCategory mBusinessCategory;
	private ModelAccountBook mModelAccountBook;
	private BusinessUser mBusinessUser;
	private List<RelativeLayout> mItemColor;
	private List<ModelUser> mUserSelectedList;

	public void onClick(View v) {
		Log.i("ActivityPayoutAddOrEdit", "onClick");
		int _ID = v.getId();
		switch (_ID) {
		case R.id.btnSelectAccountBook:
			ShowAccountBookSelectDialog();
			break;
		case R.id.btnSelectAmount:
			(new NumberDialog(this)).show();
			break;
		case R.id.btnSelectCategory:
			ShowCategorySelectDialog();
			break;
		case R.id.btnSelectPayoutDate:
			Calendar _Calendar = Calendar.getInstance();
			ShowDateSelectDialog(_Calendar.get(Calendar.YEAR), _Calendar.get(Calendar.MONTH), _Calendar.get(Calendar.DATE));
			break;
		case R.id.btnSelectPayoutType:
			ShowPayoutTypeSelectDialog();
			break;
		case R.id.btnSelectUser:
			ShowUserSelectDialog(etPayoutType.getText().toString());
			break;
		case R.id.btnSave:
			AddOrEditPayout();
			break;
		case R.id.btnCancel:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ActivityPayoutAddOrEdit", "onCreate 创建");
		AppendMainBody(R.layout.payout_add_or_edit);
		Log.i("ActivityPayoutAddOrEdit", "payout_add_or_edit 界面布局完成");
		RemoveBottomBox();
		InitView();
		InitVariable();
		BindData();
		SetTitle();
		InitListeners();
	}
	
	private void SetTitle() {
		Log.i("ActivityPayoutAddOrEdit", "SetTitle 设置Title");
		String _Titel;
		if(mModelPayout == null)
		{
			_Titel = getString(R.string.ActivityTitlePayoutAddOrEdit, new Object[]{getString(R.string.TitleAdd)});
		}
		else {
			_Titel = getString(R.string.ActivityTitlePayoutAddOrEdit, new Object[]{getString(R.string.TitleEdit)});
			InitData(mModelPayout);
		}
		
		SetTopBarTitle(_Titel);
	}

	protected void InitView() {
		Log.i("ActivityPayoutAddOrEdit", "InitView 初始化View组件");
		btnSave = (Button)findViewById(R.id.btnSave);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnSelectAccountBook = (Button)findViewById(R.id.btnSelectAccountBook);
		btnSelectAmount = (Button)findViewById(R.id.btnSelectAmount);
		btnSelectCategory = (Button)findViewById(R.id.btnSelectCategory);
		btnSelectPayoutDate = (Button)findViewById(R.id.btnSelectPayoutDate);
		btnSelectPayoutType = (Button)findViewById(R.id.btnSelectPayoutType);
		btnSelectUser = (Button)findViewById(R.id.btnSelectUser);
		etAccountBookName = (EditText) findViewById(R.id.etAccountBookName);
		etPayoutDate = (EditText) findViewById(R.id.etPayoutDate);
		etPayoutType = (EditText) findViewById(R.id.etPayoutType);
		etAmount = (EditText) findViewById(R.id.etAmount);
		etAccountBookName = (EditText) findViewById(R.id.etAccountBookName);
		actvCategoryName = (AutoCompleteTextView) findViewById(R.id.actvCategoryName);
		etPayoutUser = (EditText) findViewById(R.id.etPayoutUser);
		etComment = (EditText) findViewById(R.id.etComment);
	}

	protected void InitListeners() {
		Log.i("ActivityPayoutAddOrEdit", "InitListeners 初始化监听器");
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnSelectAmount.setOnClickListener(this);
		btnSelectCategory.setOnClickListener(this);
		btnSelectPayoutDate.setOnClickListener(this);
		btnSelectPayoutType.setOnClickListener(this);
		btnSelectUser.setOnClickListener(this);
		actvCategoryName.setOnItemClickListener(new OnAutoCompleteTextViewItemClickListener());
		btnSelectAccountBook.setOnClickListener(this);
	}
	

	protected void InitVariable() {
		Log.i("ActivityPayoutAddOrEdit", "InitVariable 初始化变量");
		mBusinessPayout = new BusinessPayout(this);
		mModelPayout = (ModelPayout) getIntent().getSerializableExtra("ModelPayout");
		mBusinessAccountBook = new BusinessAccountBook(this);
		mBusinessCategory = new BusinessCategory(this);
		mModelAccountBook = mBusinessAccountBook.GetDefaultModelAccountBook();
		mBusinessUser = new BusinessUser(this);
	}

	protected void BindData()
	{
		Log.i("ActivityPayoutAddOrEdit", "BindData 绑定数据");
		mAccountBookID = mModelAccountBook.GetAccountBookID();
		etAccountBookName.setText(mModelAccountBook.GetAccountBookName());
		actvCategoryName.setAdapter(mBusinessCategory.GetAllCategoryArrayAdapter());
		etPayoutDate.setText(DateTools.getFormatDateTime(new Date(),"yyyy-MM-dd"));
		mPayoutTypeArray = getResources().getStringArray(R.array.PayoutType);
		etPayoutType.setText(mPayoutTypeArray[0]);
	}
	
	private void InitData(ModelPayout p_ModelPayout)
	{
		Log.i("ActivityPayoutAddOrEdit", "InitData 初始化数据");
		etAccountBookName.setText(p_ModelPayout.GetAccountBookName());
		mAccountBookID = p_ModelPayout.GetAccountBookID();
		etAmount.setText(p_ModelPayout.GetAmount().toString());
		actvCategoryName.setText(p_ModelPayout.GetCategoryName());
		mCategoryID = p_ModelPayout.GetCategoryID();
		etPayoutDate.setText(DateTools.getFormatDateTime(p_ModelPayout.GetPayoutDate(), "yyyy-MM-dd"));
		etPayoutType.setText(p_ModelPayout.GetPayoutType());
		
		BusinessUser _BusinessUser = new BusinessUser(this);
		String _UserNameString = _BusinessUser.GetUserNameByUserID(p_ModelPayout.GetPayoutUserID());
		
		etPayoutUser.setText(_UserNameString);
		mPayoutUserID = p_ModelPayout.GetPayoutUserID();
		etComment.setText(p_ModelPayout.GetComment());
	}

	private void AddOrEditPayout() {
		Log.i("ActivityPayoutAddOrEdit", "AddOrEditPayout 添加或者编辑Payout");
		Boolean _CheckResult = CheckData();
		if(_CheckResult == false)
		{
			return;
		}
		
		if(mModelPayout == null)
		{
			mModelPayout = new ModelPayout();
		}
		mModelPayout.SetAccountBookID(mAccountBookID);
		mModelPayout.SetCategoryID(mCategoryID);
		mModelPayout.SetAmount(new BigDecimal(etAmount.getText().toString().trim()));
		mModelPayout.SetPayoutDate(DateTools.getDate(etPayoutDate.getText().toString().trim(), "yyyy-MM-dd"));
		mModelPayout.SetPayoutType(etPayoutType.getText().toString().trim());
		mModelPayout.SetPayoutUserID(mPayoutUserID);
		mModelPayout.SetComment(etComment.getText().toString().trim());
		
		Boolean _Result = false;
		
		if(mModelPayout.GetPayoutID() == 0)
		{
			_Result = mBusinessPayout.InsertPayout(mModelPayout);
		}
		else {
			_Result = mBusinessPayout.UpdatePayoutByPayoutID(mModelPayout);
		}

		if(_Result)
		{
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddSucceed), 1).show();
			finish();
		}
		else {
			Toast.makeText(getApplicationContext(), getString(R.string.TipsAddFail), 1).show();
		}
	}
	
	private Boolean CheckData() {
		Log.i("ActivityPayoutAddOrEdit", "CheckData 数据检查");
		Boolean _CheckResult = RegexTools.IsMoney(etAmount.getText().toString().trim());
		if(_CheckResult == false) 
		{
			etAmount.requestFocus();
			Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextMoney), 1).show();
			return _CheckResult;
		}
		
		_CheckResult = RegexTools.IsNull(mCategoryID);
		if(_CheckResult == false) 
		{
			btnSelectCategory.setFocusable(true);
			btnSelectCategory.setFocusableInTouchMode(true);
			btnSelectCategory.requestFocus();
			Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextCategoryIsNull), 1).show();
			return _CheckResult;
		}
		
		if(mPayoutUserID == null)
		{
			btnSelectUser.setFocusable(true);
			btnSelectUser.setFocusableInTouchMode(true);
			btnSelectUser.requestFocus();
			Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextPayoutUserIsNull), 1).show();
			return false;
		}
		
		String _PayoutType = etPayoutType.getText().toString();
		if(_PayoutType.equals(mPayoutTypeArray[0]) || _PayoutType.equals(mPayoutTypeArray[1]))
		{
			if(mPayoutUserID.split(",").length <= 1)
			{
				btnSelectUser.setFocusable(true);
				btnSelectUser.setFocusableInTouchMode(true);
				btnSelectUser.requestFocus();
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextPayoutUser), 1).show();
				return false;
			}
		}
		else {
			if(mPayoutUserID.equals(""))
			{
				btnSelectUser.setFocusable(true);
				btnSelectUser.setFocusableInTouchMode(true);
				btnSelectUser.requestFocus();
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextPayoutUser2), 1).show();
				return false;
			}
		}
		
		return true;
	}
	
	public void SetNumberFinish(BigDecimal p_Number) {
		Log.i("ActivityPayoutAddOrEdit", "SetNumberFinish 设置数字完成");
		((EditText)findViewById(R.id.etAmount)).setText(p_Number.toString());
	}

	private class OnAutoCompleteTextViewItemClickListener implements AdapterView.OnItemClickListener
	{

		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Postion, long arg3) {
			Log.i("ActivityPayoutAddOrEdit", "OnAutoCompleteTextViewItemClickListener 自动完成TextView点击监听");
			ModelCategory _ModelCategory = (ModelCategory)p_AdapterView.getAdapter().getItem(p_Postion);
			mCategoryID = _ModelCategory.GetCategoryID();
		}

	}
	
	private void ShowAccountBookSelectDialog()
	{
		Log.i("ActivityPayoutAddOrEdit", "ShowAccountBookSelectDialog 显示AccountBookSelect弹出对话框");
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
			Log.i("ActivityPayoutAddOrEdit", "OnAccountBookItemClickListener OnAccountBookItemClickListener 点击监听");
			m_AlertDialog = p_AlertDialog;
		}
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position,long arg3) {
			Log.i("ActivityPayoutAddOrEdit", "OnAccountBookItemClickListener onItemClick 点击监听");
			ModelAccountBook _ModelAccountBook = (ModelAccountBook)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
			etAccountBookName.setText(_ModelAccountBook.GetAccountBookName());
			mAccountBookID = _ModelAccountBook.GetAccountBookID();
			m_AlertDialog.dismiss();
		}
	}
	
	private void ShowCategorySelectDialog()
	{
		Log.i("ActivityPayoutAddOrEdit", "ShowCategorySelectDialog 显示CategorySelect弹出对话框");
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.category_select_list, null);
		ExpandableListView _ExpandableListView = (ExpandableListView)_View.findViewById(R.id.ExpandableListViewCategory);
		AdapterCategory _AdapterCategorySelect = new AdapterCategory(this);
		_ExpandableListView.setAdapter(_AdapterCategorySelect);

		_Builder.setIcon(R.drawable.category_small_icon);
		_Builder.setTitle(R.string.ButtonTextSelectCategory);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ExpandableListView.setOnGroupClickListener(new OnCategoryGroupItemClickListener(_AlertDialog,_AdapterCategorySelect));
		_ExpandableListView.setOnChildClickListener(new OnCategoryChildItemClickListener(_AlertDialog,_AdapterCategorySelect));
	}
	
	private class OnCategoryGroupItemClickListener implements OnGroupClickListener
	{
		private AlertDialog m_AlertDialog;
		private AdapterCategory m_AdapterCategory;
		
		public OnCategoryGroupItemClickListener(AlertDialog p_AlertDialog,AdapterCategory p_AdapterCategory)
		{
			Log.i("ActivityPayoutAddOrEdit", "OnCategoryGroupItemClickListener OnCategoryGroupItemClickListener 点击监听");
			m_AlertDialog = p_AlertDialog;
			m_AdapterCategory = p_AdapterCategory;
		}
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			int _Count = m_AdapterCategory.getChildrenCount(groupPosition);
			if(_Count == 0)
			{
				Log.i("ActivityPayoutAddOrEdit", "OnCategoryGroupItemClickListener onGroupClick 点击监听");
				ModelCategory _ModelCategory = (ModelCategory)m_AdapterCategory.getGroup(groupPosition);
				actvCategoryName.setText(_ModelCategory.GetCategoryName());
				mCategoryID = _ModelCategory.GetCategoryID();
				m_AlertDialog.dismiss();
			}
			return false;
		}
	
	}
	
	private class OnCategoryChildItemClickListener implements OnChildClickListener
	{		
		private AlertDialog m_AlertDialog;
		private AdapterCategory m_AdapterCategory;
		
		public OnCategoryChildItemClickListener(AlertDialog p_AlertDialog,AdapterCategory p_AdapterCategory)
		{
			Log.i("ActivityPayoutAddOrEdit", "OnCategoryChildItemClickListener OnCategoryChildItemClickListener 点击监听");
			m_AlertDialog = p_AlertDialog;
			m_AdapterCategory = p_AdapterCategory;
		}

		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			Log.i("ActivityPayoutAddOrEdit", "OnCategoryChildItemClickListener onChildClick 点击监听");
			ModelCategory _ModelCategory = (ModelCategory)m_AdapterCategory.getChild(groupPosition, childPosition);
			actvCategoryName.setText(_ModelCategory.GetCategoryName());
			mCategoryID = _ModelCategory.GetCategoryID();
			m_AlertDialog.dismiss();
			return false;
		}
	
	}
	
	private void ShowDateSelectDialog(int p_Year,int p_Month,int p_Day)
	{
		Log.i("ActivityPayoutAddOrEdit", "ShowDateSelectDialog 显示日期选择弹出对话框");
		(new DatePickerDialog(this, new OnDateSelectedListener(), p_Year, p_Month, p_Day)).show();
	}
	
	private class OnDateSelectedListener implements OnDateSetListener
	{
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			Log.i("ActivityPayoutAddOrEdit", "OnDateSelectedListener onDateSet 点击监听");
			Date _Date = new Date(year-1900, monthOfYear, dayOfMonth);
			etPayoutDate.setText(DateTools.getFormatDateTime(_Date,"yyyy-MM-dd"));
		}
	}

	private void ShowPayoutTypeSelectDialog()
	{
		Log.i("ActivityPayoutAddOrEdit", "ShowPayoutTypeSelectDialog 显示PayoutTypeSelect弹出对话框");
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.payout_type_select_list, null);
		ListView _ListView = (ListView)_View.findViewById(R.id.ListViewPayoutType);

		_Builder.setTitle(R.string.ButtonTextSelectPayoutType);
		_Builder.setNegativeButton(R.string.ButtonTextBack, null);
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ListView.setOnItemClickListener(new OnPayoutTypeItemClickListener(_AlertDialog));
	}
	
	private class OnPayoutTypeItemClickListener implements AdapterView.OnItemClickListener
	{
		private AlertDialog m_AlertDialog;
		public OnPayoutTypeItemClickListener(AlertDialog p_AlertDialog)
		{
			Log.i("ActivityPayoutAddOrEdit", "OnPayoutTypeItemClickListener OnPayoutTypeItemClickListener点击监听");
			m_AlertDialog = p_AlertDialog;
		}
		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position,long arg3) {
			Log.i("ActivityPayoutAddOrEdit", "OnPayoutTypeItemClickListener onItemClick点击监听");
			String _PayoutType = (String)p_AdapterView.getAdapter().getItem(p_Position);
			etPayoutType.setText(_PayoutType);
			etPayoutUser.setText("");
			mPayoutUserID = "";
			m_AlertDialog.dismiss();
		}
	}
	
	private void ShowUserSelectDialog(String p_PayoutType)
	{
		Log.i("ActivityPayoutAddOrEdit", "ShowUserSelectDialog 显示PayoutTypeSelect弹出对话框");
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		View _View = LayoutInflater.from(this).inflate(R.layout.user, null);
		LinearLayout _LinearLayout = (LinearLayout)_View.findViewById(R.id.LinearLayoutMain);
		_LinearLayout.setBackgroundResource(R.drawable.blue);
		ListView _ListView = (ListView)_View.findViewById(R.id.lvUserList);
		AdapterUser _AdapterUser = new AdapterUser(this);
		_ListView.setAdapter(_AdapterUser);

		_Builder.setIcon(R.drawable.user_small_icon);
		_Builder.setTitle(R.string.ButtonTextSelectUser);
		_Builder.setNegativeButton(R.string.ButtonTextBack, new OnSelectUserBack());
		_Builder.setView(_View);
		AlertDialog _AlertDialog = _Builder.create();
		_AlertDialog.show();
		_ListView.setOnItemClickListener(new OnUserItemClickListener(_AlertDialog,p_PayoutType));
	}
	
	private class OnSelectUserBack implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialog, int which) {
			Log.i("ActivityPayoutAddOrEdit", "OnSelectUserBack onClick点击");
			etPayoutUser.setText("");
			String _Name = "";
			mPayoutUserID = "";
			if(mUserSelectedList != null)
			{
				for(int i=0;i<mUserSelectedList.size();i++)
				{
					_Name += mUserSelectedList.get(i).getUserName() + ",";
					mPayoutUserID += mUserSelectedList.get(i).getUserID() + ",";
				}
				etPayoutUser.setText(_Name);
			}
			
			mItemColor = null;
			mUserSelectedList = null;
		}
	}
	
	private class OnUserItemClickListener implements AdapterView.OnItemClickListener
	{
		private AlertDialog m_AlertDialog;
		private String m_PayoutType;
		
		public OnUserItemClickListener(AlertDialog p_AlertDialog,String p_PayoutType)
		{
			Log.i("ActivityPayoutAddOrEdit", "OnUserItemClickListener OnUserItemClickListener点击");
			m_AlertDialog = p_AlertDialog;
			m_PayoutType = p_PayoutType;
		}

		public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position,long arg3) {
			Log.i("ActivityPayoutAddOrEdit", "OnUserItemClickListener OnUserItemClickListener点击");
			String _PayoutTypeArr[] = getResources().getStringArray(R.array.PayoutType);
			ModelUser _ModelUser = (ModelUser)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
			if(m_PayoutType.equals(_PayoutTypeArr[0]) || m_PayoutType.equals(_PayoutTypeArr[1]))
			{
				RelativeLayout _Main = (RelativeLayout)arg1.findViewById(R.id.RelativeLayoutMain);
				
				
				if(mItemColor == null && mUserSelectedList == null)
				{
					mItemColor = new ArrayList<RelativeLayout>();
					mUserSelectedList = new ArrayList<ModelUser>();
				}
				
				if(mItemColor.contains(_Main))
				{
					_Main.setBackgroundResource(R.drawable.blue);
					mItemColor.remove(_Main);
					mUserSelectedList.remove(_ModelUser);
				}
				else {
					_Main.setBackgroundResource(R.drawable.red);
					mItemColor.add(_Main);
					mUserSelectedList.add(_ModelUser);
				}
				
				return;
			}
			
			if(m_PayoutType.equals(_PayoutTypeArr[2]))
			{
				mUserSelectedList = new ArrayList<ModelUser>();
				mUserSelectedList.add(_ModelUser);
				etPayoutUser.setText("");
				String _Name = "";
				mPayoutUserID = "";
				for(int i=0;i<mUserSelectedList.size();i++)
				{
					_Name += mUserSelectedList.get(i).getUserName() + ",";
					mPayoutUserID += mUserSelectedList.get(i).getUserID() + ",";
				}
				etPayoutUser.setText(_Name);
				
				mItemColor = null;
				mUserSelectedList = null;
				m_AlertDialog.dismiss();
			}
		}
	}
}
