<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdCliente=$_POST['IdCliente'];
        $query = "DELETE FROM carrito WHERE IdCliente='$IdCliente'";
        $result = $conexion->query($query);
        if($conexion->affected_rows>0){
            if($result===TRUE){
                echo "Se ha vaceado el carrito";
            }
        } else{
            echo "not found any rows";
        }
        $conexion->close();
    }
?>