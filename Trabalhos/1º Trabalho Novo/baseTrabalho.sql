/*Cliente*/

drop table Cliente1 cascade; /*need to comment if table already exists*/

CREATE TABLE Cliente1(
    IdCliente int,
  	NomeCliente varchar(128),
    Genero varchar(20),
    Idade int,
 	Codigo_registo serial,
    PRIMARY KEY(IdCliente)
);

/*--------------------------------------*/

/*Centro*/

drop table Centro cascade;

CREATE TABLE Centro(
    IdCentro int, 
    NomeCentro varchar(128), 
    PRIMARY KEY(IdCentro)
);

/*--------------------------------------*/

/*Lista de Vacinados*/
drop table Lista_Vacinados cascade; /*need to comment if table already exists*/

CREATE TABLE Lista_Vacinados(
    IdCentro int,
    Codigo_registo int,
  	Vacina varchar(128),
    Data_vacina varchar(20),
    PRIMARY KEY(Codigo_registo),
  	FOREIGN KEY(Codigo_registo) REFERENCES Cliente1 on  delete restrict
);

/*--------------------------------------*/

/*Filas nos centros*/ 

drop table Fila_Centro cascade; /*need to comment if table already exists*/

CREATE TABLE Fila_Centro(
    IdCentro int,
    Codigo_registo INT,
    PRIMARY KEY(IdCentro, Codigo_registo),
    FOREIGN KEY(IdCentro) REFERENCES Centro on  delete restrict,
  	FOREIGN KEY(Codigo_registo) REFERENCES Cliente1 on  delete restrict
);

/*--------------------------------------*/
/*Filas nos centros*/ 

drop table Efeitos_Secundarios cascade; /*need to comment if table already exists*/

CREATE TABLE Efeitos_Secundarios(
    Codigo_registo INT,
  	Efeitos varchar(256),
    PRIMARY KEY(Codigo_registo),
  	FOREIGN KEY(Codigo_registo) REFERENCES Cliente1 on  delete restrict
);

/*--------------------------------------*/
insert into Centro VALUES (1, 'Estremoz');
insert into Centro VALUES (2,'Lisboa');
INSERT INTO Centro values (3,'Évora');
INSERT INTO Centro Values (4,'Mora');
