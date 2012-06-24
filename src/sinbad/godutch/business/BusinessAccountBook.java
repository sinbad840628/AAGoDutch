package sinbad.godutch.business;



import java.util.List;

import sinbad.godutch.business.base.BusinessBase;
import sinbad.godutch.database.sqlitedal.SQLiteDALAccountBook;
import sinbad.godutch.model.ModelAccountBook;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

public class BusinessAccountBook extends BusinessBase {

	private SQLiteDALAccountBook mSqLiteDALAccountBook;

	public BusinessAccountBook(Context p_Context) {
		super(p_Context);
		Log.i("BusinessAccountBook", "构造函数生成");
		mSqLiteDALAccountBook = new SQLiteDALAccountBook(p_Context);
	}

	public boolean InsertAccountBook(ModelAccountBook p_Info) {
		Log.i("BusinessAccountBook", "InsertAccountBook  插入账本");
		mSqLiteDALAccountBook.BeginTransaction();
		try {
			boolean _Result = mSqLiteDALAccountBook.InsertAccountBook(p_Info);
			boolean _Result2 = true;
			if (p_Info.GetIsDefault() == 1 && _Result) {
				_Result2 = SetIsDefault(p_Info.GetAccountBookID());
			}

			if (_Result && _Result2) {
				mSqLiteDALAccountBook.SetTransactionSuccessful();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			mSqLiteDALAccountBook.EndTransaction();
		}
	}

	public Boolean DeleteAccountBookByAccountBookID(int p_AccountBookID) {
		mSqLiteDALAccountBook.BeginTransaction();
		try {
			String _Condition = " And AccountBookID = " + p_AccountBookID;
			Boolean _Result = mSqLiteDALAccountBook
					.DeleteAccountBook(_Condition);
			Boolean _Result2 = true;
			if (_Result) {
				BusinessPayout _BusinessPayout = new BusinessPayout(
						GetContext());
				_Result2 = _BusinessPayout
						.DeletePayoutByAccountBookID(p_AccountBookID);
			}

			if (_Result && _Result2) {
				mSqLiteDALAccountBook.SetTransactionSuccessful();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			mSqLiteDALAccountBook.EndTransaction();
		}
	}

	public Boolean UpdateAccountBookByAccountBookID(ModelAccountBook pInfo) {
		Log.i("BusinessAccountBook", "UpdateAccountBookByAccountBookID  通过AccountBookID更新账本");
		mSqLiteDALAccountBook.BeginTransaction();
		try {
			String _Condition = " AccountBookID = " + pInfo.GetAccountBookID();
			Boolean _Result = mSqLiteDALAccountBook.UpdateAccountBook(
					_Condition, pInfo);
			Boolean _Result2 = true;
			if (pInfo.GetIsDefault() == 1 && _Result) {
				_Result2 = SetIsDefault(pInfo.GetAccountBookID());
			}

			if (_Result && _Result2) {
				mSqLiteDALAccountBook.SetTransactionSuccessful();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			mSqLiteDALAccountBook.EndTransaction();
		}
	}

	public List<ModelAccountBook> GetAccountBook(String pCondition) {
		Log.i("BusinessAccountBook", " GetAccountBook 得到账本");
		return mSqLiteDALAccountBook.GetAccountBook(pCondition);
	}

	public boolean IsExistAccountBookByAccountBookName(	
		String p_AccountBookName, Integer p_AccountBookID) {
		String _Condition = " And AccountBookName = '" + p_AccountBookName
				+ "'";
		if (p_AccountBookID != null) {
			_Condition += " And AccountBookID <> " + p_AccountBookID;
		}
		List _List = mSqLiteDALAccountBook.GetAccountBook(_Condition);
		if (_List.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ModelAccountBook GetDefaultModelAccountBook() {
		Log.i("BusinessAccountBook", " GetDefaultModelAccountBook  得到默认账本");
		List _List = mSqLiteDALAccountBook
				.GetAccountBook(" And IsDefault = 1");
		if (_List.size() == 1) {
			return (ModelAccountBook) _List.get(0);
		} else {
			return null;
		}
	}

	public int GetCount() {
		return mSqLiteDALAccountBook.GetCount("");
	}

	public boolean SetIsDefault(int p_ID) {
		String _Condition = " IsDefault = 1";
		ContentValues _ContentValues = new ContentValues();
		_ContentValues.put("IsDefault", 0);
		boolean _Result = mSqLiteDALAccountBook.UpdateAccountBook(_Condition,
				_ContentValues);

		_Condition = " AccountBookID = " + p_ID;
		_ContentValues.clear();
		_ContentValues.put("IsDefault", 1);
		boolean _Result2 = mSqLiteDALAccountBook.UpdateAccountBook(_Condition,
				_ContentValues);

		if (_Result && _Result2) {
			return true;
		} else {
			return false;
		}
	}
	
	public String GetAccountBookNameByAccountId(int p_BookId) {
		Log.i("BusinessAccountBook", " GetAccountBookNameByAccountId  通过ID得到账本名字");
		String _ConditionString = "And AccountBookID = " + String.valueOf(p_BookId);
		List<ModelAccountBook> _AccountBooks = mSqLiteDALAccountBook.GetAccountBook(_ConditionString);
		return _AccountBooks.get(0).GetAccountBookName();
	}
}
