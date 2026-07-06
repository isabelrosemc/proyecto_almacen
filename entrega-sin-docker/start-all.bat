@echo off
REM ============================================================
REM start-all.bat
REM Proyecto Almacen - Inicio de microservicios Spring Boot
REM Ejecuta cada JAR en una ventana CMD separada.
REM ============================================================

cd /d "%~dp0"

echo ============================================================
echo   INICIANDO ARQUITECTURA DE MICROSERVICIOS - PROYECTO ALMACEN
echo ============================================================
echo.

REM ------------------------------------------------------------
REM 1) Eureka Server debe iniciar primero
REM ------------------------------------------------------------
echo [1/13] Iniciando Eureka Server...
start "EUREKA-SERVER" cmd /k "title EUREKA-SERVER && java -jar eureka-server-1.0.0-SNAPSHOT.jar"

echo Esperando 20 segundos para que Eureka Server levante correctamente...
timeout /t 20 /nobreak > nul
echo.

REM ------------------------------------------------------------
REM 2) API Gateway inicia despues de Eureka
REM ------------------------------------------------------------
echo [2/13] Iniciando API Gateway...
start "API-GATEWAY" cmd /k "title API-GATEWAY && java -jar api-gateway-1.0.0-SNAPSHOT.jar"

echo Esperando 10 segundos para que API Gateway se registre en Eureka...
timeout /t 10 /nobreak > nul
echo.

REM ------------------------------------------------------------
REM 3) Microservicios restantes
REM ------------------------------------------------------------

echo [3/13] Iniciando ms-auth...
start "MS-AUTH" cmd /k "title MS-AUTH && java -jar ms-auth-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [4/13] Iniciando ms-categoria...
start "MS-CATEGORIA" cmd /k "title MS-CATEGORIA && java -jar ms-categoria-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [5/13] Iniciando ms-clientes...
start "MS-CLIENTES" cmd /k "title MS-CLIENTES && java -jar ms-clientes-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [6/13] Iniciando ms-proveedores...
start "MS-PROVEEDORES" cmd /k "title MS-PROVEEDORES && java -jar ms-proveedores-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [7/13] Iniciando ms-usuarios...
start "MS-USUARIOS" cmd /k "title MS-USUARIOS && java -jar ms-usuarios-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [8/13] Iniciando ms-productos...
start "MS-PRODUCTOS" cmd /k "title MS-PRODUCTOS && java -jar ms-productos-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [9/13] Iniciando ms-stock...
start "MS-STOCK" cmd /k "title MS-STOCK && java -jar ms-stock-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [10/13] Iniciando ms-compras...
start "MS-COMPRAS" cmd /k "title MS-COMPRAS && java -jar ms-compras-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [11/13] Iniciando ms-pagos...
start "MS-PAGOS" cmd /k "title MS-PAGOS && java -jar ms-pagos-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [12/13] Iniciando ms-ventas...
start "MS-VENTAS" cmd /k "title MS-VENTAS && java -jar ms-ventas-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo [13/13] Iniciando ms-reportes...
start "MS-REPORTES" cmd /k "title MS-REPORTES && java -jar ms-reportes-1.0.0-SNAPSHOT.jar"
timeout /t 5 /nobreak > nul

echo.
echo ============================================================
echo   TODOS LOS MICROSERVICIOS FUERON INICIADOS
echo ============================================================
echo.
echo Revisa Eureka en:
echo http://localhost:8761
echo.
echo Para detenerlos, ejecuta stop-all.bat
echo.
pause
