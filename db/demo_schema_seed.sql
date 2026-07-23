-- Schema completo + dados ficticios de demonstracao para o clamatiradores-spring.
--
-- Uso: rode este script inteiro contra o banco Postgres vazio criado pelo Render
-- (Render Dashboard > seu banco > Connect > "PSQL Command", ou copie a connection
-- string e use um cliente como DBeaver/pgAdmin). NAO contem nenhum dado real de
-- socio - "SOCIO EXEMPLO 1/2/3" sao ficticios, criados so para demonstrar o sistema
-- funcionando.
--
-- As colunas usam VARCHAR(255) por padrao, batendo com o mapeamento implicito do
-- Hibernate (spring.jpa.hibernate.ddl-auto: validate so confere que a coluna existe
-- e e compativel - nenhuma entidade Java declara um tamanho customizado).

CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN'
);

CREATE TABLE socio (
    id_socio SERIAL PRIMARY KEY,
    file VARCHAR(255),
    nome VARCHAR(255),
    endereco VARCHAR(255),
    email VARCHAR(255),
    fone VARCHAR(255),
    cpf VARCHAR(255) UNIQUE,
    rg VARCHAR(255),
    profissao VARCHAR(255),
    numclam VARCHAR(255),
    numcr VARCHAR(255),
    pai VARCHAR(255),
    mae VARCHAR(255),
    filiacao VARCHAR(255),
    renovacao VARCHAR(255),
    validade VARCHAR(255),
    datanasc VARCHAR(255),
    observacao VARCHAR(255),
    situacao VARCHAR(255),
    pagamento VARCHAR(255)
);

CREATE TABLE pagamento (
    id_pag SERIAL PRIMARY KEY,
    id_socio INTEGER NOT NULL REFERENCES socio(id_socio),
    pagamento VARCHAR(255)
);

CREATE TABLE declaracao (
    id_dec SERIAL PRIMARY KEY,
    id_socio INTEGER REFERENCES socio(id_socio),
    datainsc VARCHAR(255),
    dia VARCHAR(255),
    mes VARCHAR(255),
    ano VARCHAR(255),
    dataemissao VARCHAR(255),
    datavalidade VARCHAR(255),
    endereco VARCHAR(255),
    cr VARCHAR(255)
);

CREATE TABLE declaracaomodprova (
    id_decmodprova SERIAL PRIMARY KEY,
    id_socio INTEGER REFERENCES socio(id_socio),
    dia VARCHAR(255),
    mes VARCHAR(255),
    ano VARCHAR(255),
    dataatual VARCHAR(255),
    dataemissao VARCHAR(255),
    datavalidade VARCHAR(255)
);

CREATE TABLE declaracaohab (
    id_dec SERIAL PRIMARY KEY,
    id_socio INTEGER REFERENCES socio(id_socio),
    datainsc VARCHAR(255),
    dia VARCHAR(255),
    mes VARCHAR(255),
    ano VARCHAR(255),
    dataemissao VARCHAR(255),
    datavalidade VARCHAR(255),
    local VARCHAR(255),
    dataevento VARCHAR(255),
    treino_competicao VARCHAR(255),
    local1 VARCHAR(255),
    dataevento1 VARCHAR(255),
    treino_competicao1 VARCHAR(255),
    local2 VARCHAR(255),
    dataevento2 VARCHAR(255),
    treino_competicao2 VARCHAR(255),
    local3 VARCHAR(255),
    dataevento3 VARCHAR(255),
    treino_competicao3 VARCHAR(255),
    local4 VARCHAR(255),
    dataevento4 VARCHAR(255),
    treino_competicao4 VARCHAR(255),
    local5 VARCHAR(255),
    dataevento5 VARCHAR(255),
    treino_competicao5 VARCHAR(255),
    local6 VARCHAR(255),
    dataevento6 VARCHAR(255),
    treino_competicao6 VARCHAR(255),
    local7 VARCHAR(255),
    dataevento7 VARCHAR(255),
    treino_competicao7 VARCHAR(255),
    local8 VARCHAR(255),
    dataevento8 VARCHAR(255),
    treino_competicao8 VARCHAR(255),
    local9 VARCHAR(255),
    dataevento9 VARCHAR(255),
    treino_competicao9 VARCHAR(255),
    local10 VARCHAR(255),
    dataevento10 VARCHAR(255),
    treino_competicao10 VARCHAR(255),
    local11 VARCHAR(255),
    dataevento11 VARCHAR(255),
    treino_competicao11 VARCHAR(255),
    local12 VARCHAR(255),
    dataevento12 VARCHAR(255),
    treino_competicao12 VARCHAR(255),
    local13 VARCHAR(255),
    dataevento13 VARCHAR(255),
    treino_competicao13 VARCHAR(255),
    local14 VARCHAR(255),
    dataevento14 VARCHAR(255),
    treino_competicao14 VARCHAR(255),
    local15 VARCHAR(255),
    dataevento15 VARCHAR(255),
    treino_competicao15 VARCHAR(255),
    local16 VARCHAR(255),
    dataevento16 VARCHAR(255),
    treino_competicao16 VARCHAR(255),
    local17 VARCHAR(255),
    dataevento17 VARCHAR(255),
    treino_competicao17 VARCHAR(255),
    local18 VARCHAR(255),
    dataevento18 VARCHAR(255),
    treino_competicao18 VARCHAR(255),
    local19 VARCHAR(255),
    dataevento19 VARCHAR(255),
    treino_competicao19 VARCHAR(255)
);

CREATE TABLE tbfrequencia (
    id_freq SERIAL PRIMARY KEY,
    id_socio INTEGER REFERENCES socio(id_socio),
    ano VARCHAR(255),
    mes VARCHAR(255),
    datafiliacao VARCHAR(255),
    datavalidade VARCHAR(255),
    nivel VARCHAR(255),
    municao VARCHAR(255),
    treinos VARCHAR(255),
    municipal VARCHAR(255),
    estadual VARCHAR(255),
    federal VARCHAR(255),
    cpf VARCHAR(255),
    endereco VARCHAR(255),
    numcr VARCHAR(255),
    livrosis VARCHAR(255),
    folhanumregistro VARCHAR(255),
    datalancamento VARCHAR(255),
    numclam VARCHAR(255),
    data1 VARCHAR(255),
    data2 VARCHAR(255),
    data3 VARCHAR(255),
    data4 VARCHAR(255),
    data5 VARCHAR(255),
    data6 VARCHAR(255),
    data7 VARCHAR(255),
    data8 VARCHAR(255),
    data9 VARCHAR(255),
    data10 VARCHAR(255),
    data11 VARCHAR(255),
    data12 VARCHAR(255),
    data13 VARCHAR(255),
    data14 VARCHAR(255),
    data15 VARCHAR(255),
    data16 VARCHAR(255),
    data17 VARCHAR(255),
    data18 VARCHAR(255),
    data19 VARCHAR(255),
    data20 VARCHAR(255),
    sigma1 VARCHAR(255),
    sigma2 VARCHAR(255),
    sigma3 VARCHAR(255),
    sigma4 VARCHAR(255),
    sigma5 VARCHAR(255),
    sigma6 VARCHAR(255),
    sigma7 VARCHAR(255),
    sigma8 VARCHAR(255),
    sigma9 VARCHAR(255),
    sigma10 VARCHAR(255),
    sigma11 VARCHAR(255),
    sigma12 VARCHAR(255),
    sigma13 VARCHAR(255),
    sigma14 VARCHAR(255),
    sigma15 VARCHAR(255),
    sigma16 VARCHAR(255),
    sigma17 VARCHAR(255),
    sigma18 VARCHAR(255),
    sigma19 VARCHAR(255),
    sigma20 VARCHAR(255),
    municao1 VARCHAR(255),
    municao2 VARCHAR(255),
    municao3 VARCHAR(255),
    municao4 VARCHAR(255),
    municao5 VARCHAR(255),
    municao6 VARCHAR(255),
    municao7 VARCHAR(255),
    municao8 VARCHAR(255),
    municao9 VARCHAR(255),
    municao10 VARCHAR(255),
    municao11 VARCHAR(255),
    municao12 VARCHAR(255),
    municao13 VARCHAR(255),
    municao14 VARCHAR(255),
    municao15 VARCHAR(255),
    municao16 VARCHAR(255),
    municao17 VARCHAR(255),
    municao18 VARCHAR(255),
    municao19 VARCHAR(255),
    municao20 VARCHAR(255),
    evento1 VARCHAR(255),
    evento2 VARCHAR(255),
    evento3 VARCHAR(255),
    evento4 VARCHAR(255),
    evento5 VARCHAR(255),
    evento6 VARCHAR(255),
    evento7 VARCHAR(255),
    evento8 VARCHAR(255),
    evento9 VARCHAR(255),
    evento10 VARCHAR(255),
    evento11 VARCHAR(255),
    evento12 VARCHAR(255),
    evento13 VARCHAR(255),
    evento14 VARCHAR(255),
    evento15 VARCHAR(255),
    evento16 VARCHAR(255),
    evento17 VARCHAR(255),
    evento18 VARCHAR(255),
    evento19 VARCHAR(255),
    evento20 VARCHAR(255)
);

-- ==========================================================================
-- Dados ficticios de demonstracao (nenhum dado real de socio)
-- ==========================================================================

INSERT INTO socio (nome, endereco, email, fone, cpf, rg, profissao, numclam, numcr, pai, mae, filiacao, renovacao, validade, datanasc, observacao, situacao, pagamento)
VALUES
    ('SOCIO EXEMPLO UM', 'Rua das Armas, 100', 'exemplo1@teste.com', '(98) 90000-0001', '111.111.111-11', '1111111', 'Engenheiro', 'CLAM-0001', 'CR-000001', 'Pai Exemplo Um', 'Mae Exemplo Um', '2024-01-10', 'ATIVO', '2027-01-10', '1990-05-15', 'Registro de demonstracao', 'ATIVO', 'EM DIA'),
    ('SOCIO EXEMPLO DOIS', 'Av. dos Atiradores, 200', 'exemplo2@teste.com', '(98) 90000-0002', '222.222.222-22', '2222222', 'Professora', 'CLAM-0002', 'CR-000002', 'Pai Exemplo Dois', 'Mae Exemplo Dois', '2023-06-01', 'ATIVO', '2026-06-01', '1985-11-20', 'Registro de demonstracao', 'ATIVO', 'EM DIA'),
    ('SOCIO EXEMPLO TRES', 'Rua do Clube, 300', 'exemplo3@teste.com', '(98) 90000-0003', '333.333.333-33', '3333333', 'Comerciante', 'CLAM-0003', 'CR-000003', 'Pai Exemplo Tres', 'Mae Exemplo Tres', '2022-03-20', 'INATIVO', '2025-03-20', '1978-02-02', 'Registro de demonstracao - situacao inativa', 'INATIVO', 'PENDENTE');

INSERT INTO declaracao (id_socio, datainsc, dia, mes, ano, dataemissao, datavalidade, endereco, cr)
VALUES
    ((SELECT id_socio FROM socio WHERE cpf = '111.111.111-11'), '2024-01-10', '15', 'Julho', '2026', '2026-07-15', '2027-07-15', 'Rua das Armas, 100', 'CR-000001');

INSERT INTO declaracaomodprova (id_socio, dia, mes, ano, dataatual, dataemissao, datavalidade)
VALUES
    ((SELECT id_socio FROM socio WHERE cpf = '222.222.222-22'), '10', 'Julho', '2026', '2026-07-10', '2026-07-10', '2027-07-10');

INSERT INTO declaracaohab (id_socio, dia, mes, ano, datainsc, dataemissao, datavalidade, local, dataevento, treino_competicao)
VALUES
    ((SELECT id_socio FROM socio WHERE cpf = '111.111.111-11'), '5', 'Julho', '2026', '2024-01-10', '2026-07-05', '2027-07-05', 'Estande Municipal', '2026-07-01', 'TREINO');

INSERT INTO tbfrequencia (id_socio, ano, mes, nivel, cpf, endereco, numcr, datafiliacao, datavalidade, data1, sigma1, municao1, evento1)
VALUES
    ((SELECT id_socio FROM socio WHERE cpf = '222.222.222-22'), '2026', 'Julho', 'BASIC', '222.222.222-22', 'Av. dos Atiradores, 200', 'CR-000002', '2023-06-01', '2026-06-01', '2026-07-01', 'SG01', '9mm', 'Treino mensal');

INSERT INTO pagamento (id_socio, pagamento)
VALUES
    ((SELECT id_socio FROM socio WHERE cpf = '111.111.111-11'), 'Mensalidade Julho/2026 - EM DIA'),
    ((SELECT id_socio FROM socio WHERE cpf = '222.222.222-22'), 'Mensalidade Julho/2026 - EM DIA');

-- O usuario admin e criado automaticamente pela aplicacao no primeiro startup
-- (AdminUserSeeder) usando ADMIN_SEED_USERNAME/ADMIN_SEED_PASSWORD - nao precisa
-- ser inserido aqui.
