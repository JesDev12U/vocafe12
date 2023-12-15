<?php
$hostname='localhost';
$database='vocafe12';
$username='root';
$password='';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "La aplicación está experimentando problemas";
}
?>