<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$username = $_POST['username'];
		$password = $_POST['password'];			
		$data = Model::select("user","username = '$username' and password = '$password'");
		if ($data){
			$response->success = 1;
		}else{
			$response->success = 0;
		}
		$response->id_user = $data['id_user'];
		$response->nama_lengkap = $data['nama_lengkap'];
		$response->username = $data['username'];
		$response->password = $data['password'];
		$response->level = $data['level'];
		echo json_encode($response);
	}
}else{
	$response->success = 0;
	echo json_encode($response);
}
?>