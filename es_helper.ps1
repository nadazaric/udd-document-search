param(
  [Parameter(Mandatory=$true)]
  [string]$Port,

  [Parameter(Mandatory=$true)]
  [string]$Index
)

Write-Host ""
Write-Host "================ Elasticsearch helper ================" -ForegroundColor Green
Write-Host ""

function Exit-App {
  Write-Host ""
  Write-Host "================ EXIT ================" -ForegroundColor Green
  exit
}

function Read-Choice {
  param([string]$Prompt)

  $x = Read-Host $Prompt
  if ($x -eq "0") { Exit-App }
  return $x
}

function Es-SourceById {
  param([Parameter(Mandatory=$true)][string]$Id)

  if ([string]::IsNullOrWhiteSpace($Id)) {
    Write-Host "ID is required." -ForegroundColor Red
    return
  }

  $encodedId = [System.Uri]::EscapeDataString($Id)
  $uri = "http://localhost:$Port/$Index/_search?pretty"

  $body = @{
    size = 1
    query = @{
      ids = @{
        values = @($encodedId)
      }
    }
    _source = $true
  } | ConvertTo-Json -Depth 10

  try {
    $res = Invoke-RestMethod -Method Post -Uri $uri -ContentType "application/json" -Body $body
  } catch {
    Write-Host "Request failed: $($_.Exception.Message)" -ForegroundColor Magenta
    return
  }

  if (-not $res.hits -or $res.hits.total.value -eq 0) {
    Write-Host "Not found: $Id" -ForegroundColor Red
    return
  }

  $json = ($res.hits.hits[0]._source | ConvertTo-Json -Depth 20)
  Write-Host $json -ForegroundColor DarkYellow
  Write-Host ""
}

function Delete-Index {
  try {
    $resp = Invoke-RestMethod -Method Delete -Uri "http://localhost:$Port/$Index" -ErrorAction Stop
    Write-Host "Index deleted successfully." -ForegroundColor Green
  } catch {
    Write-Host "Failed to delete index. Details:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
  }
}

function Show-AnalyzerConfig {
  try {
    $analysis = (Invoke-RestMethod -Method Get -Uri "http://localhost:$Port/$Index/_settings").$Index.settings.index.analysis
    if ($null -eq $analysis) {
      Write-Host "No analysis settings found on this index." -ForegroundColor DarkYellow
      return
    }
    $analysis | ConvertTo-Json -Depth 100
  } catch {
    Write-Host "Failed to fetch analyzer config. Details:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
  }
}

function Show-DocTokensById {
  param([Parameter(Mandatory=$true)][string]$Id)

  if ([string]::IsNullOrWhiteSpace($Id)) {
    Write-Host "ID is required." -ForegroundColor Red
    return
  }

  $encodedId = [System.Uri]::EscapeDataString($Id)
  $searchUri = "http://localhost:$Port/$Index/_search?pretty"

  $searchBody = @{
    size = 1
    query = @{
      ids = @{
        values = @($encodedId)
      }
    }
    _source = @("content")
  } | ConvertTo-Json -Depth 10

  try {
    $res = Invoke-RestMethod -Method Post -Uri $searchUri -ContentType "application/json" -Body $searchBody
  } catch {
    Write-Host "Request failed: $($_.Exception.Message)" -ForegroundColor Magenta
    return
  }

  if (-not $res.hits -or $res.hits.total.value -eq 0) {
    Write-Host "Not found: $Id" -ForegroundColor Red
    return
  }

  $src = $res.hits.hits[0]._source
  if ($null -eq $src -or $null -eq $src.content -or [string]::IsNullOrWhiteSpace($src.content)) {
    Write-Host "Field 'content' is missing or empty for document: $Id" -ForegroundColor Red
    return
  }

  $text = [string]$src.content
  $maxChars = 4000
  if ($text.Length -gt $maxChars) {
    Write-Host "Content is long ($($text.Length) chars). Analyzing first $maxChars chars." -ForegroundColor DarkYellow
    $text = $text.Substring(0, $maxChars)
  }

  $analyzeUri = "http://localhost:$Port/$Index/_analyze?pretty"
  $analyzeBody = @{
    analyzer = "serbian_custom"
    text = $text
  } | ConvertTo-Json -Depth 10

  try {
    $anRes = Invoke-RestMethod -Method Post -Uri $analyzeUri -ContentType "application/json" -Body $analyzeBody
  } catch {
    Write-Host "Analyze request failed: $($_.Exception.Message)" -ForegroundColor Magenta
    return
  }

  if ($null -eq $anRes.tokens) {
    Write-Host "No tokens returned." -ForegroundColor DarkYellow
    return
  }

  $tokens = $anRes.tokens | Select-Object token, position, start_offset, end_offset, type
  $json = ($tokens | ConvertTo-Json -Depth 10)
  Write-Host $json -ForegroundColor DarkYellow
  Write-Host ""
}

function Show-Menu {
  Write-Host "=> OPTIONS:" -ForegroundColor Cyan
  Write-Host "1) Get document _source by ID" -ForegroundColor White
  Write-Host "2) Delete Index" -ForegroundColor White
  Write-Host "3) Show Analayzer Config" -ForegroundColor White
  Write-Host "4) Show tokens for document by ID" -ForegroundColor White
  Write-Host "0) Exit" -ForegroundColor White
  Write-Host ""
}

while ($true) {
  Show-Menu
  $choice = Read-Choice "Choose option"

  switch ($choice) {
    "1" {
      $id = Read-Choice "Enter document ID"
      Es-SourceById -Id $id
    }
    "2" {
      Delete-Index
    }
    "3" {
      Show-AnalyzerConfig
    }
    "4" {
      $id = Read-Choice "Enter document ID"
      Show-DocTokensById -Id $id
    }
    default {
      Write-Host "Unknown option: $choice" -ForegroundColor Red
    }
  }

}