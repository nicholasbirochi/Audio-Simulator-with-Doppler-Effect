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
	fonteID int primary key identity not null,
	timbreID int,
	potencia decimal(10,5),
	frequencia decimal(10,5),
	fonteNome VARCHAR(100)
)
go

create table ambiente
(
	ambienteID int primary key identity not null,
	velocidadeSom decimal(10,5),
	ambienteNome VARCHAR(100)
)
go

create table timbre
(
	timbreID int primary key identity not null,
	instrumentoNome VARCHAR(100)
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
	tempoDuracao int,
	taxaAmostragem VARCHAR(50),
	ambienteID int,
	fonteID int
)
go
alter table simulacao
add constraint fk_fonteID foreign key (fonteID) references fonte,
	constraint fk_ambienteID foreign key (ambienteID) references ambiente,

go
alter table fonte
add constraint fk_timbreID foreign key (timbreID) references timbre
