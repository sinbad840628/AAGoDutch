package sinbad.godutch.activity;

import java.io.Serializable;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.Toast;
import sinbad.godutch.R;
import sinbad.godutch.activity.base.ActivityFrame;
import sinbad.godutch.adapter.AdapterCategory;
import sinbad.godutch.business.BusinessCategory;
import sinbad.godutch.controls.SlideMenuItem;
import sinbad.godutch.controls.SlideMenuView.OnSlideMenuListener;
import sinbad.godutch.model.ModelCategory;
import sinbad.godutch.model.ModelCategoryTotal;

public class ActivityCategory extends ActivityFrame implements OnSlideMenuListener {

	private ExpandableListView elvCategoryList;
	private ModelCategory mSelectModelCategory;
	private BusinessCategory mBusinessCategory;
	private AdapterCategory mAdapterCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ActivityCategory", "onCreate 方法调用");
		AppendMainBody(R.layout.category);
		Log.i("ActivityCategory", "category 界面布局生成");
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		CreateSlideMenu(R.array.SlideMenuCategory);
	}
	
	private void SetTitle() {
		Log.i("ActivityCategory", "SetTitle 设置Title");
		int _Count = mBusinessCategory.GetNotHideCount();
		String _Titel = getString(R.string.ActivityTitleCategory, new Object[]{_Count});
		SetTopBarTitle(_Titel);
	}

	protected void InitView() {
		Log.i("ActivityCategory", "InitView 初始化扩展ListView视图");
		elvCategoryList = (ExpandableListView) findViewById(R.id.ExpandableListViewCategory);
	}

	protected void InitListeners() {
		Log.i("ActivityCategory", "InitListeners 初始化监听器");
		registerForContextMenu(elvCategoryList);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu p_ContextMenu, View p_View, ContextMenuInfo p_MenuInfo) {		
		Log.i("ActivityCategory", "onCreateContextMenu 创建内容菜单");		
		ExpandableListContextMenuInfo _ExpandableListContextMenuInfo = (ExpandableListContextMenuInfo)p_MenuInfo;
		long _Position = _ExpandableListContextMenuInfo.packedPosition;
		int _Type = ExpandableListView.getPackedPositionType(_Position);
		int _GroupPosition = ExpandableListView.getPackedPositionGroup(_Position);

		Log.i("ActivityCategory", "onCreateContextMenu 创建内容菜单");
		switch (_Type) {
		case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
			Log.i("ActivityCategory", "创建组");
			mSelectModelCategory = (ModelCategory)mAdapterCategory.getGroup(_GroupPosition);
			break;
		case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
			Log.i("ActivityCategory", "创建子项");
			int _ChildPosition = ExpandableListView.getPackedPositionChild(_Position);
			mSelectModelCategory = (ModelCategory)mAdapterCategory.getChild(_GroupPosition, _ChildPosition);
			break;
		default:
			break;
		}
		
		p_ContextMenu.setHeaderIcon(R.drawable.category_small_icon);
		if(mSelectModelCategory != null)
		{
			p_ContextMenu.setHeaderTitle(mSelectModelCategory.GetCategoryName());
		}
		CreateMenu(p_ContextMenu);
		p_ContextMenu.add(0, 3, 3, R.string.ActivityCategoryTotal);
		if(mAdapterCategory.GetChildCountOfGroup(_GroupPosition) != 0 && mSelectModelCategory.GetParentID() == 0)
		{
			p_ContextMenu.findItem(2).setEnabled(false);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem p_Item) {
		Log.i("ActivityCategory", "onContextItemSelected 判断是不是被选中？");		
		Intent _Intent;
		switch (p_Item.getItemId()) {
		case 1:
			_Intent = new Intent(this, ActivityCategoryAddOrEdit.class);
			_Intent.putExtra("ModelCategory", mSelectModelCategory);
			this.startActivityForResult(_Intent, 1);
			break;
		case 2:
			Delete(mSelectModelCategory);
			break;
		case 3:
			List<ModelCategoryTotal> _List = mBusinessCategory.CategoryTotalByParentID(mSelectModelCategory.GetCategoryID());
			_Intent = new Intent();
			_Intent.putExtra("Total", (Serializable)_List);
			Log.i("ActivityCategory", "onContextItemSelected 跳转进入ActivityCategoryChart");
			_Intent.setClass(this, ActivityCategoryChart.class);
			startActivity(_Intent);
			break;
		default:
			break;
		}
		
		return super.onContextItemSelected(p_Item);
	}

	protected void InitVariable() {
		Log.i("ActivityCategory", "InitVariable 初始化变量");	
		mBusinessCategory = new BusinessCategory(ActivityCategory.this);
	}

	protected void BindData()
	{
		Log.i("ActivityCategory", "BindData 绑定数据");	
		mAdapterCategory = new AdapterCategory(this);
		elvCategoryList.setAdapter(mAdapterCategory);
		SetTitle();
	}

	public void onSlideMenuItemClick(View p_View, SlideMenuItem p_SlideMenuItem) {
		Log.i("ActivityCategory", "onSlideMenuItemClick 点击滑动菜单的时候。。。");	
		SlideMenuToggle();
		if (p_SlideMenuItem.getItemID() == 0) {
			Intent _Intent = new Intent(this, ActivityCategoryAddOrEdit.class);
			this.startActivityForResult(_Intent, 1);
			
			return;
		}
		
		if (p_SlideMenuItem.getItemID() == 1) {
			List<ModelCategoryTotal> _List = mBusinessCategory.CategoryTotalByRootCategory();
			Intent _Intent = new Intent();
			_Intent.putExtra("Total", (Serializable)_List);
			Log.i("ActivityCategory", "点击统计类别  进入ActivityCategoryChart");
			_Intent.setClass(this, ActivityCategoryChart.class);
			startActivity(_Intent);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("ActivityCategory", "onActivityResult ");	
		BindData();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void Delete(ModelCategory p_ModelCategory)
	{
		Log.i("ActivityCategory", "Delete 删除方法");	
		Object _Object[] = {p_ModelCategory.GetCategoryName()}; 	
		String _Message = getString(R.string.DialogMessageCategoryDelete,_Object);
		ShowAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener(p_ModelCategory));
	}
	
	private class OnDeleteClickListener implements DialogInterface.OnClickListener {
		private ModelCategory m_ModelCategory;
		public OnDeleteClickListener(ModelCategory p_ModelCategory)
		{
			m_ModelCategory = p_ModelCategory;
		}

		public void onClick(DialogInterface dialog, int which) {
			Boolean _Result;
				_Result = mBusinessCategory.HideCategoryByByPath(m_ModelCategory.GetPath());
				if(_Result == true)
				{
					BindData();
				}
				else {
					Toast.makeText(getApplicationContext(), R.string.TipsDeleteFail, 1).show();
				}
		}
		
	}

}
