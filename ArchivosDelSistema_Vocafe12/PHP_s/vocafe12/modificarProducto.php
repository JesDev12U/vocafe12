<?php
    if($_SERVER['REQUEST_METHOD']=='POST'){
        require_once("conexion.php");
        $IdProducto=$_POST['IdProducto'];
        $NombreP=$_POST['NombreP'];
        $Descripcion = $_POST['Descripcion'];
        $Precio = $_POST['Precio'];
        $Costo = $_POST['Costo'];
        $Foto = $_POST['Foto'];
        $Categoria = $_POST['Categoria'];

        //Ruta en donde se guardan las imagenes

        $nombrelimpio = preg_replace("/[[:space:]]/","",trim($NombreP));
        $path = "uploads/$nombrelimpio.jpg";

        $actualpath = "http://192.168.1.76/vocafe12/$path";

        $query="UPDATE productos SET NombreP='$NombreP',Descripcion='$Descripcion',Precio='$Precio',Costo='$Costo',Foto='$actualpath',Categoria='$Categoria' WHERE IdProducto='$IdProducto'";
        
        if(mysqli_query($conexion,$query)){
            file_put_contents($path,base64_decode($Foto));

            echo "Los datos han sido guardados correctamente";
            mysqli_close($conexion);
        }

        /*$result = $conexion->query($query);
        if($conexion->affected_rows>0){
            if($result===TRUE){
                echo "El producto se ha actualizado satisfactoriamente";
            } else{
                echo "Error";
            }
        } else{
            echo "not found any rows";
        }
        $conexion->close();*/
    }
?>