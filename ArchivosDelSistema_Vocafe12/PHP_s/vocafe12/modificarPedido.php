<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdCliente=$_POST['IdCliente'];
        $NombreProducto=$_POST['NombreProducto'];
        $Detalles=$_POST['Detalles'];
        $Cantidad=$_POST['Cantidad'];
        $Total = $_POST['Total'];
        $query="UPDATE carrito SET Detalles='$Detalles',Cantidad='$Cantidad',Total='$Total' WHERE IdCliente='$IdCliente' AND NombreProducto='$NombreProducto'";
        if(mysqli_query($conexion,$query)){
            echo "Los datos han sido guardados correctamente";
            mysqli_close($conexion);
        }
    }
?>