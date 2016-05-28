<?php
$file = $_POST["file"];
$dir = $_POST["dir"];
$data = $_POST["data"];

if (!is_dir($dir)) {
  mkdir($dir);
}

file_put_contents($dir+$file, $data);
?>