-- Triggers os quais verificam os inserts:
use PBL
go

CREATE OR ALTER TRIGGER trg_insAmbiente
ON ambiente
INSTEAD OF INSERT
AS
BEGIN
	set nocount on
    -- Verifica se algum dos valores a serem inseridos já existe na tabela
    IF EXISTS (
        SELECT 1 
        FROM inserted i
        WHERE EXISTS (
            SELECT 1 
            FROM ambiente a 
            WHERE a.ambienteNome = i.ambienteNome
        )
    )
    BEGIN
        -- Lança um erro para impedir a inserção
        RAISERROR('Já existe esse Ambiente.', 16, 1);
    END
    ELSE
    BEGIN
        -- Permite a inserção se o valor não existir
        INSERT INTO ambiente
        SELECT *
        FROM inserted;
    END
	set nocount off
END
go

create or alter trigger trg_insFonte
ON fonte
INSTEAD OF INSERT
AS
BEGIN
	set nocount on
    -- Verifica se algum dos valores a serem inseridos já existe na tabela
    IF EXISTS (
        SELECT 1 
        FROM inserted i
        WHERE EXISTS (
            SELECT 1 
            FROM fonte f 
            WHERE f.fonteNome = i.fonteNome
        )
    )
    BEGIN
        -- Lança um erro para impedir a inserção
        RAISERROR('Já existe essa Fonte.', 16, 1);
    END
    ELSE
    BEGIN
        -- Permite a inserção se o valor não existir
        INSERT INTO fonte
        SELECT *
        FROM inserted;
    END
	set nocount off
END
go


/*===========================================================================================*/
-- Triggers os quais verificam os deletes:


CREATE OR ALTER TRIGGER trg_delAmbiente
ON ambiente
INSTEAD OF DELETE
AS
BEGIN
	set nocount on
    -- Verifica se o valor existe na tabela
    IF EXISTS (
        SELECT 1
        FROM deleted d
        WHERE EXISTS (
            SELECT 1
            FROM ambiente a
            WHERE a.ambienteNome = d.ambienteNome
        )
    )
    BEGIN
        -- Permite o delete se o valor existir
        DELETE a
        FROM ambiente a
        INNER JOIN deleted d ON a.ambienteNome = d.ambienteNome;
    END
    ELSE
    BEGIN
        RAISERROR('Esse Ambiente não existe', 16, 1);
    END
	set nocount off
END
go
	    
create or alter trigger trg_delFonte
ON fonte
INSTEAD OF DELETE
AS
BEGIN
	set nocount on
    -- Verifica se o valor existe na tabela
    IF EXISTS (
        SELECT 1
        FROM deleted d
        WHERE EXISTS (
            SELECT 1
            FROM fonte f
            WHERE f.fonteNome = d.fonteNome
        )
    )
    BEGIN
        -- Permite o delete se o valor existir
        DELETE f
        FROM fonte f
        INNER JOIN deleted d ON f.fonteNome = d.fonteNome;
    END
    ELSE
    BEGIN
        RAISERROR('Essa Fonte não existe', 16, 1);
    END
	set nocount off
END
