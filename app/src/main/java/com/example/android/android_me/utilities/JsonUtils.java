package com.example.android.android_me.utilities;

import android.util.Log;

import com.example.android.android_me.model.Receipt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static ArrayList<Receipt> parseReceiptResult(String json) {

        try {
            Gson gson3 = new Gson();
            Type listType = new TypeToken<List<Receipt>>(){}.getType();
            ArrayList<Receipt> data = gson3.fromJson(json, listType);

            return data;
        }
        catch (Exception e)
        {
            Log.e("MYAPP", "exception", e);

            // Do something to recover ... or kill the app.
        }
        return null;
    }



}

