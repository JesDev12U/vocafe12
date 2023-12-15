<?php
include 'conexion.php';
$CorreoE=$_POST['CorreoE'];
$PasswordC=$_POST['PasswordC'];
/*$CorreoE="lopezbandalajesusantonio@gmail.com";
$PasswordC="Jesus+2004";*/

$query=$conexion->prepare("SELECT * FROM clientes WHERE CorreoE=? AND PasswordC=?");
$query->bind_param('ss',$CorreoE,$PasswordC);
$query->execute();

$resultado=$query->get_result();
if($fila=$resultado->fetch_assoc()){
    echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$query->close();
$conexion->close();
?>