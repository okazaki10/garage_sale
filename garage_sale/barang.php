<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$id_barang = $_POST['id_barang'];
		$data = Model::selectsatumulti("barang,user","user.nama_lengkap,barang.*","barang.id_penjual = user.id_user and barang.id_barang = '$id_barang'");
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
		$response->nama_lengkap = $data['nama_lengkap'];
		echo json_encode($response);			
	}else if ($_POST['type'] == "insert"){
		$id_user = $_POST['id_user'];
		$id_barang = $_POST['id_barang'];
		$jumlah_barang = $_POST['jumlah_barang'];
		$harga_total = $_POST['harga_total'];
		$barang = Model::select("barang","id_barang = '$id_barang'");
		$stok = $barang['stok'];
		if ($jumlah_barang <= $stok){
		$data = Model::insert("keranjang","'','$id_user','$id_barang','$jumlah_barang','$harga_total'");
		$total = $stok - $jumlah_barang;
		Model::update("barang","stok = '$total'","id_barang = '$id_barang'");
		$response->success = 2;
		}else{
		$response->success = 4;
		}
		echo json_encode($response);
	}else if ($_POST['type'] == "hapus"){
		$id_barang = $_POST['id_barang'];
		Model::hapus("barang","id_barang = '$id_barang'");
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

