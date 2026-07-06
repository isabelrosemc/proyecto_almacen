@echo off
REM ============================================================
REM stop-all.bat
REM Proyecto Almacen - Detencion de microservicios Spring Boot
REM Cierra solo procesos Java que ejecuten los JARs del proyecto.
REM ============================================================

cd /d "%~dp0"

echo ============================================================
echo   DETENIENDO MICROSERVICIOS - PROYECTO ALMACEN
echo ============================================================
echo.

REM ------------------------------------------------------------
REM Funcion mediante PowerShell:
REM Busca procesos java.exe cuyo CommandLine contenga el nombre del JAR.
REM Esto evita cerrar otros procesos Java que no pertenezcan al proyecto.
REM ------------------------------------------------------------

echo Deteniendo API Gateway...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*api-gateway-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-reportes...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-reportes-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-ventas...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-ventas-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-pagos...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-pagos-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-compras...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-compras-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-stock...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-stock-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-productos...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-productos-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-usuarios...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-usuarios-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-proveedores...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-proveedores-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-clientes...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-clientes-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-categoria...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-categoria-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo ms-auth...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*ms-auth-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo Deteniendo Eureka Server...
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-CimInstance Win32_Process -Filter \"name='java.exe'\" | Where-Object { $_.CommandLine -like '*eureka-server-1.0.0-SNAPSHOT.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
timeout /t 2 /nobreak > nul

echo.
echo ============================================================
echo   TODOS LOS MICROSERVICIOS DEL PROYECTO FUERON DETENIDOS
echo ============================================================
echo.
pause
