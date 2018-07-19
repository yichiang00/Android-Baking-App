package com.example.android.android_me.data;


import android.os.AsyncTask;

import com.example.android.android_me.ui.MasterListFragment;

public class ReceiptQueryTask extends AsyncTask<Void, Void, Void> {
        private MasterListFragment.FragmentCallback mFragmentCallback;

        public ReceiptQueryTask(MasterListFragment.FragmentCallback fragmentCallback) {
            mFragmentCallback = fragmentCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            /* Do your thing. */
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mFragmentCallback.onTaskDone();
        }

}
