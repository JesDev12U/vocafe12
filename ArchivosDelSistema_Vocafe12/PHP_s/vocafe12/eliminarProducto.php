<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $NombreP=$_POST['NombreP'];
        $Ocupado = 'No';
        $query = "DELETE FROM productos WHERE NombreP='$NombreP' AND Ocupado = '$Ocupado'";
        $result = $conexion->query($query);
        if($conexion->affected_rows>0){
            if($result===TRUE){
                echo "El producto se ha eliminado";
            }
        } else{
            echo "El producto no se puede eliminar debido a que está en uno o varios pedidos, sin embargo, lo puedes habilitar/deshabilitar";
        }
        $conexion->close();
    }
?>