# Path to all-in-one JSON
$inputFile = "all.json"

# Output folder
$outputFolder = "./"
New-Item -ItemType Directory -Force -Path $outputFolder | Out-Null

# Read and parse JSON
$i18n = Get-Content $inputFile -Raw | ConvertFrom-Json

foreach ($lang in $i18n.PSObject.Properties.Name) {
    $langData = $i18n.$lang

    # Convert back to JSON with stable formatting
    $jsonOut = $langData | ConvertTo-Json -Depth 10 -Compress:$false

    # Output file path
    $outPath = Join-Path $outputFolder "$lang.json"

    # Write file
    Set-Content -Path $outPath -Value $jsonOut -Encoding UTF8

    Write-Host "Generated $outPath"
}
