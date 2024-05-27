-- Triggers as quais verificam os inserts:
use PBL
go

create or alter trigger trg_insAmbiente
ON ambiente
INSTEAD OF INSERT
AS
BEGIN
	Declare @nome VARCHAR(50) = (select ambienteNome from ambiente)
    -- Verifica se o valor j� existe na tabela
    IF EXISTS (SELECT 1 FROM ambiente WHERE @nome IN (SELECT ambienteNome FROM inserted))
    BEGIN
        -- Lan�a um erro para impedir a inser��o
        RAISERROR('J� existe esse Ambiente.', 16, 1);
    END
    ELSE
    BEGIN
        -- Permite a inser��o se o valor n�o existir
        INSERT INTO ambiente SELECT * FROM inserted;
    END
END
go

create or alter trigger trg_insFonte
ON fonte
INSTEAD OF INSERT
AS
BEGIN
	Declare @nome VARCHAR(50) = (select fonteNome from fonte)
    -- Verifica se o valor j� existe na tabela
    IF EXISTS (SELECT 1 FROM fonte WHERE @nome IN (SELECT fonteNome FROM inserted))
    BEGIN
        -- Lan�a um erro para impedir a inser��o
        RAISERROR('J� existe essa Fonte.', 16, 1);
    END
    ELSE
    BEGIN
        -- Permite a inser��o se o valor n�o existir
        INSERT INTO fonte SELECT * FROM inserted;
    END
END
go

create or alter trigger trg_insObservador
ON observador
INSTEAD OF INSERT
AS
BEGIN
	Declare @nome VARCHAR(50) = (select observadorNome from observador)
    -- Verifica se o valor j� existe na tabela
    IF EXISTS (SELECT 1 FROM observador WHERE @nome IN (SELECT observadorNome FROM inserted))
    BEGIN
        -- Lan�a um erro para impedir a inser��o
        RAISERROR('J� existe esse Observador.', 16, 1);
    END
    ELSE
    BEGIN
        -- Permite a inser��o se o valor n�o existir
        INSERT INTO observador SELECT * FROM inserted;
    END
END