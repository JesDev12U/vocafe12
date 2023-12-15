<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $NombreP = $_POST['NombreP'];
        $Ocupado = 'Si';
        $query="UPDATE productos SET Ocupado = '$Ocupado' WHERE NombreP = '$NombreP'";
        if(mysqli_query($conexion,$query)){
            echo "Se ha marcado como SI en ocupado el producto";
            mysqli_close($conexion);
        }
    }
?>