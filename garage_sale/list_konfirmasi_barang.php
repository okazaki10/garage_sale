<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "user_view"){
		$id_user = $_POST['id_user'];
		$data = Model::selectbanyakmulti("barang,pembayaran","barang.nama_barang,pembayaran.*","barang.id_barang = pembayaran.id_barang and pembayaran.id_user = '$id_user' order by pembayaran.id_pembayaran desc");
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);			
	}else if($_POST['type'] == "penjual_view"){
		$id_user = $_POST['id_user'];
		$data = Model::selectbanyakmulti("barang,pembayaran,user","user.nama_lengkap,user.alamat,user.provinsi,user.kota,user.kode_pos,barang.nama_barang,pembayaran.*","barang.id_barang = pembayaran.id_barang and pembayaran.id_penjual = '$id_user' and user.id_user = pembayaran.id_user and (pembayaran.status='1' or pembayaran.status='2' or pembayaran.status='3') order by pembayaran.id_pembayaran desc");
		$response->success = 1;
		$response->hasil = $data;
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

