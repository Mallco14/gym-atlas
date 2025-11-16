# Para inicializar el proyecto :

1. Crear la bd en oracle :
create database db_gym_atlas

-- Tabla: clientes
CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    dni VARCHAR(8) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefono VARCHAR(15),
    fecha_nacimiento DATE,
    direccion TEXT,
    contacto_emergencia VARCHAR(200),
    notas_medicas TEXT,
    foto_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: planes
CREATE TYPE estado_plan AS ENUM ('activo', 'inactivo');

CREATE TABLE planes (
    id_plan SERIAL PRIMARY KEY,
    nombre_plan VARCHAR(50) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    duracion_dias INT NOT NULL,
    acceso_clases BOOLEAN DEFAULT FALSE,
    acceso_maquinas BOOLEAN DEFAULT TRUE,
    estado estado_plan DEFAULT 'activo',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: membresias
CREATE TYPE estado_membresia AS ENUM ('activa', 'vencida', 'cancelada');

CREATE TABLE membresias (
    id_membresia SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL REFERENCES clientes(id_cliente) ON DELETE CASCADE,
    id_plan INT NOT NULL REFERENCES planes(id_plan),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado estado_membresia DEFAULT 'activa',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_fecha_fin ON membresias(fecha_fin);
CREATE INDEX idx_estado ON membresias(estado);

-- Tabla: ingresos
CREATE TABLE ingresos (
    id_ingreso SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL REFERENCES clientes(id_cliente),
    fecha_ingreso DATE NOT NULL,
    hora_ingreso TIME NOT NULL,
    recepcionista VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_fecha_ingreso ON ingresos(fecha_ingreso);

-- Tabla: pagos
CREATE TYPE metodo_pago AS ENUM ('efectivo', 'tarjeta', 'transferencia', 'yape', 'plin');

CREATE TABLE pagos (
    id_pago SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL REFERENCES clientes(id_cliente),
    id_membresia INT NOT NULL REFERENCES membresias(id_membresia),
    monto DECIMAL(8,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    metodo_pago metodo_pago,
    concepto VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_fecha_pago ON pagos(fecha_pago);

-- Tabla: usuarios_sistema
CREATE TYPE rol_usuario AS ENUM ('admin', 'recepcionista', 'entrenador');
CREATE TYPE estado_usuario AS ENUM ('activo', 'inactivo');

CREATE TABLE usuarios_sistema (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    rol rol_usuario NOT NULL,
    estado estado_usuario DEFAULT 'activo',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



2. Tabla creada