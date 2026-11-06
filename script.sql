CREATE DATABASE IF NOT EXISTS bd_cliente;
USE bd_cliente;

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20)
    );

CREATE DATABASE IF NOT EXISTS bd_repartidor;
USE bd_repartidor;

CREATE TABLE IF NOT EXISTS repartidores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    vehiculo VARCHAR(50) NOT NULL,
    telefono VARCHAR(20),
    disponible BOOLEAN DEFAULT TRUE
    );

CREATE DATABASE IF NOT EXISTS bd_entrega;
USE bd_entrega;

CREATE TABLE IF NOT EXISTS entregas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_repartidor BIGINT NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    costo DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha DATETIME NOT NULL
    );