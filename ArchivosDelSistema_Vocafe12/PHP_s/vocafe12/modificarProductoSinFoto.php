<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdProducto=$_POST['IdProducto'];
        $NombreP = $_POST['NombreP'];
        $Descripcion = $_POST['Descripcion'];
        $Precio = $_POST['Precio'];
        $Costo = $_POST['Costo'];
        $Categoria = $_POST['Categoria'];
        $query="UPDATE productos SET NombreP='$NombreP',Descripcion='$Descripcion',Precio='$Precio',Costo='$Costo',Categoria='$Categoria' WHERE IdProducto='$IdProducto'";
        $result = $conexion->query($query);
        if($conexion->affected_rows>0){
            if($result===TRUE){
                echo "Update sucessfully";
            } else{
                echo "Error";
            }
        } 
        $conexion->close();
    }
?>