<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $Fecha = $_POST['Fecha'];
        $Hora = $_POST['Hora'];
        $statusPedido = $_POST['statusPedido'];
        $IdCliente = $_POST['IdCliente'];
        $query = "INSERT INTO pedidos (Fecha,Hora,statusPedido,IdCliente) VALUES('$Fecha','$Hora','$statusPedido','$IdCliente')";
        $result = $conexion->query($query);
        if($result==TRUE){
            echo "Se ha agregado el pedido";
        } else{
            echo "Error";
        }
        $conexion->close();
    }
?>