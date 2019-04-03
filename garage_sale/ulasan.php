<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$id_barang = $_POST['id_barang'];
		$data = Model::selectbanyakmulti("ulasan,user","user.nama_lengkap,user.foto_profil,ulasan.*","ulasan.id_barang = '$id_barang' and ulasan.id_user = user.id_user");;
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);
	}else if ($_POST['type'] == "insert"){
		$id_barang = $_POST['id_barang'];
		$id_user = $_POST['id_user'];
		$rating = $_POST['rating'];
		$komentar = $_POST['komentar'];
		$tanggal_mulai = date('Y-m-d');
		Model::insert("ulasan","'0','$id_barang','$id_user','$rating','$komentar','$tanggal_mulai'");
		$data = Model::selectbanyak("ulasan","id_barang = '$id_barang'");
		$ulasan_count = count($data);
		$total = 0;
		foreach($data as $data2){
			$total = $total + $data2['rating'];
		}
		$total = $total / $ulasan_count;
		Model::update("barang","rating_total = '$total'","id_barang = '$id_barang'");
		$response->success = 2;
		echo json_encode($response);
	}
}else{
		$response->success = 0;
		echo json_encode($response);
	}
?>