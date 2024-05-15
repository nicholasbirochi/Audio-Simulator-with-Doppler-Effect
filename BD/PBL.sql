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
	usuarioID int primary key identity not null,
	nomePessoa VARCHAR(100),
	email VARCHAR(100),
	senha VARCHAR(100)
)
go
	
create table fonte
(
	fonteID int primary key identity not null,
	frequencia decimal(10,5),
	nomeFonte VARCHAR(100)
)
go
	
create table fonte
(
	ambienteID int primary key identity not null,
	velocidadeSom decimal(10,5),
	nomeAmbiente VARCHAR(100)
)
go

create table simulacao
(
	experimentoID int primary key identity not null,
	experimentoNome VARCHAR(100),
	velocidadeObservador decimal(10,5),
	posicaoInicialObservador decimal(10,5),
	velocidadeFonte decimal(10,5),
	posicaoInicialFonte decimal(10,5),
	ambienteID int,
	fonteID int,
)
go
alter table simulacao
add constraint fk_fonteID foreign key (fonteID) references fonte,
    constraint fk_ambienteID foreign key (ambienteID) references ambiente
