<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$id_penjual = $_POST['id_penjual'];
		$nama_barang = $_POST['nama_barang'];
		$deskripsi = $_POST['deskripsi'];
		$stok = $_POST['stok'];
		$harga = $_POST['harga'];
		$tanggal_masuk = date('Y-m-d');
		if(isset($_POST['total_gambar'])){
			$total_gambar = $_POST['total_gambar'];
		}else{
			$total_gambar = 0;
		}
		if(isset($_FILES['gambar_preview']['name'])){
			$attachment = $_FILES['gambar_preview']['name'];
			$attach_tmp = $_FILES['gambar_preview']['tmp_name'];
			$namagambar = Model::uploadimage($attachment,$attach_tmp,"gambar_barang/");
		}else{
			$namagambar = "none.jpg";
		}
		$id_barang = Model::insertselect("barang","'','$id_penjual','$nama_barang','$deskripsi','$stok','$harga','','$tanggal_masuk','$namagambar'");
		for($i = 0;$i<$total_gambar;$i++){
		
			$namagambar2 = Model::uploadimage($_FILES['list_gambar'.$i]['name'],$_FILES['list_gambar'.$i]['tmp_name'],"gambar_barang/");
			Model::insert("gambar_barang","'','$id_barang','$namagambar2'");
		}
		$response->success = 1;
		$response->id_barang = $id_barang;
		echo json_encode($response);			
	}
}else{
	$response->success = 0;
	echo json_encode($response);
}
?>
<!--
<html>
<form action="tambah_barang.php" method="POST" enctype="multipart/form-data">
	<input type="text" name="total_gambar" value="3">
	<input type="text" name="type" value="view">
	<input type="text" name="id_penjual">
	<input type="text" name="nama_barang">
	<input type="text" name="deskripsi">
	<input type="text" name="stok">
	<input type="text" name="harga">
	<input type="text" name="rating_total">
	<input type="date" name="tanggal_masuk">
	<input type="file" name="gambar_preview" id="gambar_preview">
	<input type="file" name="list_gambar0" id="list_gambar0">
	<input type="file" name="list_gambar1" id="list_gambar1">
	<input type="file" name="list_gambar2" id="list_gambar2">
	<input type="submit" value="Upload Image" name="submit">
</form>
</html>
-->

