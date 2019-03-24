<?php
include 'controller/Model.php';
if(isset($_GET['type'])){	
	if($_GET['type'] == "insert"){
		$username = $_GET['username'];
		$password = $_GET['password'];	
		Model::insert("user","'','$username','$password','','','','','','','','',''");
	}else if($_GET['type'] == "edit"){
		$id = $_GET['id'];
		$username = $_GET['username'];
		$password = $_GET['password'];
		Model::update("user","username = '$username',password = '$password'","id_user = '$id'");
	}else if($_GET['type'] == "hapus"){
		$id = $_GET['id'];
		Model::hapus("user","id_user = '$id'");
	}
}
class data{}
$data = Model::selectbanyak("user");
$response  = new data();
$response->success = 1;
$response->hasil = $data;
echo json_encode($response);
?>