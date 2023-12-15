<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdCliente=$_POST['IdCliente'];
        $NombreProducto=$_POST['NombreProducto'];
        $Detalles=$_POST['Detalles'];
        $query = "DELETE FROM carrito WHERE IdCliente='$IdCliente' AND NombreProducto='$NombreProducto' AND Detalles='$Detalles'";
        $result = $conexion->query($query);
        if($conexion->affected_rows>0){
            if($result===TRUE){
                echo "El pedido se ha eliminado";
            }
        } else{
            echo "not found any rows";
        }
        $conexion->close();
    }
?>