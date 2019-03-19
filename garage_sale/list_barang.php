<?php
include 'controller/Model.php';
class data{}
if(isset($_GET['type'])){	
	if($_GET['type'] == "view"){
		$data = Model::selectbanyak("barang");
		$response  = new data();
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);
	}
}else{
		$data = Model::selectbanyak("barang");
		$response  = new data();
		$response->success = 1;
		$response->hasil = $data;
		echo json_encode($response);
	}
?>