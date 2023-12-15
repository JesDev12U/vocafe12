<?php
include 'conexion.php';
$CorreoElectronico=$_POST['CorreoElectronico'];
$PasswordE=$_POST['PasswordE'];

/*$CorreoElectronico="fernanda@gmail.com";
$PasswordE="Fernanda+2003";*/

$sentencia=$conexion->prepare("SELECT * FROM empleados WHERE CorreoElectronico=? AND PasswordE=?");
$sentencia->bind_param('ss',$CorreoElectronico,$PasswordE);
$sentencia->execute();

$resultado=$sentencia->get_result();
if($fila=$resultado->fetch_assoc()){
    echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$sentencia->close();
$conexion->close();
?>