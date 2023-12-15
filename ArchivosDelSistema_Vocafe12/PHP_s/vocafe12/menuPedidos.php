<?php  
    require_once('conexion.php');
    $Estado = 'Habilitado';
    $query = $conexion->prepare("SELECT NombreP,Precio,Foto FROM productos WHERE Estado = '$Estado'");
    $query->execute();
    $query->bind_result($NombreP,$Precio,$Foto);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['NombreP'] = $NombreP;
        $temp['Precio'] = $Precio;
        $temp['Foto'] = $Foto;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>