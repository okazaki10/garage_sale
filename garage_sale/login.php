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
			$response->id_user = $data['id_user'];
			$response->nama_lengkap = $data['nama_lengkap'];
			$response->username = $data['username'];
			$response->password = $data['password'];
			$response->level = $data['level'];
			$response->foto_profil = $data['foto_profil'];
			$response->alamat = "Provinsi : ".$data['provinsi']."\nKota : ".$data['kota']."\nAlamat : ".$data['alamat']."\nKode pos : ".$data['kode_pos'];
			echo json_encode($response);
		}else{
			$response->success = 0;
			echo json_encode($response);
		}
	}
}else{
	$response->success = 0;
	echo json_encode($response);
}
?>