package com.example.android.android_me.data;


import android.os.AsyncTask;

import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.ui.MasterListFragment;
import com.example.android.android_me.utilities.JsonUtils;
import com.example.android.android_me.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ReceiptQueryTask extends AsyncTask<URL, Void, ArrayList<Receipt>> {
        private MasterListFragment.FragmentCallback mFragmentCallback;

        public ReceiptQueryTask(MasterListFragment.FragmentCallback fragmentCallback) {
            mFragmentCallback = fragmentCallback;
        }

        @Override
        protected ArrayList<Receipt> doInBackground(URL... params) {
            URL searchUrl = params[0];
            String resultJson = null;
            try
            {
                resultJson = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                ArrayList<Receipt> result = JsonUtils.parseReceiptResult(resultJson);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Receipt> result) {
            mFragmentCallback.onTaskDone(result);
        }

}
