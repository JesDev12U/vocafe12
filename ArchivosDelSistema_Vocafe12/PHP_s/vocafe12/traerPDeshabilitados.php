<?php  
    require_once('conexion.php');
    $Estado = 'Deshabilitado';
    $query = $conexion->prepare("SELECT NombreP,Foto FROM productos WHERE Estado = '$Estado'");
    $query->execute();
    $query->bind_result($NombreP,$Foto);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['NombreP'] = $NombreP;
        $temp['Foto'] = $Foto;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>