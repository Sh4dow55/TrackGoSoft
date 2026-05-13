-- 1. REINICIO TOTAL DE LA BASE DE DATOS
DROP SCHEMA IF EXISTS trackgo;
CREATE SCHEMA trackgo DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE trackgo;

-- -----------------------------------------------------
-- Transporte
-- -----------------------------------------------------

CREATE TABLE transporte (
  placa VARCHAR(10) NOT NULL,
  tipo VARCHAR(50) NOT NULL,
  marca VARCHAR(50) NOT NULL,
  modelo VARCHAR(50) NOT NULL,
  PRIMARY KEY (placa)
);

-- -----------------------------------------------------
-- Direccion
-- -----------------------------------------------------

CREATE TABLE direccion (
  idDireccion INT NOT NULL AUTO_INCREMENT,
  departamento VARCHAR(100) NOT NULL,
  provincia VARCHAR(100) NOT NULL,
  distrito VARCHAR(100) NOT NULL,
  codPostal VARCHAR(10),
  calleNumero VARCHAR(150) NOT NULL,
  referencia VARCHAR(255),
  PRIMARY KEY (idDireccion)
);

-- -----------------------------------------------------
-- Empleado
-- -----------------------------------------------------

CREATE TABLE empleado (
  idUsuario INT NOT NULL AUTO_INCREMENT,
  codigoEmpleado VARCHAR(15) NOT NULL,
  dni VARCHAR(8) NOT NULL,
  nombres VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  correo VARCHAR(150) NOT NULL,
  contrasenaHash VARCHAR(255) NOT NULL,
  telefono VARCHAR(15),
  fechaRegistro DATE NOT NULL,
  estado TINYINT NOT NULL DEFAULT TRUE,
  cargo ENUM('TRANSPORTISTA', 'RECEPCIONISTA') NOT NULL,
  licencia VARCHAR(16),
  turno ENUM('MANHANA', 'TARDE', 'NOCHE'),

  PRIMARY KEY (idUsuario),
  UNIQUE (codigoEmpleado),
  UNIQUE (dni),
  UNIQUE (correo)
);

-- -----------------------------------------------------
-- Administrador
-- -----------------------------------------------------

CREATE TABLE administrador (
  idUsuario INT NOT NULL AUTO_INCREMENT,
  codigoEmpleado VARCHAR(15) NOT NULL,
  dni VARCHAR(8) NOT NULL,
  nombres VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  correo VARCHAR(150) NOT NULL,
  contrasenaHash VARCHAR(255) NOT NULL,
  telefono VARCHAR(15),
  fechaRegistro DATE NOT NULL,
  estado TINYINT NOT NULL DEFAULT TRUE,
  cargo ENUM('ADMINISTRADOR') NOT NULL,
  nivelDeAcceso VARCHAR(15) NOT NULL,
  isManager TINYINT NOT NULL DEFAULT FALSE,

  PRIMARY KEY (idUsuario),
  UNIQUE (codigoEmpleado),
  UNIQUE (dni),
  UNIQUE (correo)
);

-- -----------------------------------------------------
-- Empresa
-- -----------------------------------------------------

CREATE TABLE empresa (
  idEmpresa INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(60) NOT NULL,
  RUC VARCHAR(11) NOT NULL,
  direccion VARCHAR(100) NOT NULL,
  sector VARCHAR(100) NOT NULL,
  fechaFundacion DATE,
  
  PRIMARY KEY (idEmpresa)

);

-- -----------------------------------------------------
-- Pedido
-- -----------------------------------------------------

CREATE TABLE pedido (
  idPedido INT NOT NULL AUTO_INCREMENT,
  codigoPedido VARCHAR(15),
  destinatario VARCHAR(150) NOT NULL,
  fechaCreacion DATE NOT NULL,
  fechaActualizacion DATE NOT NULL,
  tarifaEnvio DECIMAL(10,2) NOT NULL,
  estado ENUM('SIN_REGISTRAR','EN_AGENCIA', 'SALIDA_A_RUTA', 'ENTREGADO') NOT NULL,

  idDireccion INT NOT NULL,
  placa VARCHAR(10),
  idEmpleado INT,
  idAdministrador INT,
  idEmpresa INT,

  PRIMARY KEY (idPedido),
  UNIQUE (codigoPedido),

  CONSTRAINT fk_pedido_direccion
  FOREIGN KEY (idDireccion)
  REFERENCES direccion(idDireccion)
  ON UPDATE CASCADE,

  CONSTRAINT fk_pedido_transporte
  FOREIGN KEY (placa)
  REFERENCES transporte(placa)
  ON DELETE SET NULL
  ON UPDATE CASCADE,


  CONSTRAINT fk_pedido_empresa
  FOREIGN KEY (idEmpresa)
  REFERENCES empresa(idEmpresa)
  ON DELETE SET NULL,

  CONSTRAINT fk_pedido_empleado
  FOREIGN KEY (idEmpleado)
  REFERENCES empleado(idUsuario)
  ON UPDATE CASCADE,

  CONSTRAINT fk_pedido_administrador
  FOREIGN KEY (idAdministrador)
  REFERENCES administrador(idUsuario)
  ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- DetalleDePedido
-- -----------------------------------------------------

CREATE TABLE detalleDePedido (
  idDetalle INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(255) NOT NULL,
  cantidad INT NOT NULL,
  idPedido INT NOT NULL,

  PRIMARY KEY (idDetalle),

  CONSTRAINT fk_detalle_pedido
  FOREIGN KEY (idPedido)
  REFERENCES pedido(idPedido)
  ON DELETE CASCADE
);

-- -----------------------------------------------------
-- HistorialDePedido
-- -----------------------------------------------------

CREATE TABLE historialDePedido (
  idHistorial INT NOT NULL AUTO_INCREMENT,
  instante TIMESTAMP NOT NULL,

  idEmpleado INT,
  idAdministrador INT,

  estado ENUM('EN_AGENCIA', 'SALIDA_A_RUTA', 'ENTREGADO') NOT NULL,
  placa VARCHAR(10),
  duracionSegundos BIGINT DEFAULT 0,
  observacionIncidencia VARCHAR(500),
  idPedido INT NOT NULL,

  PRIMARY KEY (idHistorial),

  CONSTRAINT fk_historial_pedido
  FOREIGN KEY (idPedido)
  REFERENCES pedido(idPedido)
  ON DELETE CASCADE,

  CONSTRAINT fk_historial_transporte
  FOREIGN KEY (placa)
  REFERENCES transporte(placa)
  ON DELETE SET NULL,

  CONSTRAINT fk_historial_empleado
  FOREIGN KEY (idEmpleado)
  REFERENCES empleado(idUsuario),

  CONSTRAINT fk_historial_admin
  FOREIGN KEY (idAdministrador)
  REFERENCES administrador(idUsuario)
);

-- -----------------------------------------------------
-- RegistroDeTracking
-- -----------------------------------------------------

CREATE TABLE registroDeTracking (
  idRegistro INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (idRegistro)
);

-- -----------------------------------------------------
-- RegistroTrackingPedido
-- -----------------------------------------------------

CREATE TABLE registroTrackingPedido (
  idRegistro INT NOT NULL,
  idPedido INT NOT NULL,

  PRIMARY KEY (idRegistro, idPedido),

  CONSTRAINT fk_rtp_registro
  FOREIGN KEY (idRegistro)
  REFERENCES registroDeTracking(idRegistro)
  ON DELETE CASCADE,

  CONSTRAINT fk_rtp_pedido
  FOREIGN KEY (idPedido)
  REFERENCES pedido(idPedido)
  ON DELETE CASCADE
);