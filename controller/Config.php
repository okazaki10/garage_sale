<?php
class Config{
	public static function konfigurasi(){
		$server		= "localhost";
		$user		= "root";
		$password	= "";
		$database	= "garage_sale";
		$db = mysqli_connect($server,$user,$password,$database) or die ("Koneksi gagal!");
		return $db;
	}
}
?>