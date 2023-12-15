<?php  
    require_once('conexion.php');
    $CodigoPedido = $_GET['CodigoPedido'];
    $query = $conexion->prepare("SELECT Importe FROM detallepedidos WHERE CodigoPedido = '$CodigoPedido'");
    $query->execute();
    $query->bind_result($Importe);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['Importe'] = $Importe;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>