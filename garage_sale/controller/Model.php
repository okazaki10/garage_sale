<?php
include 'Config.php';
class Model extends Config
{
	public static function insert($table,$value){
		$db = Config::konfigurasi();
		$query = "INSERT INTO $table VALUES ($value)";
		$hasil = mysqli_query($db,$query);
	}
	public static function insertselect($table,$value){
		$db = Config::konfigurasi();
		$query = "INSERT INTO $table VALUES ($value)";
		if (mysqli_query($db,$query)){
			$data= mysqli_insert_id($db);
			return $data;
		}
	}
	public static function select($table,$value){
		$db = Config::konfigurasi();
		if ($value != ""){
			$query = "select * from $table where $value";
		}else{
			$query = "select * from $table";
		}
		$hasil = mysqli_query($db,$query);
		$data = mysqli_fetch_assoc($hasil);
		return $data;
	}	
	public static function selectbanyak($table,$value = ""){
		$db = Config::konfigurasi();
		if ($value != ""){
			$query = "select * from $table where $value";
		}else{
			$query = "select * from $table";
		}
		$hasil = mysqli_query($db,$query);
		$data = array();
		while($row = mysqli_fetch_assoc($hasil)){
			$data[] = $row;
		}
		return $data;
	}
	public static function selectbanyakmulti($table,$value,$value2){
		$db = Config::konfigurasi();
		$query = "select $value from $table where $value2";
		$hasil = mysqli_query($db,$query);
		$data = array();
		while($row = mysqli_fetch_assoc($hasil)){
			$data[] = $row;
		}
		return $data;
	}
	public static function selectsatu($table,$value){
		$db = Config::konfigurasi();
		$query = "select * from $table order by $value desc limit 1";
		$hasil = mysqli_query($db,$query);
		$data = mysqli_fetch_assoc($hasil);
		$data2 = $data[$value];
		return $data2;
	}
	public static function hapus($table,$value){
		$db = Config::konfigurasi();
		$query = "delete from $table where $value";
		$hasil = mysqli_query($db,$query);
	}
	public static function update($table,$field,$value){
		$db = Config::konfigurasi();
		$query = "update $table set $field where $value";
		$hasil = mysqli_query($db,$query);
	}
	public static function generateRandomString($length = 10) {
		$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		$charactersLength = strlen($characters);
		$randomString = '';
		for ($i = 0; $i < $length; $i++) {
			$randomString .= $characters[rand(0, $charactersLength - 1)];
		}
		return $randomString;
	}
	public static function gettimeimage(){
		return date('Y-m-d H-i-s.') . gettimeofday()['usec'];
	}
}
?>