use PBL

go

-- Todas as procedures que buscam todos os nomes:
create procedure sp_ambientesTodosNomes as
BEGIN
	select ambienteNome
	from ambiente
END


go

create procedure sp_fontesTodosNomes as
BEGIN
	select fonteNome
	from fonte
END

go

create procedure sp_experimentoTodosNomes as
BEGIN
	select experimentoNome
	from experimento
END

go

/*==============================================================================*/
-- Buscando as tabelas pelos seus nomes:
create procedure sp_ambienteBuscarPorNome (@nome VARCHAR(50)) as
BEGIN
	select *
	from ambiente a
	where @nome = a.ambienteNome
END

go

create procedure sp_fonteBuscarPorNome (@nome VARCHAR(50)) as
BEGIN
	select *
	from fonte f
	where @nome = f.fonteNome
END

go

create procedure sp_experimentoBuscarPorNome (@nome VARCHAR(50)) as
BEGIN
	select *
	from experimento e
	where @nome = e.experimentoNome
END

go

/*==============================================================================*/
-- Adicionando informações às tabelas:
create procedure sp_adicionarAmbiente (@nome VARCHAR(50),
												@velocidadeSom decimal(10,5)) as
BEGIN
	insert into ambiente (ambienteNome, velocidadeSom)
			     values(@nome,
								@velocidadeSom)
END

go

create procedure sp_adicionarTimbre (@nome VARCHAR(50)) as
BEGIN
	insert into timbre(timbreNome) values(@nome)
END

go

create procedure sp_adicionarFonte (@nomeFonte VARCHAR(50),
											 @nomeTimbre VARCHAR(50),
											 @potencia decimal(10,5),
											 @frequencia decimal(10,5)) as
BEGIN
	insert into fonte(fonteNome, timbreNome, potencia, frequencia) 
			   values(@nomeFonte,
							 @nomeTimbre,
							 @potencia,
							 @frequencia)
END

go

create procedure sp_adicionarExperimento (@nomeExperimento VARCHAR(50),
												   @velocidadeObservador decimal(10,5),
												   @posicaoLateral decimal(10,5),
												   @posicaoInicialObservador decimal(10,5),
												   @velocidadeFonte decimal(10,5),
												   @posicaoInicialFonte decimal(10,5),
												   @nomeAmbiente VARCHAR(50),
												   @nomeFonte VARCHAR(50)) as
BEGIN
	insert into experimento(experimentoNome, velocidadeObservador, posicaoLateral, velocidadeFonte, posicaoInicialFonte, ambienteNome, fonteNome)
				values(@nomeExperimento,
								   @velocidadeObservador,
								   @posicaoLateral,
								   @velocidadeFonte,
								   @posicaoInicialFonte,
								   @nomeAmbiente,
								   @nomeFonte)
END

go

-- Valores inseridos pelo desenvolvedor:
exec sp_adicionarAmbiente 'Ar', 330
exec sp_adicionarAmbiente 'Água', 343

exec sp_adicionarTimbre 'TimbreAbelhaEletrica'
exec sp_adicionarTimbre 'TimbreAlienigena'
exec sp_adicionarTimbre 'TimbreArcoIris'
exec sp_adicionarTimbre 'TimbreCarrinhoDeSorvete'
exec sp_adicionarTimbre 'TimbrePiano'
exec sp_adicionarTimbre 'TimbrePuro'
exec sp_adicionarTimbre 'TimbreTrompete'
exec sp_adicionarTimbre 'TimbreViolao'


exec sp_adicionarFonte 'Lá do Violao', 'TimbreViolao', 5, 440
exec sp_adicionarFonte 'Lá do Piano', 'TimbrePiano', 5, 880
exec sp_adicionarFonte 'Dó do Timbre puro', 'TimbrePuro', 5, 523.2
