<#
.SYNOPSIS
    Faz backup do banco bdsocio (local ou remoto) em formato custom do pg_dump,
    pronto para restaurar com restore.ps1.

.EXAMPLE
    # Backup do banco local (usa as mesmas variaveis padrao da aplicacao)
    .\db\backup.ps1

.EXAMPLE
    # Backup do banco do Render, informando host/porta/senha manualmente
    .\db\backup.ps1 -DbHost dpg-xxxxx.oregon-postgres.render.com -Port 5432 -DbName clamatiradores -DbUser clamatiradores_user -Password "SENHA_DO_RENDER"

.NOTES
    Gera um arquivo em db\backups\bdsocio_<timestamp>.dump (pasta ignorada pelo git,
    ver .gitignore) - nunca commite backups reais, eles contem dados pessoais dos socios.
#>
param(
    [string]$DbHost = $(if ($env:DB_HOST) { $env:DB_HOST } else { "localhost" }),
    [string]$Port   = $(if ($env:DB_PORT) { $env:DB_PORT } else { "5432" }),
    [string]$DbName = $(if ($env:DB_NAME) { $env:DB_NAME } else { "bdsocio" }),
    [string]$DbUser = $(if ($env:DB_USER) { $env:DB_USER } else { "postgres" }),
    [string]$Password = $(if ($env:DB_PASSWORD) { $env:DB_PASSWORD } else { "252107" }),
    [string]$OutDir = "$PSScriptRoot\backups"
)

$ErrorActionPreference = "Stop"

function Find-PgTool([string]$Name) {
    # Prefere sempre a versao mais nova instalada: hosts gerenciados modernos (ex.: o
    # Postgres do Render) exigem negociacao SSL direta (sslnegotiation=direct), suportada
    # so a partir do libpq 17+ - um psql/pg_dump/pg_restore 13 ou 14 falha contra eles com
    # "FATAL: No SNI information found", mesmo estando no PATH.
    $roots = @("C:\Program Files\PostgreSQL\*\bin\$Name.exe", "C:\PostgreSQL*\bin\$Name.exe")
    $candidates = Get-ChildItem $roots -ErrorAction SilentlyContinue |
        ForEach-Object {
            $verFolder = Split-Path (Split-Path $_.FullName -Parent) -Leaf
            [PSCustomObject]@{ Path = $_.FullName; Version = [int]($verFolder -replace '\D', '') }
        } |
        Sort-Object Version -Descending

    if ($candidates) { return $candidates[0].Path }

    $cmd = Get-Command $Name -ErrorAction SilentlyContinue
    if ($cmd) { return $cmd.Source }

    throw "$Name nao encontrado em C:\Program Files\PostgreSQL\*\bin, C:\PostgreSQL*\bin nem no PATH. Instale o cliente do PostgreSQL (de preferencia versao 17+, necessaria para hosts como o Render) ou ajuste o PATH."
}

$pgDump = Find-PgTool "pg_dump"

if (-not (Test-Path $OutDir)) {
    New-Item -ItemType Directory -Path $OutDir | Out-Null
}

$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$outFile = Join-Path $OutDir "bdsocio_$timestamp.dump"

Write-Host "Fazendo backup de $DbUser@${DbHost}:${Port}/$DbName para $outFile ..."

$env:PGPASSWORD = $Password
try {
    & $pgDump -h $DbHost -p $Port -U $DbUser -d $DbName -F c -f $outFile
    if ($LASTEXITCODE -ne 0) {
        throw "pg_dump saiu com codigo $LASTEXITCODE"
    }
} finally {
    Remove-Item Env:\PGPASSWORD -ErrorAction SilentlyContinue
}

$sizeKb = [Math]::Round((Get-Item $outFile).Length / 1KB, 1)
Write-Host "Backup concluido: $outFile ($sizeKb KB)"
Write-Host "Para restaurar: .\db\restore.ps1 -DumpFile `"$outFile`" -DbHost <destino> -DbUser <usuario> -Password <senha> -DbName <banco>"
