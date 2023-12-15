<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdCliente=$_POST['IdCliente'];
        $Estado = 'Habilitado';
        $query="UPDATE clientes SET Estado='$Estado' WHERE IdCliente='$IdCliente'";
        if(mysqli_query($conexion,$query)){
            echo "Se ha desbloqueado al cliente";
            mysqli_close($conexion);
        }
    }
?>