<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdCliente = $_POST['IdCliente'];
        $NombreProducto = $_POST['NombreProducto'];
        $Detalles = $_POST['Detalles'];
        $Cantidad = $_POST['Cantidad'];
        $Total = $_POST['Total'];
        $Foto = $_POST['Foto'];
        $query = "INSERT INTO carrito (IdCliente,NombreProducto,Detalles,Cantidad,Total,Foto) VALUES('$IdCliente','$NombreProducto','$Detalles','$Cantidad','$Total','$Foto')";
        $result = $conexion->query($query);
        if($result==TRUE){
            echo "Se ha agregado al carrito";
        } else{
            echo "Error";
        }
        $conexion->close();
    }
?>