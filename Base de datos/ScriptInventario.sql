--CATEGORIAS--

drop table if exists categorias;
create table categorias(
	codigo_cat serial not null,
	nombre varchar(100) not null,
	categoria_padre int,
	constraint categorias_pk primary key (codigo_cat),
	constraint categorias_fk foreign key (categoria_padre) references categorias(codigo_cat)
);
insert into categorias(nombre,categoria_padre)
values('Materia Prima', null);
insert into categorias(nombre,categoria_padre)
values('Proteina', 1);
insert into categorias(nombre,categoria_padre)
values('Salsas', 1);
insert into categorias(nombre,categoria_padre)
values('Punto de venta', null);
insert into categorias(nombre,categoria_padre)
values('Bebidas', 4);
insert into categorias(nombre,categoria_padre)
values('Con Alcohol', 5);
insert into categorias(nombre,categoria_padre)
values('Sin Alcohol', 5);

--CATEGORIA UNIDAD MEDIDAS
drop table if exists categoria_unidad_medida;
create table categoria_unidad_medida(
    codigo_categoria_udm char(1) not null,
    nombre varchar(100) not null,
    constraint categoria_udm_pk primary key (codigo_categoria_udm)
);
insert into categoria_unidad_medida(codigo_categoria_udm,nombre)
values ('U','Unidades');
insert into categoria_unidad_medida(codigo_categoria_udm,nombre)
values ('V','Volumen');
insert into categoria_unidad_medida(codigo_categoria_udm,nombre)
values ('P','Peso');

--UNIDADES DE MEDIDA
drop table if exists unidad_medida;
create table unidad_medida(
    codigo_udm char(10) not null,
    descripcion varchar(100) not null,
    categoria_udm char(1) not null,
    constraint unidad_medida_pk primary key (codigo_udm),
    constraint unidad_medida_fk foreign key (categoria_udm) 
    references categoria_unidad_medida (codigo_categoria_udm)
);
insert into unidad_medida(codigo_udm,descripcion,categoria_udm)
values('ml','mililitros','V');
insert into unidad_medida(codigo_udm,descripcion,categoria_udm)
values('l','unidad','U');
insert into unidad_medida(codigo_udm,descripcion,categoria_udm)
values('u','mililitros','V');
insert into unidad_medida(codigo_udm,descripcion,categoria_udm)
values('d','docena','U');
insert into unidad_medida(codigo_udm,descripcion,categoria_udm)
values('g','gramos','P');
insert into unidad_medida(codigo_udm,descripcion,categoria_udm)
values('kg','kilogramos','P');
insert into unidad_medida(codigo_udm,descripcion,categoria_udm)
values('lb','libras','P');	

-- PRODUCTO
drop table if exists producto;
create table producto(
	codigo_prod serial not null,
	nombre varchar(100) not null,
	udm char(10) not null,
	precio_venta money not null,
	iva boolean not null,
	coste money not null,
	categoria int not null,
	stock int not null,
	constraint producto_pk primary key (codigo_prod),
	constraint producto_udm_fk foreign key (udm) references unidad_medida(codigo_udm),
	constraint producto_categoria_fk foreign key (categoria) references categorias(codigo_cat)
);
insert into producto(nombre,udm,precio_venta,iva,coste,categoria,stock)
values ('Coca Cola pequena','u','0.5804',true,'0.3729',7,110);
insert into producto(nombre,udm,precio_venta,iva,coste,categoria,stock)
values ('Salsa de tomate','kg','0.95',true,'0.8736',3,0);
insert into producto(nombre,udm,precio_venta,iva,coste,categoria,stock)
values ('Mostaza','kg','0.95',true,'0.98',3,0);
insert into producto(nombre,udm,precio_venta,iva,coste,categoria,stock)
values ('Fuze tea','u','0.8',true,'0.7',7,50);

--HISTORIAL STOCK
drop table if exists historial_stock;
create table historial_stock(
	codigo_historial serial not null,
	fecha date not null,
	producto int not null,
	referencia varchar(100)not null,
	cantidad int not null,
	constraint historial_pk primary key (codigo_historial),
	constraint producto_fk foreign key (producto) references producto(codigo_prod)
);
insert into historial_stock(fecha,referencia,producto,cantidad)
values('20-11-2023','PEDIDO 1',1,100);
insert into historial_stock(fecha,referencia,producto,cantidad)
values('20-11-2023','PEDIDO 1',4,50);
insert into historial_stock(fecha,referencia,producto,cantidad)
values('20-11-2023','PEDIDO 2',1,10);
insert into historial_stock(fecha,referencia,producto,cantidad)
values('20-11-2023','VENTA 1',1,-5);
insert into historial_stock(fecha,referencia,producto,cantidad)
values('20-11-2023','VENTA 1',4,1);

--TIPO DE DOCUMENTO
drop table if exists tipo_documento;
create table tipo_documento(
    codigo_documento char(1) not null,
    descripcion varchar(100) not null,
    constraint tipo_documento_pk primary key (codigo_documento)
);
insert into tipo_documento(codigo_documento,descripcion)
values ('C','CEDULA');
insert into tipo_documento(codigo_documento,descripcion)
values ('R','RUC');

--PROVEEDORES
drop table if exists proveedores;
create table proveedores(
    identificador varchar(100) not null,
    tipo_documento char(1) not null,
    nombre varchar(100) not null,
    telefono varchar(10) not null,
    correo varchar(100) not null,
    ubicacion varchar(100) not null,
    constraint proveedores_pk primary key (identificador),
    constraint tipo_documento_fk foreign key (tipo_documento) 
    references tipo_documento(codigo_documento)
);
insert into proveedores(identificador, tipo_documento, nombre, telefono, correo, ubicacion)
values ('1792285747','C','SANTIAGO MOSQUERA','992920306','ZANTYCB89@GMAIL.COM','CUMBAYORK');
insert into proveedores(identificador, tipo_documento, nombre, telefono, correo, ubicacion)
values ('1792285744001','R','SNACKS SA','992920398','SNACKS@GMAIL.COM','LA TOLA');


--ESTADO PEDIDO
drop table if exists estado_pedido;
create table estado_pedido(
	codigo_estado char(1) not null,
	descripcion varchar(100) not null,
	constraint estado_pedido_pk primary key (codigo_estado)
);
insert into estado_pedido(codigo_estado,descripcion)
values('S','Solicitado');
insert into estado_pedido(codigo_estado,descripcion)
values('R','Recibido');

--CABECERA PEDIDO
drop table if exists cabecera_pedido;
create table cabecera_pedido(
	numero serial not null,
	proveedor varchar(100) not null,
	fecha date not null,
	estado char(1),
	constraint cabecera_pedido_pk primary key (numero),
	constraint estado_pedido_fk foreign key (estado) references estado_pedido(codigo_estado)
);
insert into cabecera_pedido(proveedor,fecha,estado)
values ('1792285747','20-11-2023','R');
insert into cabecera_pedido(proveedor,fecha,estado)
values ('1792285747','20-11-2023','S');

--CABECERA VENTAS
drop table if exists cabecera_ventas;
create table cabecera_ventas(
	codigo serial not null,
	fecha date not null,
	total_sin_iva decimal not null,
	iva decimal not null,
	total decimal not null,
	constraint cabecera_ventas_pk primary key (codigo)
);
insert into cabecera_ventas(fecha,total_sin_iva,iva,total)
values('20-11-2023',3.26,0.39,3.65);
--DETALLE VENTAS
drop table if exists detalle_ventas;
create table detalle_ventas(
	codigo serial not null,
	cabecera_ventas int not null,
	producto int not null,
	cantidad int not null,
	precio_venta decimal not null,
	subtotal decimal not null,
	subtotal_iva decimal not null,
	constraint detalle_ventas_pk primary key (codigo),
	constraint detalle_cabecera_fk foreign key (cabecera_ventas) references cabecera_ventas (codigo),
	constraint detalle_producto_fk foreign key (producto) references producto(codigo_prod)
);
insert into detalle_ventas(cabecera_ventas,producto,cantidad,precio_venta,subtotal,subtotal_iva)
values(1,1,5,0.58,2.9,3.25);
insert into detalle_ventas(cabecera_ventas,producto,cantidad,precio_venta,subtotal,subtotal_iva)
values(1,4,1,0.36,0.36,0.4);