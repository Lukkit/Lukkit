<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=CP850">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Lukkit Docs</title>
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
		<link href="https://maxcdn.bootstrapcdn.com/bootswatch/3.2.0/yeti/bootstrap.min.css" rel="stylesheet">
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesnt work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style> 
		  .sidebar {
			position: fixed;
			top: 0;
			left: 0;
			bottom: 0;
			width: 300px;
			background: #eee;

			overflow: auto;
		  }
		  .page {
			position: absolute;
			top: 0;
			left: 300px;
			bottom: 0;
			right: 0;
		  }
		  .page iframe {
			width: 100%;
			height: 100%;
			border: none;
		  }
		</style>
	</head>
	<body>
		<div class="sidebar">
			<ul class="nav nav-pills nav-stacked">
				<?php
					$docs = array();
					$di = new RecursiveDirectoryIterator('org');
					foreach (new RecursiveIteratorIterator($di) as $filename => $file) {
						if(strpos($filename,'.html') == FALSE) {
							$hyper = str_replace('/','.',$filename);
							$hyper = str_replace('...','',$hyper);
							$hyper = str_replace('..','',$hyper);
							if(in_array($hyper,$docs) == FALSE) {
								$docs[] = $hyper;
								echo '<li><a target="page" href="find.php?cat='.$hyper.'">'.$hyper.'</a></li>';
							}
						}
					}
				?>
			</ul>
		</div>
		<div class="page">
			<iframe name="page" src="org"></iframe>
		</div>
	</body>
</html>

