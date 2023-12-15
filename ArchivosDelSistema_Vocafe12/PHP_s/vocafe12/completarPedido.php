<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $CodigoPedido = $_POST['CodigoPedido'];
        $statusPedido = $_POST['statusPedido'];
        $query="UPDATE pedidos SET statusPedido = '$statusPedido' WHERE CodigoPedido ='$CodigoPedido'";
        if(mysqli_query($conexion,$query)){
            echo "Se ha completado el pedido";
            mysqli_close($conexion);
        }
    }
?>