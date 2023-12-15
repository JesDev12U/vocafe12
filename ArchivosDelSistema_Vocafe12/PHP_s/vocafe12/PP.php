<?php  
    require_once('conexion.php');
    $status = 'Pendiente';
    $query = $conexion->prepare("SELECT CodigoPedido,Fecha,Hora,IdCliente FROM pedidos WHERE statusPedido = '$status'");
    $query->execute();
    $query->bind_result($CodigoPedido,$Fecha,$Hora,$IdCliente);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['CodigoPedido'] = $CodigoPedido;
        $temp['Fecha'] = $Fecha;
        $temp['Hora'] = $Hora;
        $temp['IdCliente'] = $IdCliente;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>