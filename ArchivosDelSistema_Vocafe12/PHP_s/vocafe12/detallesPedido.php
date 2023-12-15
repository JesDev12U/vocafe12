<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $CodigoPedido = $_POST['CodigoPedido'];
        $IdCliente = $_POST['IdCliente'];
        $NombreProducto = $_POST['NombreProducto'];
        $Cantidad = $_POST['Cantidad'];
        $Importe = $_POST['Importe'];
        $Detalles = $_POST['Detalles'];
        $Foto = $_POST['Foto'];
        $query = "INSERT INTO detallepedidos (CodigoPedido,IdCliente,NombreProducto,Cantidad,Importe,Detalles,Foto) VALUES('$CodigoPedido','$IdCliente','$NombreProducto','$Cantidad','$Importe','$Detalles','$Foto')";
        $result = $conexion->query($query);
        if($result==TRUE){
            echo "Se han agregado los detalles del pedido";
        } else{
            echo "Error";
        }
        $conexion->close();
    }
?>