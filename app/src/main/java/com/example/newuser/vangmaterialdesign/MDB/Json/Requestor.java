package com.example.newuser.vangmaterialdesign.MDB.Json;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by new user on 9/3/2016.
 */
public class Requestor {
    public static JSONObject requestMoviesJSON(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
            L.m("response"+response);
        } catch (InterruptedException e) {
            L.m(e + "");
        } catch (ExecutionException e) {
            L.m(e + "");
        } catch (TimeoutException e) {
            L.m(e + "");
        }
        return response;
    }

}
