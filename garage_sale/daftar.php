<?php
include 'controller/Model.php';
class data{}
$response  = new data();
if(isset($_POST['type'])){	
	if($_POST['type'] == "view"){
		$nama_lengkap = $_POST['nama_lengkap'];
		$username = $_POST['username'];
		$password = $_POST['password'];
		$alamat = $_POST['alamat'];
		$nomor_telpon = $_POST['nomor_telpon'];
		$provinsi = $_POST['provinsi'];
		$kota = $_POST['kota'];
		$kode_pos = $_POST['kode_pos'];	
		$level = $_POST['level'];
		$nomor_rekening = $_POST['nomor_rekening'];
		$dataselect = Model::select("user","username = '$username'");
		if ($dataselect){
			$response->success = 0;
			echo json_encode($response);
		}else{
			if(isset($_FILES['gambar']['name'])){
				$attachment = $_FILES['gambar']['name'];
				$attach_tmp = $_FILES['gambar']['tmp_name'];
				$namagambar = Model::uploadimage($attachment,$attach_tmp,"foto_profil/");
			}else{
				$namagambar = "none.jpg";
			}
			Model::insert("user","'0','$username','$password','$nama_lengkap','$alamat','$nomor_telpon','$provinsi','$kota','$kode_pos','$nomor_rekening','$namagambar','$level'");
			
			$data = Model::select("user","username = '$username' and password = '$password'");	
			$response->success = 1;
			$response->id_user = $data['id_user'];
			$response->username = $data['username'];
			$response->password = $data['password'];
			$response->level = $data['level'];
			$response->foto_profil = $data['foto_profil'];
			echo json_encode($response);			
		}
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
