<?php  
    require_once('conexion.php');
    $Estado = 'Habilitado';
    $query = $conexion->prepare("SELECT NombreP,Descripcion,Precio,Foto FROM productos WHERE Estado = '$Estado'");
    $query->execute();
    $query->bind_result($NombreP,$Descripcion,$Precio,$Foto);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['NombreP'] = $NombreP;
        $temp['Descripcion'] = $Descripcion;
        $temp['Precio'] = $Precio;
        $temp['Foto'] = $Foto;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>