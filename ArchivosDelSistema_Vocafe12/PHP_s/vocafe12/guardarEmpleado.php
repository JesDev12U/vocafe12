<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $NombreE = $_POST['NombreE'];
        $ApellidoPaternoE = $_POST['ApellidoPaternoE'];
        $ApellidoMaternoE = $_POST['ApellidoMaternoE'];
        $CorreoElectronico = $_POST['CorreoElectronico'];
        $PasswordE = $_POST['PasswordE'];
        $Cargo = $_POST['Cargo'];
        $query = "INSERT INTO empleados (NombreE,ApellidoPaternoE,ApellidoMaternoE,CorreoElectronico,PasswordE,Cargo) VALUES('$NombreE','$ApellidoPaternoE','$ApellidoMaternoE','$CorreoElectronico','$PasswordE','$Cargo')";
        $result = $conexion->query($query);

        if($result==TRUE){
            echo "El empleado se ha creado";
        } else{
            echo "Error";
        }
        $conexion->close();
    }
?>