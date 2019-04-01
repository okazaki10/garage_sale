<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$id_user = $_POST['id_user'];
		$data = Model::selectbanyakmulti("keranjang,barang","keranjang.*,barang.id_penjual,barang.nama_barang,barang.gambar_preview","keranjang.id_barang = barang.id_barang and keranjang.id_user = '$id_user'");
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);
	}else if($_POST['type'] == "hapus"){
		$id_keranjang = $_POST['id_keranjang'];
		$data = Model::select("keranjang","id_keranjang = '$id_keranjang'");
		$jumlah_barang = $data['jumlah_barang'];
		$id_barang = $data['id_barang'];
		$data2 = Model::select("barang","id_barang = '$id_barang'");
		$stok = $data2['stok'];
		$total = $jumlah_barang + $stok;
		Model::update("barang","stok = '$total'","id_barang = '$id_barang'");
		Model::hapus("keranjang","id_keranjang = '$id_keranjang'");
		$response->success = 2;
		echo json_encode($response);
	}else if($_POST['type'] == "hapus_konfirmasi"){
		$id_keranjang = $_POST['id_keranjang'];
		Model::hapus("keranjang","id_keranjang = '$id_keranjang'");
		$response->success = 2;
		echo json_encode($response);
	}
}else{
		$response->success = 0;
		echo json_encode($response);
	}
?>