<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $NombreC = $_POST['NombreC'];
        $ApellidoPaternoC = $_POST['ApellidoPaternoC'];
        $ApellidoMaternoC = $_POST['ApellidoMaternoC'];
        $CorreoE = $_POST['CorreoE'];
        $PasswordC = $_POST['PasswordC'];
        $Estado = 'Habilitado';
        $query = "INSERT INTO clientes (NombreC,ApellidoPaternoC,ApellidoMaternoC,CorreoE,PasswordC,Estado) VALUES('$NombreC','$ApellidoPaternoC','$ApellidoMaternoC','$CorreoE','$PasswordC','$Estado')";
        $result = $conexion->query($query);

        if($result==TRUE){
            echo "El cliente se ha creado";
        } else{
            echo "Error";
        }
        $conexion->close();
    }
?>