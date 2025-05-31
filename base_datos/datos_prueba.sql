USE banco;

-- Persona
INSERT INTO persona (nombre, genero, identificacion, direccion, telefono)
VALUES 
('Jose Lema', 'Masculino', '12345678901', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'Femenino', '23456789012', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'Masculino', '34567890123', '13 junio y Equinoccial', '098874587');

-- Cliente
INSERT INTO cliente (id, contrasena, estado)
VALUES 
(1, '1234', TRUE),
(2, '5678', TRUE),
(3, '1245', TRUE);

-- Cuenta
INSERT INTO cuenta (numero_cuenta, saldo, tipo_cuenta, estado, cliente_id)
VALUES 
('478758', 2000.00, 'Ahorro', TRUE, 1),
('225487', 100.00, 'Corriente', TRUE, 2),
('495878', 0.00, 'Ahorros', TRUE, 3),
('496825', 540.00, 'Ahorros', TRUE, 2),
('585545', 1000.00, 'Corriente', TRUE, 1);
