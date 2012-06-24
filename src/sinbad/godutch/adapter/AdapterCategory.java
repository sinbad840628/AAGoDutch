package sinbad.godutch.adapter;

import java.util.ArrayList;
import java.util.List;

import sinbad.godutch.R;
import sinbad.godutch.business.BusinessCategory;
import sinbad.godutch.model.ModelCategory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AdapterCategory extends BaseExpandableListAdapter {
	
	private class GroupHolder
	{
		TextView Name;
		TextView Count;
	}
	
	private class ChildHolder
	{
		TextView Name;
	}
	
	private Context m_Context;
	private List m_List;
	private BusinessCategory m_BusinessCategory;
	public List _ChildCountOfGroup;
	
	public AdapterCategory(Context p_Context)
	{
		m_BusinessCategory = new BusinessCategory(p_Context);
		m_Context = p_Context;
		m_List = m_BusinessCategory.GetNotHideRootCategory();
		_ChildCountOfGroup = new ArrayList();
	}
	
	public Integer GetChildCountOfGroup(int groupPosition)
	{
		return (Integer) _ChildCountOfGroup.get(groupPosition);
	}

	public Object getChild(int groupPosition, int childPosition) {
		ModelCategory _ModelCategory = (ModelCategory) getGroup(groupPosition);
		List _List = m_BusinessCategory.GetNotHideCategoryListByParentID(_ModelCategory.GetCategoryID());
		return _List.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent) {
		ChildHolder _ChildHolder;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(m_Context).inflate(R.layout.category_children_list_item, null);
			_ChildHolder = new ChildHolder();
			_ChildHolder.Name = (TextView)convertView.findViewById(R.id.tvCategoryName);
			convertView.setTag(_ChildHolder);
		}
		else {
			_ChildHolder = (ChildHolder)convertView.getTag();
		}
		ModelCategory _ModelCategory = (ModelCategory)getChild(groupPosition, childPosition);
		_ChildHolder.Name.setText(_ModelCategory.GetCategoryName());
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		ModelCategory _ModelCategory = (ModelCategory) getGroup(groupPosition);
		List _List = m_BusinessCategory.GetNotHideCategoryListByParentID(_ModelCategory.GetCategoryID());
		return _List.size();
	}

	public Object getGroup(int groupPosition) {
		return (ModelCategory)m_List.get(groupPosition);
	}

	public int getGroupCount() {
		return m_List.size();
	}

	public long getGroupId(int groupPosition) {
		return (long)groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupHolder _GroupHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(m_Context).inflate(R.layout.category_group_list_item, null);
			_GroupHolder = new GroupHolder();
			_GroupHolder.Name = (TextView)convertView.findViewById(R.id.tvCategoryName);
			_GroupHolder.Count = (TextView)convertView.findViewById(R.id.tvCount);
			convertView.setTag(_GroupHolder);
		}
		else {
			_GroupHolder = (GroupHolder)convertView.getTag();
		}
		ModelCategory _ModelCategory = (ModelCategory)getGroup(groupPosition);
		_GroupHolder.Name.setText(_ModelCategory.GetCategoryName());
		int _Count = m_BusinessCategory.GetNotHideCountByParentID(_ModelCategory.GetCategoryID());
		_GroupHolder.Count.setText(m_Context.getString(R.string.TextViewTextChildrenCategory, _Count));
		_ChildCountOfGroup.add(_Count);
		return convertView;
	}

	public boolean hasStableIds() {
		//行是否具有唯一id
		//是否指定分组视图及其子视图的ID对应的后台数据改变也会保持该ID
		Log.i("AdapterCategory", "hasStableIds");
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		////行是否可选
		Log.i("AdapterCategory", "isChildSelectable");
		return true;
	}

}
