use PBL

create or alter procedure sp_buscarTimbrePorId (@id int) as
begin
	SELECT timbreID, instrumentoNome
	FROM timbre
	WHERE timbreID = @id
end

--exec sp_buscarTimbrePorId 1 

create or alter procedure sp_buscarTimbreIDPorNome (@nome VARCHAR(50)) as
begin	
	SELECT timbreID, instrumentoNome FROM timbre WHERE instrumentoNome = @nome
end

--exec sp_buscarTimbreIDPorNome 'ainn'

create or alter procedure sp_inserirFonte(@timbreID int,
										  @potencia decimal(10,2),
										  @frequencia decimal(10,2),
										  @fonteNome VARCHAR(50)) as
begin
	INSERT into fonte(timbreID, potencia, frequencia, fonteNome) values(@timbreID,
																		@potencia,
																		@frequencia,
																		@fonteNome)
end

--exec sp_inserirFonte 1, 20.2, 20.2, 'AINN'

----use PBL
----create or alter trigger trg_verificacaoIDsim on simulacao
----for insert as
----BEGIN
----	DECLARE @proximo int = (select MAX(experimentoID) from simulacao) + 1
----	insert into setores values (@proximo, null, 0)
----END

----create or alter trigger trg_verificacaoIDfont on fonte
----for insert as
----BEGIN
----	DECLARE @proximo int = (select MAX(fonteID) from fonte) + 1
----	insert into fonte values (@proximo, null, 0)
----END

----drop trigger trg_verificacaoIFfont


----insert into fonte(frequencia, nome) values (23.45, 'Ambulância')
----select * from fonte