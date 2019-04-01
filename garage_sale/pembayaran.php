<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$id_pembayaran = $_POST['id_pembayaran'];
		$data = Model::selectbanyakmulti("barang,pembayaran","barang.nama_barang,pembayaran.*","barang.id_barang = pembayaran.id_barang and pembayaran.id_pembayaran = '$id_pembayaran'");
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);			
	}else if ($_POST['type'] == "insert"){
		$id_penjual = $_POST['id_penjual'];
		$id_user = $_POST['id_user'];
		$id_barang = $_POST['id_barang'];
		$kurir = $_POST['kurir'];
		$jumlah_barang = $_POST['jumlah_barang'];
		$harga_total = $_POST['harga_total'];
		$bukti_foto = "";
		$no_resi = "";
		$tanggal_mulai = date('Y-m-d');
		$tanggal_selesai = "";
		$status = 0;
		$data = Model::insertselect("pembayaran","'','$id_penjual','$id_user','$id_barang','$kurir','$jumlah_barang','$harga_total','$bukti_foto','$no_resi','$tanggal_mulai','$tanggal_selesai','$status'");
		$response->success = 2;
		$response->id_pembayaran = $data;
		echo json_encode($response);
	}else if ($_POST['type'] == "update_pembeli"){
		$id_pembayaran = $_POST['id_pembayaran'];
		$attachment = $_FILES['bukti_foto']['name'];
		$attach_tmp = $_FILES['bukti_foto']['tmp_name'];
		$status = 1;
		$namagambar = Model::uploadimage($attachment,$attach_tmp,"bukti_transfer/");
		Model::update("pembayaran","bukti_foto = '$namagambar',status = '$status'","id_pembayaran = '$id_pembayaran'");
		$response->success = 3;
		echo json_encode($response);
	}else if ($_POST['type'] == "update_penjual_berhasil"){
		$id_pembayaran = $_POST['id_pembayaran'];
		$no_resi = $_POST['no_resi'];
		$status = 2;
		$tanggal_selesai = date('Y-m-d');
		Model::update("pembayaran","status = '$status',no_resi = '$no_resi',tanggal_selesai = '$tanggal_selesai'","id_pembayaran = '$id_pembayaran'");
		$response->success = 3;
		echo json_encode($response);
	}else if ($_POST['type'] == "update_penjual_gagal"){
		$id_pembayaran = $_POST['id_pembayaran'];
		$id_barang = $_POST['id_barang'];
		$jumlah_barang = $_POST['jumlah_barang'];
		$status = 3;
		$data = Model::select("barang","id_barang = '$id_barang'");
		$stok = $data['stok'];
		$total = $jumlah_barang + $stok;
		Model::update("barang","stok = '$total'","id_barang = '$id_barang'");
		Model::update("pembayaran","status = '$status'","id_pembayaran = '$id_pembayaran'");
		$response->success = 3;
		echo json_encode($response);
	}
}else{
	$response->success = 0;
	echo json_encode($response);
}
?>
<!--
<html>
<form action="barang.php" method="POST" enctype="multipart/form-data">
	<input type="text" name="type" value="insert">
	<input type="text" name="id_user">
	<input type="text" name="id_barang">
	<input type="text" name="jumlah_barang">
	<input type="text" name="harga_total">
	<input type="text" name="nomor_telpon">
	<input type="text" name="provinsi">
	<input type="text" name="kota">
	<input type="text" name="nomor_rekening">
	<input type="text" name="kode_pos">
	<input type="text" name="level">
	<input type="file" name="gambar" id="gambar">
	<input type="submit" value="Upload Image" name="submit">
</form>
</html>
-->

