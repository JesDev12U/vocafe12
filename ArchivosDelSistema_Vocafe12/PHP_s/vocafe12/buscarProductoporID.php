<?php  
    require_once('conexion.php');
    $query = $conexion->prepare("SELECT NombreP,Descripcion,Precio,Costo,Foto,Categoria FROM productos");
    $query->execute();
    $query->bind_result($NombreP,$Descripcion,$Precio,$Costo,$Foto,$Categoria);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['NombreP'] = $NombreP;
        $temp['Descripcion'] = $Descripcion;
        $temp['Precio'] = $Precio;
        $temp['Costo'] = $Costo;
        $temp['Foto'] = $Foto;
        $temp['Categoria'] = $Categoria;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>