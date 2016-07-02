<?php
/*
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * */

include ('dbconfig.php');
echo $_GET['action'];
if($_GET['action']==test){
	$query = "select $flight from $table";
	$result = "";
}else{
    echo "api call error";
}

?>
