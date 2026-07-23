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

## Limitacoes do plano gratuito do Render (vale saber)

- O servico web gratuito **hiberna apos ~15 minutos sem acesso** - o primeiro
  acesso depois disso demora uns 30-50 segundos para "acordar" o container.
- O banco Postgres gratuito do Render tem um **prazo de expiracao** (a politica
  muda com o tempo - confira na pagina do banco no painel; atualmente e por
  volta de 30 dias, podendo pedir upgrade ou recriacao depois disso).
- Para uma demonstracao publica isso e aceitavel; para uso real do clube com os
  dados verdadeiros dos socios, vale migrar para um plano pago (Render Starter,
  ou manter rodando localmente/on-premises como esta hoje).
