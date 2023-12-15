<?php  
    if($_SERVER['REQUEST_METHOD']=='GET'){
        require_once("conexion.php");
        $Fecha=$_GET['Fecha'];
        $Hora=$_GET['Hora'];
        $IdCliente=$_GET['IdCliente'];
        $query="SELECT * FROM pedidos WHERE Fecha = '$Fecha' AND Hora = '$Hora' AND IdCliente='$IdCliente'";
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