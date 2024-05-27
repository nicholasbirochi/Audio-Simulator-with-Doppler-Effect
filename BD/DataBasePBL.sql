-- Criando a base das tabelas:
use master
go
drop database PBL
go
create database PBL
go
use PBL
go
create table fonte
(
	fonteNome VARCHAR(50) primary key,
	timbreNome VARCHAR(50) not null,
	posicaoInicialFonte decimal(10,5) not null,
	posicaoLateralFonte decimal(10,5) not null,
	velocidadeFonte decimal(10,5) not null,
	potencia decimal(10,5) not null,
	frequencia decimal(10,5) not null
)
go

create table observador
(
	observadorNome VARCHAR(50) primary key,
	velocidadeObservador decimal(10,5) not null,
	posicaoInicialObservador decimal(10,5) not null
)
go
	
create table ambiente
(
	ambienteNome VARCHAR(50) primary key,
	velocidadeSom decimal(10,5) not null
)
go

create table timbre
(
	timbreNome VARCHAR(50) primary key
)
go

create table experimento
(
	experimentoNome VARCHAR(50) primary key,
	tempoDuracao decimal(10,5) not null,
	taxaAmostragem int not null,
	ambienteNome VARCHAR(50) not null,
	fonteNome VARCHAR(50) not null,
	observadorNome VARCHAR(50) not null
)
go
alter table experimento
add constraint fk_fonteNome foreign key (fonteNome) references fonte,
	constraint fk_observadorNome foreign key (observadorNome) references observador,
	constraint fk_ambienteNome foreign key (ambienteNome) references ambiente

go
alter table fonte
add constraint fk_timbreNome foreign key (timbreNome) references timbre
