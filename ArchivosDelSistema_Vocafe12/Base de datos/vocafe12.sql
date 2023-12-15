-- phpMyAdmin SQL Dump
-- version 5.3.0-dev+20220607.1684aa8b89
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-06-2022 a las 22:46:00
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `vocafe12`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `IdCliente` int(11) NOT NULL,
  `NombreProducto` varchar(255) NOT NULL,
  `Detalles` varchar(255) NOT NULL,
  `Cantidad` int(11) NOT NULL,
  `Total` varchar(255) NOT NULL,
  `Foto` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `IdCliente` int(11) NOT NULL,
  `NombreC` varchar(255) NOT NULL,
  `ApellidoPaternoC` varchar(255) NOT NULL,
  `ApellidoMaternoC` varchar(255) NOT NULL,
  `CorreoE` varchar(255) NOT NULL,
  `PasswordC` varchar(255) NOT NULL,
  `Estado` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`IdCliente`, `NombreC`, `ApellidoPaternoC`, `ApellidoMaternoC`, `CorreoE`, `PasswordC`, `Estado`) VALUES
(1, 'Jesús Antonio', 'López', 'Bandala', 'lopezbandalajesusantonio@gmail.com', 'Jesus+2004', 'Habilitado'),
(2, 'Marcos Isaid', 'López', 'Ramos', 'marcos.ramos@gmail.com', 'Marcos+2004', 'Habilitado'),
(3, 'Ernesto', 'Pérez', 'Pérez', 'ernesto@gmail.com', 'Ernesto1999', 'Habilitado'),
(4, 'Débora Estefanía', 'Ibarra', 'Zendejas', 'debora.ibarra@gmail.com', 'Debora111', 'Habilitado'),
(6, 'Fernando', 'Pérez', 'Pérez', 'fernando@gmail.com', 'Fernando4444', 'Habilitado'),
(7, 'Humberto', 'Taboada', 'Guitierrez', 'humberto@gmail.com', 'humbertoT', 'Habilitado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallepedidos`
--

CREATE TABLE `detallepedidos` (
  `CodigoPedido` varchar(255) NOT NULL,
  `IdCliente` varchar(255) NOT NULL,
  `NombreProducto` varchar(255) NOT NULL,
  `Cantidad` varchar(255) NOT NULL,
  `Importe` varchar(255) NOT NULL,
  `Detalles` varchar(255) NOT NULL,
  `Foto` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `detallepedidos`
--

INSERT INTO `detallepedidos` (`CodigoPedido`, `IdCliente`, `NombreProducto`, `Cantidad`, `Importe`, `Detalles`, `Foto`) VALUES
('5', '1', 'Tacos dorados', '3', '31.5', 'Con harta salsa verde', 'http://192.168.1.76/vocafe12/uploads/Tacosdorados.jpg'),
('5', '1', 'Coca cola', '1', '10.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg'),
('6', '1', 'Tacos dorados', '2', '21.0', 'Con salsa verde de la que pica mucho', 'http://192.168.1.76/vocafe12/uploads/Tacosdorados.jpg'),
('6', '1', 'Tortas', '1', '25.0', '-', 'http://192.168.1.76/vocafe12/uploads/Tortas.jpg'),
('6', '1', 'Coca cola', '3', '30.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg'),
('7', '2', 'Tortas', '3', '75.0', 'Tortas de huevo', 'http://192.168.1.76/vocafe12/uploads/Tortas.jpg'),
('7', '2', 'Coca cola', '2', '20.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg'),
('7', '2', 'Fanta', '1', '12.5', '-', 'http://192.168.1.76/vocafe12/uploads/Fanta.jpg'),
('8', '1', 'Sopes', '3', '45.0', 'Con salsa verde y sin cebolla', 'http://192.168.1.76/vocafe12/uploads/Sopes.jpg'),
('8', '1', 'Fanta', '1', '12.5', '-', 'http://192.168.1.76/vocafe12/uploads/Fanta.jpg'),
('9', '2', 'Coca cola', '1', '10.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg'),
('10', '1', 'Tacos dorados', '3', '31.5', '-', 'http://192.168.1.76/vocafe12/uploads/Tacosdorados.jpg'),
('10', '1', 'Fanta', '2', '25.0', '-', 'http://192.168.1.76/vocafe12/uploads/Fanta.jpg'),
('10', '1', 'Tortas', '2', '50.0', '-', 'http://192.168.1.76/vocafe12/uploads/Tortas.jpg'),
('11', '1', 'Coca cola', '2', '20.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg'),
('11', '1', 'Doritos dinamita', '1', '15.0', '-', 'http://192.168.1.76/vocafe12/uploads/Doritosdinamita.jpg'),
('11', '1', 'Cheetos extra flamin hot', '1', '16.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cheetosextraflaminhot.jpg'),
('12', '2', 'Chicles Trident menta', '3', '39.0', '-', 'http://192.168.1.76/vocafe12/uploads/ChiclesTridentmenta.jpg'),
('12', '2', 'Tacos dorados', '3', '31.5', 'Con mucha salsa verde', 'http://192.168.1.76/vocafe12/uploads/Tacosdorados.jpg'),
('12', '2', 'Sopes', '5', '75.0', 'Sin cebolla', 'http://192.168.1.76/vocafe12/uploads/Sopes.jpg'),
('13', '2', 'Sopes', '4', '60.0', 'Sin cebolla y con mucha salsa verde', 'http://192.168.1.76/vocafe12/uploads/Sopes.jpg'),
('13', '2', 'Coca cola', '3', '30.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg'),
('13', '2', 'Chicles Trident menta', '1', '13.0', '-', 'http://192.168.1.76/vocafe12/uploads/ChiclesTridentmenta.jpg'),
('14', '1', 'Chicles Trident menta', '4', '52.0', '-', 'http://192.168.1.76/vocafe12/uploads/ChiclesTridentmenta.jpg'),
('14', '1', 'Fanta', '4', '50.0', '-', 'http://192.168.1.76/vocafe12/uploads/Fanta.jpg'),
('15', '1', 'Coca cola', '2', '20.0', '-', 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg'),
('15', '1', 'Sopes', '2', '30.0', '-', 'http://192.168.1.76/vocafe12/uploads/Sopes.jpg'),
('16', '1', 'Chicles Trident menta', '6', '78.0', '-', 'http://192.168.1.76/vocafe12/uploads/ChiclesTridentmenta.jpg'),
('16', '1', 'Tortas', '5', '125.0', 'Torta como la del chavo =D', 'http://192.168.1.76/vocafe12/uploads/Tortas.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `IdEmpleado` int(11) NOT NULL,
  `NombreE` varchar(255) NOT NULL,
  `ApellidoPaternoE` varchar(255) NOT NULL,
  `ApellidoMaternoE` varchar(255) NOT NULL,
  `CorreoElectronico` varchar(255) NOT NULL,
  `PasswordE` varchar(255) NOT NULL,
  `Cargo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`IdEmpleado`, `NombreE`, `ApellidoPaternoE`, `ApellidoMaternoE`, `CorreoElectronico`, `PasswordE`, `Cargo`) VALUES
(1, 'Fernanda', 'Muñoz', 'Orozpe', 'fernanda@gmail.com', 'Fernanda+2003', 'Ventas'),
(2, 'Alexis', 'Ramos', 'López', 'alexis.ramos@gmail.com', 'alexis1999', 'Ventas'),
(3, 'Matías', 'Segura', 'De la Fuente', 'matias.segura@gmail.com', 'MatiasSegura3000', 'Ventas'),
(4, 'Alberto', 'López', 'López', 'alberto@gmail.com', 'Alberto3000', 'Ventas'),
(5, 'Rafael', 'Ramos', 'Ramos', 'rafa.ramos@gmail.com', 'Rafael1000', 'Ventas'),
(6, 'Jesús', 'López', 'Pérez', 'jesus@gmail.com', 'jesus123', 'Ventas'),
(7, 'Daniel', 'Torreón', 'Martinez', 'daniel@gmail.com', 'daniel1', 'Caja');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `CodigoPedido` int(11) NOT NULL,
  `Fecha` varchar(255) NOT NULL,
  `Hora` varchar(255) NOT NULL,
  `statusPedido` varchar(30) NOT NULL,
  `IdCliente` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`CodigoPedido`, `Fecha`, `Hora`, `statusPedido`, `IdCliente`) VALUES
(5, '23/5/2022', '11:20:19 p. m.', 'Completado', '1'),
(6, '24/5/2022', '12:46:50 a. m.', 'Completado', '1'),
(7, '24/5/2022', '1:42:32 p. m.', 'Completado', '2'),
(8, '24/5/2022', '1:58:43 p. m.', 'Completado', '1'),
(9, '24/5/2022', '2:04:37 p. m.', 'Completado', '2'),
(10, '29/5/2022', '2:53:08 p. m.', 'Completado', '1'),
(11, '29/5/2022', '10:08:38 p. m.', 'Completado', '1'),
(12, '30/5/2022', '11:01:07 p. m.', 'Completado', '2'),
(13, '30/5/2022', '11:38:08 p. m.', 'Completado', '2'),
(14, '30/5/2022', '11:39:44 p. m.', 'Completado', '1'),
(15, '7/6/2022', '11:02:18 p. m.', 'Completado', '1'),
(16, '8/6/2022', '3:19:19 p. m.', 'Completado', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `IdProducto` int(11) NOT NULL,
  `NombreP` varchar(255) NOT NULL,
  `Descripcion` varchar(255) NOT NULL,
  `Precio` double NOT NULL,
  `Costo` double NOT NULL,
  `Foto` varchar(255) NOT NULL,
  `Categoria` varchar(255) NOT NULL,
  `Estado` varchar(255) NOT NULL,
  `Ocupado` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`IdProducto`, `NombreP`, `Descripcion`, `Precio`, `Costo`, `Foto`, `Categoria`, `Estado`, `Ocupado`) VALUES
(1, 'Tacos dorados', 'Tacos dorados hechos con queso, papa, jamón, etc.', 10.5, 4, 'http://192.168.1.76/vocafe12/uploads/Tacosdorados.jpg', 'Guisado', 'Habilitado', 'Si'),
(2, 'Coca cola', 'Refresco coca cola de 500 ml', 10, 8, 'http://192.168.1.76/vocafe12/uploads/Cocacola.jpg', 'Producto', 'Habilitado', 'Si'),
(3, 'Fanta', 'Refresco Fanta de 500 ml', 12.5, 10, 'http://192.168.1.76/vocafe12/uploads/Fanta.jpg', 'Producto', 'Habilitado', 'Si'),
(4, 'Tortas', 'Tortas de huevo y jamón', 25, 16.5, 'http://192.168.1.76/vocafe12/uploads/Tortas.jpg', 'Guisado', 'Habilitado', 'Si'),
(6, 'Sopes', 'Sopes con frijoles, salsa verde o roja y queso', 15, 10, 'http://192.168.1.76/vocafe12/uploads/Sopes.jpg', 'Guisado', 'Habilitado', 'Si'),
(9, 'Doritos dinamita', 'Doritos dinamita de 65 gramos', 15, 10, 'http://192.168.1.76/vocafe12/uploads/Doritosdinamita.jpg', 'Producto', 'Deshabilitado', 'Si'),
(10, 'Cheetos extra flamin hot', 'Cheetos extra flamin hot de 52 gramos', 16, 11.5, 'http://192.168.1.76/vocafe12/uploads/Cheetosextraflaminhot.jpg', 'Producto', 'Deshabilitado', 'Si'),
(12, 'Chicles Trident menta', 'Chicles de la marca Trident de 12 pastillas', 13, 10, 'http://192.168.1.76/vocafe12/uploads/ChiclesTridentmenta.jpg', 'Producto', 'Habilitado', 'Si'),
(14, 'Nito', 'Pan dulce de chocolate de la marca Bimbo de 62 gramos', 10, 7, 'http://192.168.1.76/vocafe12/uploads/Nito.jpg', 'Producto', 'Habilitado', 'No');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`IdCliente`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`IdEmpleado`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`CodigoPedido`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`IdProducto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `IdCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `IdEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `CodigoPedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `IdProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;



