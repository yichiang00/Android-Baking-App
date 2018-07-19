package com.example.android.android_me.data;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.ui.MasterListAdapter;
import com.example.android.android_me.utilities.JsonUtils;
import com.example.android.android_me.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
//android.os.AsyncTask<Params, Progress, Result>
public class ReceiptQueryTask extends AsyncTask<String, Void, ArrayList<Receipt>> {

    private MasterListAdapter mReceiptCardAdapter;
    Activity mContext;

    public ReceiptQueryTask( Activity context, MasterListAdapter adapter) {
        this.mReceiptCardAdapter=adapter;
        this.mContext=context;

    }
    public ReceiptQueryTask( Activity context) {
        this.mContext=context;

    }
//    public ReceiptQueryTask() {
//
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Receipt> doInBackground(String... params) {
//        URL searchUrl = params[0];
//        String resultJson = null;
//        try
//        {
//            resultJson = NetworkUtils.getResponseFromHttpUrl(searchUrl);
//            ArrayList<Receipt> result = JsonUtils.parseReceiptResult(resultJson);
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new ArrayList<Receipt>();
    }

    @Override
    protected void onPostExecute(ArrayList<Receipt> result) {
        if (result != null) {
            mReceiptCardAdapter.setReceiptItems(result);
        } else {
        }
    }
}
