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

function Show-Menu {
  Write-Host "=> OPTIONS:" -ForegroundColor Cyan
  Write-Host "1) Get document _source by ID" -ForegroundColor White
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
    default {
      Write-Host "Unknown option: $choice" -ForegroundColor Red
    }
  }

}