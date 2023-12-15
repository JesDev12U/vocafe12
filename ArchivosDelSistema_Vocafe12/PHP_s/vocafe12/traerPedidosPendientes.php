<?php  
    require_once('conexion.php');
    $IdCliente = $_GET['IdCliente'];
    $status = 'Pendiente';
    $query = $conexion->prepare("SELECT CodigoPedido,Fecha,Hora,statusPedido FROM pedidos WHERE IdCliente = '$IdCliente' AND statusPedido = '$status'");
    $query->execute();
    $query->bind_result($CodigoPedido,$Fecha,$Hora,$statusPedido);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['CodigoPedido'] = $CodigoPedido;
        $temp['Fecha'] = $Fecha;
        $temp['Hora'] = $Hora;
        $temp['statusPedido'] = $statusPedido;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>