package com.elsa.garagesale.adapter;

/**
 * Created by ibuk on 12/04/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsa.garagesale.R;
import com.elsa.garagesale.data.Datakeranjangbarang;
import com.elsa.garagesale.data.Datalistbarang;
import com.elsa.garagesale.data.Datalistkonfirmasipembeli;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Adapterlistkonfirmasipembeli extends BaseAdapter {
    public AppCompatActivity activity;
    private LayoutInflater inflater;
    private List<Datalistkonfirmasipembeli> items;
    public Context context;
    public Adapterlistkonfirmasipembeli(AppCompatActivity activity2, List<Datalistkonfirmasipembeli> items2) {
        activity = activity2;
        items = items2;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.list_konfirmasi_pembeli, null);
        TextView tanggal_mulai = (TextView) view.findViewById(R.id.tanggal_mulai);
        TextView tanggal_selesai = (TextView) view.findViewById(R.id.tanggal_selesai);
        TextView kode_transaksi = (TextView) view.findViewById(R.id.kode_transaksi);
        TextView judul_barang = (TextView) view.findViewById(R.id.judul_barang);
        TextView harga_barang = (TextView) view.findViewById(R.id.harga_barang);
        TextView jumlah = (TextView) view.findViewById(R.id.jumlah);
        ImageView gambar_barang = (ImageView) view.findViewById(R.id.gambar_barang);
        TextView status = (TextView) view.findViewById(R.id.status);
        TextView no_resi = (TextView) view.findViewById(R.id.no_resi);
        Datalistkonfirmasipembeli data = items.get(position);
        if (!data.getBukti_foto().equals("")) {
            Picasso.with(activity).load(Server.URL + "bukti_transfer/" + data.getBukti_foto()).resize(300, 300).error(R.drawable.gallery).into(gambar_barang);
        }
        judul_barang.setText(data.getNama_barang());
        harga_barang.setText("Rp " + data.getHarga_total());
        jumlah.setText("Jumlah " + data.getJumlah_barang());
        kode_transaksi.setText(data.getId_pembayaran());
        tanggal_mulai.setText(data.getTanggal_mulai());
        if (data.getTanggal_selesai().equals("0000-00-00")) {
            tanggal_selesai.setText("");
        }else{
            tanggal_selesai.setText(data.getTanggal_selesai());
        }
        if (data.getStatus().equals("0")){
            status.setText("belum upload bukti pembayaran");
            no_resi.setText("");
        }else if(data.getStatus().equals("1")){
            status.setText("sudah upload bukti pembayaran\ntinggal menunggu konfirmasi penjual");
            no_resi.setText("");
        }else if(data.getStatus().equals("2")){
            status.setText("sudah di konfirmasi dan akan di kirim");
            no_resi.setText(data.getNo_resi());
        }else if(data.getStatus().equals("3")){
            status.setText("konfirmasi dibatalkan oleh penjual\nsilahkan beli lagi");
            no_resi.setText("");
        }


        return view;
    }

}