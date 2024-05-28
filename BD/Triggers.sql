-- Triggers os quais verificam os inserts:
use PBL
go

create or alter trigger trg_insAmbiente
ON ambiente
INSTEAD OF INSERT
AS
BEGIN
	Declare @nome VARCHAR(50) = (select ambienteNome from ambiente)
    -- Verifica se o valor já existe na tabela
    IF EXISTS (SELECT 1 FROM ambiente WHERE @nome IN (SELECT ambienteNome FROM inserted))
    BEGIN
        -- Lança um erro para impedir a inserção
        RAISERROR('Já existe esse Ambiente.', 16, 1);
    END
    ELSE
    BEGIN
        -- Permite a inserção se o valor não existir
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
    -- Verifica se o valor já existe na tabela
    IF EXISTS (SELECT 1 FROM fonte WHERE @nome IN (SELECT fonteNome FROM inserted))
    BEGIN
        -- Lança um erro para impedir a inserção
        RAISERROR('Já existe essa Fonte.', 16, 1);
    END
    ELSE
    BEGIN
        -- Permite a inserção se o valor não existir
        INSERT INTO fonte SELECT * FROM inserted;
    END
END
go


/*===========================================================================================*/
-- Triggers os quais verificam os deletes:


create or alter trigger trg_delAmbiente
ON ambiente
INSTEAD OF DELETE
AS
BEGIN
	Declare @nome VARCHAR(50) = (select ambienteNome from ambiente)
    -- Verifica se o valor já existe na tabela
    IF EXISTS (SELECT 1 FROM ambiente WHERE @nome IN (SELECT ambienteNome FROM deleted))
    BEGIN
	-- Permite o delete se o valor existir:
        DELETE INTO ambiente SELECT * FROM deleted;
    END
    ELSE
    BEGIN
	RAISERROR('Esse Ambiente não existe', 16, 1);
    END
END
go
	    
create or alter trigger trg_delFonte
ON fonte
INSTEAD OF DELETE
AS
BEGIN
	Declare @nome VARCHAR(50) = (select fonteNome from fonte)
    -- Verifica se o valor já existe na tabela
    IF EXISTS (SELECT 1 FROM fonte WHERE @nome IN (SELECT fonteNome FROM deleted))
    BEGIN
	-- Permite o delete se o valor existir:
        DELETE INTO fonte SELECT * FROM deleted;
    END
    ELSE
    BEGIN
	RAISERROR('Essa Fonte não existe', 16, 1);
    END
END
