<#
.SYNOPSIS
    Restaura um backup gerado por backup.ps1 (formato custom do pg_dump) em um
    banco Postgres de destino (local ou remoto, ex: Render).

.DESCRIPTION
    DESTRUTIVO por padrao: usa --clean --if-exists, ou seja, apaga as tabelas
    existentes no banco de destino antes de recriar com os dados do backup.
    Exige -Force para rodar, exatamente para evitar restaurar por engano em
    cima do banco de producao.

.EXAMPLE
    # Restaurar no banco local de testes
    .\db\restore.ps1 -DumpFile .\db\backups\bdsocio_20260728_101500.dump -Force

.EXAMPLE
    # Restaurar no banco do Render (dados reais -> demonstracao/producao)
    .\db\restore.ps1 -DumpFile .\db\backups\bdsocio_20260728_101500.dump `
        -DbHost dpg-xxxxx.oregon-postgres.render.com -Port 5432 `
        -DbName clamatiradores -DbUser clamatiradores_user -Password "SENHA_DO_RENDER" -Force
#>
param(
    [Parameter(Mandatory = $true)]
    [string]$DumpFile,

    [string]$DbHost = $(if ($env:DB_HOST) { $env:DB_HOST } else { "localhost" }),
    [string]$Port   = $(if ($env:DB_PORT) { $env:DB_PORT } else { "5432" }),
    [string]$DbName = $(if ($env:DB_NAME) { $env:DB_NAME } else { "bdsocio" }),
    [string]$DbUser = $(if ($env:DB_USER) { $env:DB_USER } else { "postgres" }),
    [string]$Password = $(if ($env:DB_PASSWORD) { $env:DB_PASSWORD } else { "252107" }),

    # Confirmacao obrigatoria: este script apaga e recria as tabelas do banco de destino.
    [switch]$Force
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $DumpFile)) {
    throw "Arquivo de backup nao encontrado: $DumpFile"
}

if (-not $Force) {
    throw "Este comando apaga e recria as tabelas de '$DbName' em ${DbHost}:${Port} antes de restaurar. " +
          "Confirme o destino e rode novamente com -Force para prosseguir."
}

function Find-PgTool([string]$Name) {
    $cmd = Get-Command $Name -ErrorAction SilentlyContinue
    if ($cmd) { return $cmd.Source }
    $candidates = Get-ChildItem "C:\Program Files\PostgreSQL\*\bin\$Name.exe" -ErrorAction SilentlyContinue |
        Sort-Object FullName -Descending
    if ($candidates) { return $candidates[0].FullName }
    throw "$Name nao encontrado no PATH nem em C:\Program Files\PostgreSQL\*\bin. Instale o cliente do PostgreSQL ou ajuste o PATH."
}

$pgRestore = Find-PgTool "pg_restore"
$psql = Find-PgTool "psql"

Write-Host "Restaurando $DumpFile em $DbUser@${DbHost}:${Port}/$DbName ..."
Write-Host "ATENCAO: tabelas existentes em '$DbName' serao apagadas e recriadas (--clean --if-exists)."

$env:PGPASSWORD = $Password
try {
    # "pagamento" e "usuario" (ver README) nao fazem parte do dump do sistema legado -
    # sao tabelas adicionadas por esta migracao. "pagamento" tem uma FK pra "socio", o
    # que trava o "--clean" do pg_restore (nao consegue derrubar/recriar "socio" com uma
    # FK de fora do dump apontando pra ela). Solucao: tira "pagamento" do caminho antes
    # do restore (salvando as linhas, se houver) e recria depois - assim o restore fica
    # idempotente e funciona tanto local quanto num Postgres gerenciado (Render), onde
    # nao da pra simplesmente trocar o banco inteiro de nome.
    $pagamentoBackup = Join-Path ([System.IO.Path]::GetTempPath()) "pagamento_pre_restore.csv"
    $pagamentoExists = & $psql -h $DbHost -p $Port -U $DbUser -d $DbName -tAc "SELECT to_regclass('public.pagamento') IS NOT NULL;"
    if ($pagamentoExists.Trim() -eq "t") {
        Write-Host "Salvando linhas atuais de 'pagamento' (sera recriada depois do restore)..."
        & $psql -h $DbHost -p $Port -U $DbUser -d $DbName -c "\copy pagamento TO '$pagamentoBackup' WITH CSV"
        & $psql -h $DbHost -p $Port -U $DbUser -d $DbName -c "DROP TABLE pagamento CASCADE;"
    } else {
        $pagamentoExists = "f"
    }

    & $pgRestore -h $DbHost -p $Port -U $DbUser -d $DbName --clean --if-exists --no-owner --no-privileges -v $DumpFile
    if ($LASTEXITCODE -ne 0) {
        Write-Warning "pg_restore saiu com codigo $LASTEXITCODE - revise os avisos acima (alguns sao normais, ex: role/owner nao encontrado)."
    }

    Write-Host "Recriando 'pagamento' (nao faz parte do dump do sistema legado)..."
    & $psql -h $DbHost -p $Port -U $DbUser -d $DbName -c "CREATE TABLE IF NOT EXISTS pagamento (id_pag SERIAL PRIMARY KEY, id_socio INTEGER NOT NULL REFERENCES socio(id_socio), pagamento VARCHAR(255));"
    if ($pagamentoExists.Trim() -eq "t") {
        & $psql -h $DbHost -p $Port -U $DbUser -d $DbName -c "\copy pagamento FROM '$pagamentoBackup' WITH CSV"
        & $psql -h $DbHost -p $Port -U $DbUser -d $DbName -c "SELECT setval('pagamento_id_pag_seq', COALESCE((SELECT max(id_pag) FROM pagamento), 1));"
        Remove-Item $pagamentoBackup -ErrorAction SilentlyContinue
    }

    Write-Host "Garantindo que 'usuario' existe (nao faz parte do dump; ver README para o seed do admin)..."
    & $psql -h $DbHost -p $Port -U $DbUser -d $DbName -c "CREATE TABLE IF NOT EXISTS usuario (id_usuario SERIAL PRIMARY KEY, username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(100) NOT NULL, enabled BOOLEAN NOT NULL DEFAULT TRUE, role VARCHAR(20) NOT NULL DEFAULT 'ADMIN');"
} finally {
    Remove-Item Env:\PGPASSWORD -ErrorAction SilentlyContinue
}

Write-Host "Restore concluido."
