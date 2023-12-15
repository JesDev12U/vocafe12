<?php
    if($_SERVER['REQUEST_METHOD']=='GET'){
        require_once("conexion.php");
        $CorreoElectronico=$_GET['CorreoElectronico'];
        $query="SELECT * FROM empleados WHERE CorreoElectronico = '$CorreoElectronico'";
        $result = $conexion->query($query);
        if($conexion->affected_rows>0){
            while($row=$result->fetch_assoc()){
                $array=$row;
            }
            echo json_encode($array);
        } else{
            echo "not found any rows";
        }
        $result->close();
        $conexion->close();
    }
?>