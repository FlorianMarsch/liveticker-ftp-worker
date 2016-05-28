<?php
$file = $_POST["file"];
$dir = $_POST["dir"];
$data = $_POST["data"];

if (!is_dir($dir)) {
  mkdir($dir,0777,true);
}

file_put_contents($file, $data);
?>