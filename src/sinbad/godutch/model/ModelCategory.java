package sinbad.godutch.model;

import java.io.Serializable;
import java.util.Date;

public class ModelCategory implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 907837048323397148L;
		//类别表主键ID
		private int mCategoryID;
		//类别名称
		private String mCategoryName;
		//类型标记名称
		private String mTypeFlag;
		//父类型ID
		private int mParentID = 0;
		//路径
		private String mPath;	
		//添加日期
		private Date mCreateDate = new Date();
		//状态 0失效 1启用
		private int mState = 1;
		
		/**
		 * 账本表主键ID
		 */
		public int GetCategoryID() {
			return mCategoryID;
		}
		/**
		 * 账本表主键ID
		 */
		public void SetCategoryID(int pCategoryID) {
			this.mCategoryID = pCategoryID;
		}
		/**
		 * 账本名称
		 */
		public String GetCategoryName() {
			return mCategoryName;
		}
		/**
		 * 设置种类名
		 */
		public void SetCategoryName(String pCategoryName) {
			this.mCategoryName = pCategoryName;
		}
		/**
		 * 得到类型标记名称
		 */
		public String GetTypeFlag() {
			return mTypeFlag;
		}
		/**
		 * 设置类型标记名称
		 */
		public void SetTypeFlag(String pTypeFlag) {
			this.mTypeFlag = pTypeFlag;
		}
		/**
		 * 得到父类型ID
		 */
		public int GetParentID() {
			return mParentID;
		}
		/**
		 * 设置父类型ID
		 */
		public void SetParentID(int pParentID) {
			this.mParentID = pParentID;
		}
		/**
		 * 得到路径
		 */
		public String GetPath() {
			return mPath;
		}
		/**
		 * 设置路径
		 */
		public void SetPath(String pPath) {
			this.mPath = pPath;
		}
		/**
		 * 得到添加日期
		 */
		public Date GetCreateDate() {
			return mCreateDate;
		}
		/**
		 * 设置创建日期
		 */
		public void SetCreateDate(Date pCreateDate) {
			this.mCreateDate = pCreateDate;
		}
		/**
		 * 得到状态
		 */
		public int GetState() {
			return mState;
		}
		/**
		 * 设置状态
		 */
		public void SetState(int pState) {
			this.mState = pState;
		}
		
		@Override
		public String toString() {
			return mCategoryName;
		}
}
