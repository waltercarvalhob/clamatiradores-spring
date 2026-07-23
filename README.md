# clamatiradores-spring

Modernizacao do sistema legado `clamAtiradores` (Servlet/JSP + JDBC puro) para
**Spring Boot + Thymeleaf + Bootstrap 5**. Cobre 6 dos 7 modulos de dominio do
sistema: **Socio, Declaracao, Declaracao de Modalidade de Prova, Declaracao de
Habitualidade, Habitualidade e Pagamento** - cada um com listagem/pesquisa
paginada, cadastro, edicao, exclusao e os relatorios PDF correspondentes via
JasperReports. Autenticacao via Spring Security (o legado nao tinha nenhuma
funcional).

O projeto legado em `C:/dados/clamAtiradores` continua intacto e pode seguir
rodando normalmente - nada foi apagado ou alterado la.

**Modulo nao migrado:** a entidade `Frequencia` do legado (que grava na tabela
`habitualidade`, nao confundir com a tabela `tbfrequencia` do modulo
Habitualidade acima) so tinha um servlet de atualizacao funcional
(`FrequenciaServerAtualiza`) - o `web.xml` legado referencia
`dao.ServerFrequencia` para criacao, uma classe que nao existe no codigo-fonte.
Como o fluxo de criacao ja estava quebrado no sistema original, este modulo
ficou de fora.

## Stack

- Java 17, Spring Boot 4.0.7
- Thymeleaf + thymeleaf-layout-dialect (layout unico compartilhado) + Bootstrap 5 (via webjar)
- Spring Data JPA (Hibernate) sobre o PostgreSQL existente (`bdsocio`) - schema **nao** foi migrado, apenas mapeado
- Spring Security (form login)
- JasperReports 6.16.0, reaproveitando os `.jrxml` originais de `WebContent/relatorio`, mais
  `barcode4j`, `zxing` e `batik-all` (necessarios em runtime para os relatorios que tem QR code -
  descoberto rodando os relatorios de verdade, nao estava obvio so lendo o `.jrxml`)

## Pre-requisitos

- JDK 17+ (testado com JDK 25)
- Maven (ou use o Eclipse, que ja traz Maven embutido via m2e - nao ha `mvnw` neste projeto)
- PostgreSQL com o banco `bdsocio` existente acessivel (mesmo banco do sistema legado)

## Configuracao do banco

A conexao usa as mesmas variaveis do sistema legado, com fallback para os valores
que ja estavam hardcoded no codigo antigo (`postgres`/`252107`, `localhost:5432/bdsocio`):

| Variavel | Padrao |
|---|---|
| `DB_HOST` | `localhost` |
| `DB_PORT` | `5432` |
| `DB_NAME` | `bdsocio` |
| `DB_USER` | `postgres` |
| `DB_PASSWORD` | `252107` |
| `SERVER_PORT` | `8080` |
| `ADMIN_SEED_USERNAME` | `admin` |
| `ADMIN_SEED_PASSWORD` | `changeme` |

**Rotacione `DB_PASSWORD` e `ADMIN_SEED_PASSWORD` antes de expor a aplicacao
fora da sua maquina.** Os valores padrao acima servem só para desenvolvimento local.

### Tabelas que precisam existir antes do primeiro `run`

`spring.jpa.hibernate.ddl-auto` esta como `validate` de proposito - a aplicacao
nunca altera o schema existente sozinha. Duas tabelas usadas por este projeto
nao existiam no banco `bdsocio` e precisam ser criadas manualmente uma unica vez:

```sql
-- Autenticacao: o legado nao tinha nenhuma tabela de usuarios (a autenticacao
-- nunca funcionou de fato no sistema antigo).
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN'
);

-- Pagamento: existe no codigo legado (dados/Pagamento.java, dao/Pagamentodao.java,
-- servidor/ServerPagamento*.java, relatorio sociosPagos.jrxml) mas a tabela nunca
-- chegou a ser criada neste banco especifico.
CREATE TABLE pagamento (
    id_pag SERIAL PRIMARY KEY,
    id_socio INTEGER NOT NULL REFERENCES socio(id_socio),
    pagamento VARCHAR(255)
);
```

Na primeira subida, `AdminUserSeeder` cria automaticamente um usuario
`admin`/`changeme` (senha com hash BCrypt) se ainda nao existir nenhum usuario
com esse username.

## Rodando

### Pelo Eclipse

1. `File > Import > Maven > Existing Maven Projects`, aponte para
   `C:/dados/clamAtiradores-spring`.
2. Rode `ClamAtiradoresApplication` como "Spring Boot App" (ou "Java Application").

### Pela linha de comando

```bash
mvn spring-boot:run
```

Depois acesse `http://localhost:8080/socios` (redireciona para `/login` se
nao autenticado).

## Estrutura de cada modulo

Todos os modulos seguem o mesmo padrao (pacote `com.clamatiradores.<modulo>`):
entidade JPA mapeada 1:1 nas colunas existentes, repositorio + Specification
para busca, servico, controller MVC (`/modulo`, `/modulo/novo`, `/modulo/{id}/editar`,
etc.) e templates em `templates/<modulo>/list.html` + `form.html`. Registros
filiados a um socio (tudo exceto o proprio Socio) usam `/socio-picker` como
passo intermediario para escolher o socio antes de abrir o formulario de
criacao - acessivel tanto pela tela de listagem de cada modulo quanto pelo
botao "+ Registro" na listagem de Socios.

Os modulos com muitas colunas repetidas (Declaracao de Habitualidade: 20 grupos
de local/data/treino-competicao; Habitualidade: 20 grupos de data/SIGMA/municao/evento)
viram uma grade de linhas na tela em vez de dezenas de campos soltos - a
entidade continua mapeando as colunas originais uma a uma (schema inalterado),
so o formulario agrupa visualmente.

## O que foi verificado (rodado de ponta a ponta contra o banco `bdsocio` real)

- Startup limpo com `ddl-auto: validate` para todas as 7 entidades (confirma que
  o mapeamento bate exatamente com as colunas reais de `socio`, `declaracao`,
  `declaracaomodprova`, `declaracaohab`, `tbfrequencia`, `pagamento` e `usuario`).
- Login, busca paginada e CRUD completo (criar/editar/excluir, POST + CSRF) para
  os 6 modulos.
- **Correcao do bug do sistema legado confirmada**: criado um registro de
  Declaracao de Habitualidade com valores diferentes nos indices 0, 8 e 19 da
  grade de eventos - o valor do indice 8 gravou corretamente em `treino_competicao8`
  sem sobrescrever o campo base (`treino_competicao`), ao contrario do
  `ServerDeclaracaohabAltera.java` legado.
- Todos os relatorios PDF (Socio: ativo/inativo/geral; Declaracao: filiacao;
  Declaracao Modalidade de Prova; Declaracao de Habitualidade: permitido/restrito/completo;
  Habitualidade: relatorio mensal) geram PDF valido.
- CPF duplicado no cadastro de Socio (constraint `cpf_unique`) e outros erros de
  integridade de dados (ex.: coluna `nivel` de `tbfrequencia` e `varchar(5)`) mostram
  mensagem amigavel em vez de erro 500 cru.

## Bugs encontrados e corrigidos durante a migracao (nao existiam sinal deles so lendo o codigo)

- **Dependencias de runtime do JasperReports**: alguns `.jrxml` (ex.: `clamdecfiliacao`,
  usado no relatorio de Declaracao) usam um componente de QR code que precisa de
  `barcode4j` + `zxing` + `batik-all` no classpath - so apareceu rodando o relatorio de
  verdade contra o banco, nao dava pra prever so lendo o XML. Adicionados ao `pom.xml`.
- **`FetchType.LAZY` nas associacoes `@ManyToOne` para Socio**: com
  `spring.jpa.open-in-view: false` (configuracao correta, evita o anti-padrao Open
  Session In View), acessar `item.socio.nome` na tela de listagem lancava
  `LazyInitializationException` porque a sessao do Hibernate ja tinha fechado. Trocado
  para `FetchType.EAGER` nas 5 entidades filiadas a Socio (seguro aqui, pois sao
  `@ManyToOne`, sem risco de produto cartesiano na paginacao).
- **Mapa imutavel no `ReportService`**: `JasperFillManager` tenta inserir parametros
  internos (ex. `REPORT_CONNECTION`) no mapa de parametros recebido - passar `Map.of()`
  (imutavel) lancava `UnsupportedOperationException`. Trocado por `HashMap`.

## Limitacoes conhecidas (herdadas do sistema legado, nao resolvidas nesta migracao)

- **`socioAtivo.jrxml`/`socioInativo.jrxml` nao tem parametros declarados** - o filtro
  de nome/CPF/etc. e ignorado porque o proprio relatorio legado ja era assim. O
  relatorio "Socio Ativo" usa uma data de corte **fixa** (`01/12/2023`) no SQL do
  proprio relatorio - vale revisar com quem usa o relatorio antes de editar o `.jrxml`.
- **`clamAtiradoesRel` e o relatorio mensal de Habitualidade dependem dos parametros
  `mes`/`ano`**; sem eles saem praticamente vazios (comportamento herdado do legado).
- **`clamDeclaracaoHab`/`clamDeclaracaoHabRestrito`** usam um parametro chamado
  `id_freq` (nome herdado do legado, provavel copy-paste de outro modulo) mas na
  verdade recebem o `id_dec` da declaracao de habitualidade - preservado assim para
  bater com o SQL ja compilado dentro do `.jrxml`.
- O upload de arquivo do socio (coluna `socio.file`, `util.Upload.java` no legado)
  nao foi portado - o formulario de Socio nao mexe nessa coluna.
- As paginas de "Vencimento" por ano (duplicadas ano a ano no menu do legado) nao
  foram portadas - os modulos migrados ja resolvem isso com busca real no banco.

## Achados no codigo legado relevantes para manutencao futura

- A entidade `Frequencia` grava na tabela `habitualidade`, e a entidade `Habitualidade`
  grava na tabela `tbfrequencia` - os nomes estao trocados no sistema legado. Este
  projeto usa `tbfrequencia` (via `Habitualidade`/`HabitualidadeForm`), que e o fluxo
  que realmente funciona no legado (tem create + update); `Frequencia`/`habitualidade`
  ficou de fora (ver nota no topo deste README).
