package com.qiwenge.android.async;

import android.content.Context;
import android.os.AsyncTask;

import com.qiwenge.android.listeners.CommonHandler;
import com.qiwenge.android.entity.Book;
import com.qiwenge.android.utils.PushUtils;
import com.qiwenge.android.utils.book.BookManager;

/**
 * 异步从书架移除书
 */
public class AsyncRemoveBook extends AsyncTask<Book, Integer, Boolean> {

    private CommonHandler mHandler;

    private Context mContext;

    public AsyncRemoveBook(Context context, CommonHandler handler) {
        mContext = context;
        mHandler = handler;
    }

    @Override
    protected Boolean doInBackground(Book... params) {
        if (params != null && params[0] != null) {
            BookManager.getInstance().delete(mContext, params[0]);
            new PushUtils(mContext).setTags(mContext, BookManager.getInstance().getAll());
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onPreExecute() {
        if (mHandler != null) mHandler.onStart();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (mHandler != null) {
            if (result)
                mHandler.onSuccess();
            else
                mHandler.onFailure();
        }
    }

}