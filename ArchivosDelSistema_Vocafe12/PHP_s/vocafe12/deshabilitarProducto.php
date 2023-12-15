<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $NombreP = $_POST['NombreP'];
        $Estado = 'Deshabilitado';
        $query="UPDATE productos SET Estado = '$Estado' WHERE NombreP = '$NombreP'";
        if(mysqli_query($conexion,$query)){
            echo "Se ha deshabilitado el producto";
            mysqli_close($conexion);
        }
    }
?>