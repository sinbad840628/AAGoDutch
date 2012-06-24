package sinbad.godutch.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ModelPayout implements Serializable {

		//支出表主键ID
		private int mPayoutID;
		//账本ID外键
		private int mAccountBookID;
		//账本名称
		private String mAccountBookName;
		//支出类别ID外键
		private int mCategoryID;
		//类别名称
		private String mCategoryName;
		//路径
		private String mPath;
		//付款方式ID外键
		private int mPayWayID;
		//消费地点ID外键
		private int mPlaceID;
		//消费金额
		private BigDecimal mAmount;
		//消费日期
		private Date mPayoutDate;
		//计算方式
		private String mPayoutType;
		//消费人ID外键
		private String mPayoutUserID;
		//备注
		private String mComment;
		//添加日期
		private Date mCreateDate = new Date();
		//状态 0失效 1启用
		private int mState = 1;
		
		/**
		 * 支出表主键ID
		 */
		public int GetPayoutID() {
			return mPayoutID;
		}
		/**
		 * 支出表主键ID
		 */
		public void SetPayoutID(int pPayoutID) {
			this.mPayoutID = pPayoutID;
		}
		/**
		 * 账本名称ID外键
		 */
		public int GetAccountBookID() {
			return mAccountBookID;
		}
		/**
		 * 账本ID外键
		 */
		public void SetAccountBookID(int pAccountBookID) {
			this.mAccountBookID = pAccountBookID;
		}
		/**
		 * 账本名称
		 */
		public String GetAccountBookName() {
			return mAccountBookName;
		}
		/**
		 * 账本名称
		 */
		public void SetAccountBookName(String pAccountBookName) {
			this.mAccountBookName = pAccountBookName;
		}
		/**
		 * 支出类别ID外键
		 */
		public int GetCategoryID() {
			return mCategoryID;
		}
		/**
		 * 支出类别ID外键
		 */
		public void SetCategoryID(int pCategoryID) {
			this.mCategoryID = pCategoryID;
		}
		/**
		 * 路径
		 */
		public String GetPath() {
			return mPath;
		}
		/**
		 * 路径
		 */
		public void SetPath(String pPath) {
			this.mPath = pPath;
		}
		/**
		 * 账本名称
		 */
		public String GetCategoryName() {
			return mCategoryName;
		}
		/**
		 * 账本名称
		 */
		public void SetCategoryName(String pCategoryName) {
			this.mCategoryName = pCategoryName;
		}
		/**
		 * 	付款方式ID外键
		 */
		public int GetPayWayID() {
			return mPayWayID;
		}
		/**
		 * 	付款方式ID外键
		 */
		public void SetPayWayID(int pPayWayID) {
			this.mPayWayID = pPayWayID;
		}
		/**
		 * 消费地点ID外键
		 */
		public int GetPlaceID() {
			return mPlaceID;
		}
		/**
		 * 消费地点ID外键
		 */
		public void SetPlaceID(int pPlaceID) {
			this.mPlaceID = pPlaceID;
		}
		/**
		 * 消费金额
		 */
		public BigDecimal GetAmount() {
			return mAmount;
		}
		/**
		 * 消费金额
		 */
		public void SetAmount(BigDecimal pAmount) {
			this.mAmount = pAmount;
		}
		/**
		 * 消费日期
		 */
		public Date GetPayoutDate() {
			return mPayoutDate;
		}
		/**
		 * 消费日期
		 */
		public void SetPayoutDate(Date pPayoutDate) {
			this.mPayoutDate = pPayoutDate;
		}
		/**
		 * 计算方式
		 */
		public String GetPayoutType() {
			return mPayoutType;
		}
		/**
		 * 计算方式
		 */
		public void SetPayoutType(String pPayoutType) {
			this.mPayoutType = pPayoutType;
		}
		/**
		 * 消费人ID外键
		 */
		public String GetPayoutUserID() {
			return mPayoutUserID;
		}
		/**
		 * 消费人ID外键
		 */
		public void SetPayoutUserID(String pPayoutUserID) {
			this.mPayoutUserID = pPayoutUserID;
		}	
		/**
		 * 备注
		 */
		public String GetComment() {
			return mComment;
		}
		/**
		 * 备注
		 */
		public void SetComment(String pComment) {
			this.mComment = pComment;
		}
		/**
		 * 添加日期
		 */
		public Date GetCreateDate() {
			return mCreateDate;
		}
		/**
		 * 添加日期
		 */
		public void SetCreateDate(Date pCreateDate) {
			this.mCreateDate = pCreateDate;
		}
		/**
		 * 状态 0失效 1启用
		 */
		public int GetState() {
			return mState;
		}
		/**
		 * 状态 0失效 1启用
		 */
		public void SetState(int pState) {
			this.mState = pState;
		}

		
}
