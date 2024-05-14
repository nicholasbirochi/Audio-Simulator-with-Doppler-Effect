use master
go
drop database PBL
go
create database PBL
go
use PBL
go
create table cadastro
(
	usuarioID int primary key not null,
	nome VARCHAR(100),
	email VARCHAR(100),
	senha VARCHAR(100)
)
go

create table fonte
(
	fonteID int primary key not null,
	frequencia decimal(10,5),
	nome VARCHAR(100)
)
go

create table simulacao
(
	experimentoID int primary key not null,
	experimentoNome VARCHAR(100),
	velocidadeObservador decimal(10,5),
	velocidadeFonte decimal(10,5),
	velocidadeSom decimal(10,5),
	posicaoInicialFonte decimal(10,5),
	posicaoInicialObservador decimal(10,5),
	fonteID int,
	observadorID int
)
go
alter table simulacao
add constraint fk_fonteID foreign key (fonteID) references fonte