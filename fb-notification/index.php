<!DOCTYPE html>
<html>
<head>
<title>Know Your Flight Delay</title>
    <!-- You can use Open Graph tags to customize link previews.
    Learn more: https://developers.facebook.com/docs/sharing/webmasters -->
	<meta property="og:url"           content="https://myphpapp-hajurkoaaja.rhcloud.com" />
	<meta property="og:type"          content="website" />
	<meta property="og:title"         content="Know Your Plane Status" />
	<meta property="og:description"   content="Notify You on your plane status" />
	<meta property="og:image"         content="https://myphpapp-hajurkoaaja.rhcloud.com/photo.png" />
<link rel="stylesheet" type="text/css" href="style.css">	

</head>

<body id = body>
<div id="fb-root"></div>

<?php
session_start();
require_once __DIR__ . '/src/Facebook/autoload.php';
require_once 'dbconfig.php';

$fb = new Facebook\Facebook([
  'app_id' => '1526616007668429',
  'app_secret' => '6c732a121fdea39f2a1ac258b4cab67a',
  'default_graph_version' => 'v2.5',
  ]);

$helper = $fb->getRedirectLoginHelper();

$permissions = ['email']; // optional
	
try {
	if (isset($_SESSION['facebook_access_token'])) {
		$accessToken = $_SESSION['facebook_access_token'];
	} else {
  		$accessToken = $helper->getAccessToken();
	}
} catch(Facebook\Exceptions\FacebookResponseException $e) {
 	// When Graph returns an error
 	echo 'Graph returned an error: ' . $e->getMessage();

  	exit;
} catch(Facebook\Exceptions\FacebookSDKException $e) {
 	// When validation fails or other local issues
	echo 'Facebook SDK returned an error: ' . $e->getMessage();
  	exit;
 }

if (isset($accessToken)) {
	if (isset($_SESSION['facebook_access_token'])) {
		$fb->setDefaultAccessToken($_SESSION['facebook_access_token']);
	} else {
		// getting short-lived access token
		$_SESSION['facebook_access_token'] = (string) $accessToken;

	  	// OAuth 2.0 client handler
		$oAuth2Client = $fb->getOAuth2Client();

		// Exchanges a short-lived access token for a long-lived one
		$longLivedAccessToken = $oAuth2Client->getLongLivedAccessToken($_SESSION['facebook_access_token']);

		$_SESSION['facebook_access_token'] = (string) $longLivedAccessToken;

		// setting default access token to be used in script
		$fb->setDefaultAccessToken($_SESSION['facebook_access_token']);
	}

	// redirect the user back to the same page if it has "code" GET variable
	if (isset($_GET['code'])) {
		header('Location: ./');
	}

	// getting basic info about user
	try {
		$profile_request = $fb->get('/me?fields=name,first_name,last_name');
		$profile = $profile_request->getGraphNode()->asArray();
	} catch(Facebook\Exceptions\FacebookResponseException $e) {
		// When Graph returns an error
		echo 'Graph returned an error: ' . $e->getMessage();
		session_destroy();
		// redirecting user back to app login page
		header("Location: ./");
		exit;
	} catch(Facebook\Exceptions\FacebookSDKException $e) {
		// When validation fails or other local issues
		echo 'Facebook SDK returned an error: ' . $e->getMessage();
		exit;
    }
	
	$id = $profile['id'];
	//inserting id and accessToken on database
	$query = "INSERT into accesstoken (id) values($profile[id])";
	mysql_query($query);
	?>
	<!--login for admin and crs -->

	<?php
	

	// printing $profile array on the screen which holds the basic info about user
	//print_r($profile);
	echo "<a href=modify.php?id=$id&delete=1>I don't want to use this</a>";
	echo "Welcome ".$profile['name']."<br />";
	if(isset($_POST["submit"])){
	$plane = "\"".$_POST['plane']."\"";	

	
	$query = "UPDATE accesstoken set plane=".$plane." where id =".$profile[id];
	echo $query;
	mysql_query($query);
	?>

<div class="fb-share-button" data-href="https://myphpapp-hajurkoaaja.rhcloud.com" data-layout="button_count"></div>	
	
	<?php
	}else{
	?>
		<form action="" method="POST">
		Add or Change Plane: <input name="plane" type="text"></input>
		<input type="submit" name="submit" value="submit">
		</form> 

<?php 	
}

  	// Now you can redirect to another page and use the access token from $_SESSION['facebook_access_token']
} else {
	
	$loginUrl = $helper->getLoginUrl('http://myphpapp-hajurkoaaja.rhcloud.com/', $permissions);
	echo '<a href="' . $loginUrl . '">Log in with Facebook!</a>';
	
}

	
	//key is for security
	if(isset(isset($_GET['description']) && isset($_GET['plane']) && $_GET['key']==1122){
	//if(isset($title) && isset($class)){
		$delay = $_GET['delay'];
		$description = $_GET['description'];
		$plane = $_GET['plane'];	

		$query = "SELECT id FROM accesstoken where plane =".$plane;
		echo "$query";	
		$result = mysql_query($query);
	
		if(mysql_num_rows($result)>0){
        	while($row=mysql_fetch_row($result)){	
			$sendNotif = $fb->post('/' . $row[0] . '/notifications', array('href' => '?true=43', 'template' => $description), '1526616007668429|KnpiggPDR2mGliZ_nZkMrr_FKew');//1526616007668429|KnpiggPDR2mGliZ_nZkMrr_FKew is accesstokem from fb app
		}
	}
	}	


?>	  
 </body>
</html>

