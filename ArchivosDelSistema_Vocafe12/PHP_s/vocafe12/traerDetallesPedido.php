<?php  
    require_once('conexion.php');
    $CodigoPedido = $_GET['CodigoPedido'];
    $query = $conexion->prepare("SELECT NombreProducto,Cantidad,Importe,Detalles,Foto FROM detallepedidos WHERE CodigoPedido = '$CodigoPedido'");
    $query->execute();
    $query->bind_result($NombreProducto,$Cantidad,$Importe,$Detalles,$Foto);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['NombreProducto'] = $NombreProducto;
        $temp['Cantidad'] = $Cantidad;
        $temp['Importe'] = $Importe;
        $temp['Detalles'] = $Detalles;
        $temp['Foto'] = $Foto;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>