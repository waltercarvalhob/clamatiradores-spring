# Publicando no Render (camada gratuita)

Este guia parte do zero: voce ainda nao tem conta no GitHub/Render, e o codigo
esta so na sua maquina (ja commitado localmente em `C:/dados/clamAtiradores-spring`).

**Importante sobre os dados**: o banco criado neste guia comeca **vazio, com
dados ficticios de demonstracao** (`db/demo_schema_seed.sql`), nao com os dados
reais dos socios. Os dados reais continuam so no seu `bdsocio` local.

## Pipeline automatizado (depois do primeiro deploy)

Depois que voce fizer os passos 1-4 uma vez, o fluxo do dia a dia fica assim,
sem nenhum clique manual:

```
git push origin main
      │
      ├──► GitHub Actions (.github/workflows/ci.yml)
      │      compila o projeto e roda os testes contra um Postgres de teste
      │      (falha aqui = voce fica sabendo antes de ir para producao)
      │
      └──► Render (autoDeploy: true no render.yaml)
             builda a imagem Docker e publica automaticamente
```

As duas coisas disparam em paralelo a cada `git push` na branch `main` - o
Render nao espera o resultado do GitHub Actions por padrao. Se quiser que o
deploy so aconteca depois do CI passar, configure uma **Branch protection rule**
no GitHub (Settings > Branches > exigir que o check "build-and-test" passe antes
do merge) e so faca push/merge na `main` depois do PR ficar verde.

O [Dependabot](https://docs.github.com/pt/code-security/dependabot) tambem esta
configurado (`.github/dependabot.yml`) - toda semana ele abre Pull Requests
sozinho atualizando dependencias do Maven, as actions do workflow e as imagens
base do Dockerfile. Cada PR do Dependabot roda o mesmo CI automaticamente antes
de voce decidir se aprova o merge.

## 1. Colocar o codigo no GitHub

1. Va em [github.com/new](https://github.com/new) e crie um repositorio novo,
   vazio (sem README/gitignore/license - ja temos tudo isso).
   Nome sugerido: `clamatiradores-spring`. Pode ser privado ou publico -
   privado nao impede o deploy no Render.
2. O GitHub vai te mostrar a URL do repositorio (algo como
   `https://github.com/SEU_USUARIO/clamatiradores-spring.git`). Copie ela.
3. No terminal, dentro de `C:/dados/clamAtiradores-spring`:

```bash
git remote add origin https://github.com/SEU_USUARIO/clamatiradores-spring.git
git push -u origin main
```

   O Git vai pedir para autenticar - se for a primeira vez, o GitHub vai abrir
   o navegador para login, ou vai pedir um "Personal Access Token" no lugar da
   senha (o GitHub nao aceita mais senha direta no `git push`).

## 2. Criar conta no Render

1. Va em [render.com](https://render.com) e crie uma conta (da para entrar
   direto com a conta do GitHub, o que ja facilita o proximo passo).
2. Nao precisa cadastrar cartao de credito para o plano gratuito.

## 3. Deploy via Blueprint (cria o servico web + banco juntos)

1. No painel do Render, clique em **New > Blueprint**.
2. Conecte sua conta do GitHub e selecione o repositorio `clamatiradores-spring`.
3. O Render vai ler o arquivo `render.yaml` da raiz do repositorio e propor a
   criacao de dois recursos: o banco `clamatiradores-db` e o servico web
   `clamatiradores-spring`. Confirme (**Apply**).
4. O primeiro deploy demora alguns minutos (o Render precisa construir a imagem
   Docker, que compila o projeto Maven dentro do build). Acompanhe os logs na
   aba **Logs** do servico.
5. Isso so precisa ser feito uma vez. Dali para frente, `autoDeploy: true` no
   `render.yaml` significa que todo `git push` na `main` refaz o deploy
   sozinho - nao precisa voltar no painel do Render de novo.

**Se o Render nao aceitar o `render.yaml`** (formato de Blueprint pode mudar
com o tempo), crie os recursos manualmente:
- **New > PostgreSQL**: nome `clamatiradores-db`, plano Free.
- **New > Web Service**: conecte o mesmo repositorio, Runtime = **Docker**,
  plano Free. Em **Environment**, adicione as variaveis:
  - `SPRING_PROFILES_ACTIVE` = `prod`
  - `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` - copie esses
    valores da pagina do banco `clamatiradores-db` (aba **Connect**, campos
    "Hostname", "Port", "Database", "Username", "Password" da conexao interna).
  - `ADMIN_SEED_USERNAME` = `admin`
  - `ADMIN_SEED_PASSWORD` = escolha uma senha forte (nao deixe `changeme` no ar publico).

## 4. Rodar o schema + dados de demonstracao no banco novo

O banco do Render comeca vazio - a aplicacao NAO cria tabelas sozinha
(`ddl-auto: validate`, de proposito, para nunca alterar schema sem voce saber).

1. Na pagina do banco `clamatiradores-db` no Render, aba **Connect**, copie o
   comando **PSQL Command** (algo como `PGPASSWORD=... psql -h ... -U ... ...`).
2. No seu terminal local (onde ja tem o `psql` do PostgreSQL instalado), rode
   esse comando para abrir uma sessao conectada ao banco do Render, e dentro
   dele execute:

```sql
\i C:/dados/clamAtiradores-spring/db/demo_schema_seed.sql
```

   (ou, sem entrar no `psql` interativo, cole o comando PSQL Command do Render
   adicionando ` -f "C:/dados/clamAtiradores-spring/db/demo_schema_seed.sql"` no final).

3. Volte no painel do Render e reinicie o servico web (**Manual Deploy > Restart**),
   se ele ja tinha subido antes do banco ter as tabelas - assim ele reconecta e
   passa pela validacao do schema com sucesso.

## 5. Acessar o sistema publicado

1. Na pagina do servico `clamatiradores-spring` no Render, o link publico
   aparece no topo (algo como `https://clamatiradores-spring.onrender.com`).
2. Faca login com `admin` e a senha que voce configurou em `ADMIN_SEED_PASSWORD`
   (ou veja nos logs do primeiro deploy, se usou `generateValue: true` no
   `render.yaml` - o Render gera e guarda a senha em **Environment**, aba do
   servico).
3. Voce vai ver os 3 socios ficticios de demonstracao (`SOCIO EXEMPLO UM/DOIS/TRES`).

## 6. Backup e restore dos dados reais

Os passos acima (secao 4) so cobrem o banco de **demonstracao**. Para levar os
dados reais dos socios (do `bdsocio` local) para o Render, ou simplesmente
manter backups periodicos, use os scripts em `db/backup.ps1` e `db/restore.ps1`
(PowerShell - funcionam tanto contra o banco local quanto contra qualquer
Postgres remoto, bastando trocar host/usuario/senha). Eles chamam `pg_dump`/
`pg_restore` do client do PostgreSQL (procuram automaticamente em
`C:\Program Files\PostgreSQL\*\bin` se nao estiverem no PATH).

### Backup do banco local (`bdsocio`)

```powershell
.\db\backup.ps1
```

Gera `db\backups\bdsocio_<timestamp>.dump` (pasta ignorada pelo git - nunca
commite um backup real, ele contem dados pessoais dos socios).

### Restaurar um backup (local, ou publicar os dados reais no Render)

```powershell
# No banco local, para testar o backup
.\db\restore.ps1 -DumpFile .\db\backups\bdsocio_20260728_101500.dump -Force

# No banco do Render (pegue host/usuario/senha na aba "Connect" do banco
# clamatiradores-db, "External Database URL" tem todos os campos separados)
.\db\restore.ps1 -DumpFile .\db\backups\bdsocio_20260728_101500.dump `
    -DbHost dpg-xxxxx.oregon-postgres.render.com -Port 5432 `
    -DbName clamatiradores -DbUser clamatiradores_user -Password "SENHA_DO_RENDER" -Force
```

`-Force` e obrigatorio de proposito: o restore roda com `--clean --if-exists`,
ou seja, **apaga e recria as tabelas do destino** antes de importar os dados
do backup. Confira sempre o `-DbHost`/`-DbName` antes de rodar com `-Force`
contra um banco que nao seja o de teste.

### Backup automatico periodico (opcional)

O plano gratuito do Render nao tem backup automatico. Para agendar um backup
diario do banco de producao (Render) direto da sua maquina, crie uma tarefa no
**Agendador de Tarefas do Windows** que rode:

```powershell
powershell -File "C:\dados\clamAtiradores-spring\db\backup.ps1" -DbHost dpg-xxxxx.oregon-postgres.render.com -Port 5432 -DbName clamatiradores -DbUser clamatiradores_user -Password "SENHA_DO_RENDER"
```

Alternativa sem depender da sua maquina estar ligada: planos pagos do Render
(Starter em diante) incluem backup diario automatico gerenciado pela propria
plataforma, com restore de um clique pelo painel.

## Limitacoes do plano gratuito do Render (vale saber)

- O servico web gratuito **hiberna apos ~15 minutos sem acesso** - o primeiro
  acesso depois disso demora uns 30-50 segundos para "acordar" o container.
- O banco Postgres gratuito do Render tem um **prazo de expiracao** (a politica
  muda com o tempo - confira na pagina do banco no painel; atualmente e por
  volta de 30 dias, podendo pedir upgrade ou recriacao depois disso).
- Para uma demonstracao publica isso e aceitavel; para uso real do clube com os
  dados verdadeiros dos socios, vale migrar para um plano pago (Render Starter,
  ou manter rodando localmente/on-premises como esta hoje).
