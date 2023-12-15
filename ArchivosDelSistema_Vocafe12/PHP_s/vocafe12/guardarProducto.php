<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $NombreP = $_POST['NombreP'];
        $Descripcion = $_POST['Descripcion'];
        $Precio = $_POST['Precio'];
        $Costo = $_POST['Costo'];
        $Foto = $_POST['Foto'];
        $Categoria = $_POST['Categoria'];
        $Estado = 'Habilitado';
        $Ocupado = 'No';
        //Ruta donde se guardan las imagenes

        $nombrelimpio = preg_replace("/[[:space:]]/","",trim($NombreP));
        $path = "uploads/$nombrelimpio.jpg";

        $actualpath = "http://192.168.1.76/vocafe12/$path";

        $query = "INSERT INTO productos (NombreP,Descripcion,Precio,Costo,Foto,Categoria,Estado,Ocupado) VALUES('$NombreP','$Descripcion','$Precio','$Costo','$actualpath','$Categoria','$Estado','$Ocupado')";
        
        if(mysqli_query($conexion,$query)){
            file_put_contents($path,base64_decode($Foto));

            echo "Los datos han sido guardados correctamente";
            mysqli_close($conexion);
        } else{
            echo "Error";
        }
    }
?>