<?php  
    require_once('conexion.php');
    $Estado = 'Habilitado';
    $query = $conexion->prepare("SELECT IdCliente,NombreC,ApellidoPaternoC,ApellidoMaternoC,CorreoE,PasswordC FROM clientes WHERE Estado = '$Estado'");
    $query->execute();
    $query->bind_result($IdCliente,$NombreC,$ApellidoPaternoC,$ApellidoMaternoC,$CorreoE,$PasswordC);
    $productos = array();
    while($query->fetch()){
        $temp = array();
        $temp['IdCliente'] = $IdCliente;
        $temp['NombreC'] = $NombreC;
        $temp['ApellidoPaternoC'] = $ApellidoPaternoC;
        $temp['ApellidoMaternoC'] = $ApellidoMaternoC;
        $temp['CorreoE'] = $CorreoE;
        $temp['PasswordC'] = $PasswordC;
        array_push($productos,$temp);
    }
    echo json_encode($productos);
?>