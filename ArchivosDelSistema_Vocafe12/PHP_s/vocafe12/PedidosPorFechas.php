<?php  
    require_once('conexion.php');
    $status = 'Completado';
    $FechaInicio = $_GET['FechaInicio'];
    $FechaFinal = $_GET['FechaFinal'];
    $query = $conexion->prepare("SELECT CodigoPedido,Fecha,Hora,IdCliente FROM pedidos WHERE statusPedido = '$status' AND Fecha BETWEEN '$FechaInicio' AND '$FechaFinal'");
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