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
    $cmd = Get-Command $Name -ErrorAction SilentlyContinue
    if ($cmd) { return $cmd.Source }
    $candidates = Get-ChildItem "C:\Program Files\PostgreSQL\*\bin\$Name.exe" -ErrorAction SilentlyContinue |
        Sort-Object FullName -Descending
    if ($candidates) { return $candidates[0].FullName }
    throw "$Name nao encontrado no PATH nem em C:\Program Files\PostgreSQL\*\bin. Instale o cliente do PostgreSQL ou ajuste o PATH."
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
