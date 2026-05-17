# Download and setup Maven
$MavenVersion = "3.9.5"
$MavenHome = "C:\Maven"
$MavenUrl = "https://archive.apache.org/dist/maven/maven-3/$MavenVersion/binaries/apache-maven-$MavenVersion-bin.zip"
$ZipPath = "$env:TEMP\apache-maven-$MavenVersion-bin.zip"

if (-not (Test-Path $MavenHome)) {
    Write-Host "Downloading Maven $MavenVersion..."
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    (New-Object System.Net.WebClient).DownloadFile($MavenUrl, $ZipPath)
    
    Write-Host "Extracting Maven..."
    Expand-Archive -Path $ZipPath -DestinationPath "C:\" -Force
    Rename-Item -Path "C:\apache-maven-$MavenVersion" -NewName "Maven" -Force
    Remove-Item $ZipPath -Force
    Write-Host "Maven installed successfully at $MavenHome"
} else {
    Write-Host "Maven already exists at $MavenHome"
}

# Add Maven to PATH
$env:PATH = "$MavenHome\bin;" + $env:PATH
[Environment]::SetEnvironmentVariable("PATH", "$MavenHome\bin;" + [Environment]::GetEnvironmentVariable("PATH", "Machine"), "Machine")

# Verify Maven installation
mvn --version
