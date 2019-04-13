package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import androidx.appcompat.app.AppCompatActivity;

public class Konfirmasi_penjual extends AppCompatActivity{
    TextView nama_barang;
    TextView harga_barang;
    TextView kurir;
    TextView nama_lengkap;
    TextView alamat;
    TextView jumlah;
    EditText no_resi;
    ImageView imageview;
    Button konfirmasi;
    Button batal_konfirmasi;

    public static String id_penjual;
    public static int harga_barang3;
    public static int stok_barang;
    String nama_barang2;
    int harga_barang2;
    String stok2;
    String deskripsi2;
    String tanggal_masuk2;
    String jumlah2;
    String no_resi2;
    int rating_total2;
    int id_gambar = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konfirmasi_penjual);
        nama_lengkap = (TextView) findViewById(R.id.nama_lengkap);
        nama_barang = (TextView) findViewById(R.id.nama_barang);
        harga_barang = (TextView) findViewById(R.id.harga_barang);
        jumlah = (TextView) findViewById(R.id.jumlah);
        alamat = (TextView) findViewById(R.id.alamat);
        kurir = (TextView) findViewById(R.id.kurir);
        konfirmasi = (Button) findViewById(R.id.konfirmasi);
        batal_konfirmasi = (Button) findViewById(R.id.batal_konfirmasi);
        no_resi = (EditText) findViewById(R.id.no_resi);
        imageview = (ImageView) findViewById(R.id.imageView);
        Picasso.with(Konfirmasi_penjual.this).load(Server.URL + "bukti_transfer/" + Listview_konfirmasi_penjual.bukti_foto).resize(300,300).error(R.drawable.gallery).into(imageview);
        nama_lengkap.setText(Listview_konfirmasi_penjual.nama_lengkap);
        nama_barang.setText(Listview_konfirmasi_penjual.nama_barang);
        harga_barang.setText(Listview_konfirmasi_penjual.harga_total);
        jumlah.setText(Listview_konfirmasi_penjual.jumlah_barang);
        kurir.setText(Listview_konfirmasi_penjual.kurir);
        alamat.setText(Listview_konfirmasi_penjual.alamat2);
        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_resi2 = no_resi.getText().toString();
                handlekoneksikonfirmasi(Konfirmasi_penjual.this,Server.URL + "pembayaran.php");
            }
        });
        batal_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlekoneksikonfirmasigagal(Konfirmasi_penjual.this,Server.URL + "pembayaran.php");
            }
        });
    }

    public void handlekoneksikonfirmasi(final Context kelasini, String url) {

        SimpleMultiPartRequest strReq = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 3) {
                        startActivity(new Intent(kelasini,Menu_penjual.class));
                        finishAffinity();
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
        });
        strReq.addStringParam("type", "update_penjual_berhasil");
        strReq.addStringParam("id_pembayaran",Listview_konfirmasi_penjual.id_pembayaran);
        strReq.addStringParam("no_resi",no_resi2);
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }


    public void handlekoneksikonfirmasigagal(final Context kelasini, String url) {

        SimpleMultiPartRequest strReq = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 3) {
                        startActivity(new Intent(kelasini,Menu_penjual.class));
                        finishAffinity();
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
        });
        strReq.addStringParam("type", "update_penjual_gagal");
        strReq.addStringParam("id_pembayaran",Listview_konfirmasi_penjual.id_pembayaran);
        strReq.addStringParam("id_barang",Listview_konfirmasi_penjual.id_barang);
        strReq.addStringParam("jumlah_barang",Listview_konfirmasi_penjual.jumlah_barang);
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
}
