INSERT INTO departamentos(nombre, ubicacion) VALUES('Informática','Gijón');
INSERT INTO departamentos(nombre, ubicacion) VALUES('Electrónica','Avilés');

INSERT INTO empleados(dni, nombre, salario, telefono, departamento_id) VALUES('123456789A', 'Marianico El Corto', 1500, 1234567, 1);
INSERT INTO empleados(dni, nombre, salario, telefono, departamento_id) VALUES('987654321B', 'Perico Los Palotes', 1500, 2345678, 2);

INSERT INTO jefes(dni, nombre, salario, telefono, departamento_id) VALUES('234567890B', 'Elena Nito Del Bosque', 2500, 6587452, 1);
INSERT INTO jefes(dni, nombre, salario, telefono, departamento_id) VALUES('345678901C', 'Pepe Villuela', 2500, 6587452, 1);

INSERT INTO usuarios(username, password, enabled) VALUES('psariego', '$2a$10$Rdx3DD/g989uzE5zT9sI..qbR8b0L4BvJVv68.fgXtZM6oSpIbusS', 1);
INSERT INTO usuarios(username, password, enabled) VALUES('admin', '$2a$10$TTRHpl4hdOWcfZ8WcykIsO83kSC3jKzAH5eAxX7JwqOBr36mDrPH6', 1);

INSERT INTO roles(nombre) VALUES('ROLE_USER');
INSERT INTO roles(nombre) VALUES('ROLE_ADMIN');

INSERT INTO usuarios_roles(usuario_id, role_id) VALUES(1,1);
INSERT INTO usuarios_roles(usuario_id, role_id) VALUES(2,2);
INSERT INTO usuarios_roles(usuario_id, role_id) VALUES(2,1);