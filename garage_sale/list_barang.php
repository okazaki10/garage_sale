<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$data = Model::selectbanyak("barang","stok > '0'");
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);
	}else if ($_POST['type'] == "penjual"){
		$id_penjual = $_POST['id_penjual'];
		$data = Model::selectbanyak("barang","id_penjual = '$id_penjual'");
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);
	}
}else{
		$response->success = 0;
		echo json_encode($response);
	}
?>