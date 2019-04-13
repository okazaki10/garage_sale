package com.elsa.garagesale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.plus.SimpleImageLoader;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.elsa.garagesale.adapter.Adapterlistbarang;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.data.Datalistbarang;
import com.elsa.garagesale.util.Server;
import com.android.volley.cache.plus.ImageLoader.ImageContainer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Menu_belum_login extends AppCompatActivity {
    ListView listview;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Datalistbarang> listbarang = new ArrayList<Datalistbarang>();
    Adapterlistbarang adapterlistbarang;
    Handler handler = new Handler();
    Button login;
    TextView list_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_belum_login);
        listview = (ListView) findViewById(R.id.listview);
        list_barang = (TextView) findViewById(R.id.list_barang);
        login = (Button) findViewById(R.id.login);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        handlekoneksibarang(Menu_belum_login.this, Server.URL + "list_barang.php");
        adapterlistbarang = new Adapterlistbarang(Menu_belum_login.this, listbarang);
        listview.setAdapter(adapterlistbarang);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.id_barang = listbarang.get(position).getId_barang();
                MainActivity.harga_barang = listbarang.get(position).getHarga();
                startActivity(new Intent(Menu_belum_login.this, Barang.class));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    handlekoneksibarang(Menu_belum_login.this, Server.URL + "list_barang.php");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(Menu_belum_login.this, Login.class));
            }
        });
    }

    public void handlekoneksibarang(final Context kelasini, String url) {
        listbarang.clear();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);

                    int success = jObj.getInt("success");
                    JSONArray hasil = jObj.getJSONArray("hasil");
                    if (success == 1) {
                        for (int i = 0; i < hasil.length(); i++) {

                            JSONObject hasil2 = hasil.getJSONObject(i);
                            String id_barang = hasil2.getString("id_barang");
                            String id_penjual = hasil2.getString("id_penjual");
                            String nama_barang = hasil2.getString("nama_barang");
                            int harga = Integer.parseInt(hasil2.getString("harga"));
                            String rating_total = hasil2.getString("rating_total");
                            String tanggal_masuk = hasil2.getString("tanggal_masuk");
                            String gambar_preview = hasil2.getString("gambar_preview");
                            listbarang.add(new Datalistbarang(id_barang, id_penjual, nama_barang, harga, rating_total, tanggal_masuk, gambar_preview));

                        }
                        swipeRefreshLayout.setRefreshing(false);
                        adapterlistbarang.notifyDataSetChanged();
                    } else {
                        Toast.makeText(kelasini, "gagal", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    Toast.makeText(kelasini, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(kelasini, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "view");
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
}