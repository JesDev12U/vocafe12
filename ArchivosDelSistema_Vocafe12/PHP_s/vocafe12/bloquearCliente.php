<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdCliente=$_POST['IdCliente'];
        $Estado = 'Bloqueado';
        $query="UPDATE clientes SET Estado='$Estado' WHERE IdCliente='$IdCliente'";
        if(mysqli_query($conexion,$query)){
            echo "Se ha bloqueado al cliente";
            mysqli_close($conexion);
        }
    }
?>