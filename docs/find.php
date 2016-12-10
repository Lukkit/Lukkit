<?php
if(!empty($_GET['cat'])) {
	$path = str_replace('.','/',$_GET['cat']);
	if(file_exists($path)) {
		echo '
<!DOCTYPE html>
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=CP850">
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<title>Lukkit Docs</title>
			<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
			<link href="http://maxcdn.bootstrapcdn.com/bootswatch/3.2.0/yeti/bootstrap.min.css" rel="stylesheet">

			<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
			<!-- WARNING: Respond.js doesnt work if you view the page via file:// -->
			<!--[if lt IE 9]>
			  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
			<![endif]-->
		</head>
		<body>
			<div class="container">
				<div class="page-header">
					<h1><span class="domain">Classes in '.$_GET["cat"].' <small>'.$path.'</small></span></h1>
				</div>
				<div class="panel panel-default">
					<ul class="nav nav-pills nav-stacked">
		';
		foreach (new DirectoryIterator($path.'/') as $file) {
			if($file->isDot()) continue;
			if (strpos($file, '.') == TRUE) {
				$file = str_replace('.html','',$file);
				echo '<li><a href="'.$path.'/'.$file.'.html">'.$file.'</a></li>';
			}
		}
		echo '
				</ul>
			</div>
		</div>
	</body>
</html>
		';
	} else {
		echo file_get_contents('org/index.html');
	}
} elseif(!empty($_GET['sub'])) {
	if(file_exists($_GET['sub'])) {
		echo file_get_contents($_GET['sub']);
	} else {
		echo file_get_contents('org/index.html');
	}	
} else {
	echo file_get_contents('org/index.html');
}
?>