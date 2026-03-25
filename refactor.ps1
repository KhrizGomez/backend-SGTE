$baseDir = "src/main/java/com/app/backend"
$dtosDir = "$baseDir/dtos/tramites"
$requestDir = "$dtosDir/request"
$responseDir = "$dtosDir/response"

New-Item -ItemType Directory -Force -Path $requestDir | Out-Null
New-Item -ItemType Directory -Force -Path $responseDir | Out-Null

$requestFiles = @(
    "CrearSolicitudRequestDTO.java",
    "RechazoSolicitudRequestDTO.java"
)

$responseFiles = @(
    "CategoriaTramiteResponseDTO.java",
    "DefinicionFlujoResponseDTO.java",
    "DetallesTramiteBaseResponseDTO.java",
    "DetallesTramiteResponseDTO.java",
    "EtapaProcesamientoResponseDTO.java",
    "MotivoRechazoResponseDTO.java",
    "PasoFlujoResponseDTO.java",
    "PasoFlujoTramiteResponseDTO.java",
    "PlazoTramiteResponseDTO.java",
    "RequisitoTramiteResponseDTO.java",
    "SeguimientoSolicitudResponseDTO.java",
    "SolicitudResponseDTO.java",
    "TipoTramiteResponseDTO.java",
    "TransicionFlujoResponseDTO.java"
)

function Move-And-Update {
    param($fileName, $subDir)
    
    $sourcePath = "$dtosDir/$fileName"
    $targetPath = "$dtosDir/$subDir/$fileName"
    
    if (Test-Path $sourcePath) {
        Move-Item -Path $sourcePath -Destination $targetPath -Force
        
        # Update package declaration in the target file
        $content = Get-Content $targetPath -Raw
        $content = $content -replace "package com\.app\.backend\.dtos\.tramites;", "package com.app.backend.dtos.tramites.$subDir;"
        Set-Content -Path $targetPath -Value $content -Encoding UTF8
        
        # Replace occurrences in all java files
        $className = $fileName -replace "\.java$", ""
        
        Get-ChildItem -Path "src/main/java" -Recurse -Filter "*.java" | ForEach-Object {
            $fileContent = Get-Content $_.FullName -Raw
            $newContent = $fileContent -replace "import com\.app\.backend\.dtos\.tramites\.$className;", "import com.app.backend.dtos.tramites.$subDir.$className;"
            if ($newContent -cne $fileContent) {
                Set-Content -Path $_.FullName -Value $newContent -Encoding UTF8
            }
        }
    }
}

foreach ($file in $requestFiles) { Move-And-Update $file "request" }
foreach ($file in $responseFiles) { Move-And-Update $file "response" }

Write-Host "Done!"