<?php
    if($_SERVER['REQUEST_METHOD']=='GET'){
        require_once('conexion.php');
        $IdCliente=$_GET['IdCliente'];
        $query = $conexion->prepare("SELECT NombreProducto,Detalles,Cantidad,Total,Foto FROM carrito WHERE IdCliente = '$IdCliente'");
        $query->execute();
        $query->bind_result($NombreProducto,$Detalles,$Cantidad,$Total,$Foto);
        $productos = array();
        while($query->fetch()){
            $temp = array();
            $temp['NombreProducto'] = $NombreProducto;
            $temp['Detalles'] = $Detalles;
            $temp['Cantidad'] = $Cantidad;
            $temp['Total'] = $Total;
            $temp['Foto'] = $Foto;
            array_push($productos,$temp);
        }
        echo json_encode($productos);
    }
?>