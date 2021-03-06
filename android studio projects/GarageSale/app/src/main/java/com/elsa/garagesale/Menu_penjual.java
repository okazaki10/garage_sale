package com.elsa.garagesale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
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

public class Menu_penjual extends AppCompatActivity {
    ListView listview;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Datalistbarang> listbarang = new ArrayList<Datalistbarang>();
    Adapterlistbarang adapterlistbarang;
    Handler handler = new Handler();
    Button tambah_barang;
    Button logout;
    Button konfirmasi;

    TextView nama_lengkap2;

    TextView list_barang;
    ImageView foto_profil2;
    String id_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_penjual);
        nama_lengkap2 = (TextView) findViewById(R.id.nama_lengkap);
        tambah_barang = (Button) findViewById(R.id.tambah_barang);
        logout = (Button) findViewById(R.id.logout);
        konfirmasi = (Button) findViewById(R.id.konfirmasi);
        listview = (ListView) findViewById(R.id.listview);
        foto_profil2 = (ImageView) findViewById(R.id.foto_profil);
        list_barang = (TextView) findViewById(R.id.list_barang);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        nama_lengkap2.setText(MainActivity.nama_lengkap);
        handlekoneksibarangpenjual(Menu_penjual.this, Server.URL + "list_barang.php");
        Picasso.with(Menu_penjual.this).load(Server.URL + "foto_profil/" + MainActivity.foto_profil).resize(300, 300).error(R.drawable.gallery).into(foto_profil2);
        adapterlistbarang = new Adapterlistbarang(Menu_penjual.this, listbarang);
        listview.setAdapter(adapterlistbarang);
        AlertDialog.Builder alert  = new AlertDialog.Builder(Menu_penjual.this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.id_barang = listbarang.get(position).getId_barang();
                MainActivity.harga_barang = listbarang.get(position).getHarga();
                startActivity(new Intent(Menu_penjual.this, Barang.class));
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alert.setMessage("Hapus?");
                alert.setTitle("Garage Sale");
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id_barang = listbarang.get(position).getId_barang();
                        handlekoneksihapusbarang(Menu_penjual.this, Server.URL + "barang.php");
                    }
                });
                alert.setCancelable(true);
                alert.create().show();
                return true;
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handlekoneksibarangpenjual(Menu_penjual.this, Server.URL + "list_barang.php");
            }
        });
        tambah_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu_penjual.this, Tambah_barang.class));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
                editor.putString("id_user", "");
                editor.putString("nama_lengkap", "");
                editor.putString("username", "");
                editor.putString("password", "");
                editor.putString("level", "-1");
                editor.putString("foto_profil", "");
                editor.putString("alamat","");
                editor.apply();
                startActivity(new Intent(Menu_penjual.this, Menu_belum_login.class));
                finishAffinity();
            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu_penjual.this,Listview_konfirmasi_penjual.class));
            }
        });
    }

    public void handlekoneksibarangpenjual(final Context kelasini, String url) {
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
                params.put("type", "penjual");
                params.put("id_penjual", MainActivity.id_user);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
    public void handlekoneksihapusbarang(final Context kelasini, String url) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(kelasini, response, Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);

                    int success = jObj.getInt("success");
                    if (success == 3) {
                        Toast.makeText(kelasini, "berhasil hapus barang", Toast.LENGTH_LONG).show();
                        handlekoneksibarangpenjual(Menu_penjual.this, Server.URL + "list_barang.php");
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
                params.put("type", "hapus");
                params.put("id_barang", id_barang);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
}