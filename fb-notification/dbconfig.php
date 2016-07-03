<?php
error_reporting(0);
$h = "localhost";
$u = "root";
$p = "12345678";
$db = "deerthon";
$port = $OPENSHIFT_MYSQL_DB_PORT;
$conn = mysqli_connect($h,$u,$p,$db) or die(mysql_error());
//mysqli_select_db($db)or die(mysql_error());
?>
