CREATE DATABASE correccion;
USE correccion;
/*-----------------------------------------------------------------------------------------------------------------*/
CREATE TABLE IF NOT EXISTS categoria_abarrote
(
	id_cate int auto_increment primary key,
	nom_cate varchar(30)

);
INSERT INTO categoria_abarrote (nom_cate)
VALUES("Cárnicos"),("Lácteos"),("Embutidos"),("Vegetales"),("Frutas"),("Aceites"),("Bebidas"),("Licores");
SELECT * FROM categoria_abarrote;
/*-----------------------------------------------------------------------------------------------------------------*/

CREATE TABLE IF NOT EXISTS proveedor
(
	id_prov int auto_increment primary key,
	nom_prov varchar(30)

);
INSERT INTO proveedor (nom_prov)
VALUES("Coca Cola Co"),("La Favorita"),("Juris"),("Ruffles"),("Nestlé"),("Pepsico"),("Bacardi/s");
SELECT * FROM proveedor;
/*-----------------------------------------------------------------------------------------------------------------*/

CREATE TABLE IF NOT EXISTS producto_abarrote
(
	cod_prod varchar(10) primary key,
	nom_prod varchar(30),
	descrip_prod varchar(50),
	prec_prod decimal(8,2),
	cate_prod varchar(30),
	prove_prodd varchar(30)

);


SELECT * FROM producto_abarrote;
/*-----------------------------------------------------------------------------------------------------------------*/




