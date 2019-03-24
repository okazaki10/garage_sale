<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$id_barang = $_POST['id_barang'];
		$data = Model::select("barang","id_barang = '$id_barang'");
		$gambar_barang = Model::selectbanyak("gambar_barang","id_gambar_barang = '$id_barang'");
		$response->success = 1;
		$response->id_penjual = $data['id_penjual'];
		$response->nama_barang = $data['nama_barang'];
		$response->deskripsi = $data['deskripsi'];
		$response->stok = $data['stok'];
		$response->rating_total = $data['rating_total'];
		$response->tanggal_masuk = $data['tanggal_masuk'];
		$response->harga = $data['harga'];
		$response->gambar_preview = $data['gambar_preview'];
		$response->gambar = $gambar_barang;
		echo json_encode($response);			
	}
}else{
	$response->success = 0;
	echo json_encode($response);
}
?>
<!--
<html>
<form action="daftar.php" method="POST" enctype="multipart/form-data">
	<input type="text" name="type" value="view">
	<input type="text" name="nama_lengkap">
	<input type="text" name="username">
	<input type="text" name="password">
	<input type="text" name="alamat">
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
