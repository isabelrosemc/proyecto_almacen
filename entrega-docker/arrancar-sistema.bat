@echo off
title Lanzador Sistema de Microservicios (Docker)
cd /d %~dp0
cls

echo ==========================================================
echo      SISTEMA DE GESTION DE ALMACEN - MODO DOCKER
echo ==========================================================
echo.

:: ==========================================================
:: 1. LEVANTAR MYSQL
:: ==========================================================
echo [1/4] Levantando MySQL...
docker compose up -d mysql-db

echo Esperando a que MySQL este completamente listo...
timeout /t 20 /nobreak >nul
echo.

:: ==========================================================
:: 2. LEVANTAR EUREKA
:: ==========================================================
echo [2/4] Levantando Eureka Server...
docker compose up -d eureka-server

echo Esperando a que Eureka inicie completamente...
timeout /t 25 /nobreak >nul
echo.

:: ==========================================================
:: 3. LEVANTAR MICROSERVICIOS
:: ==========================================================
echo [3/4] Levantando Microservicios...
docker compose up -d ^
ms-auth ^
ms-categoria ^
ms-clientes ^
ms-proveedores ^
ms-usuarios ^
ms-productos ^
ms-stock ^
ms-compras ^
ms-pagos ^
ms-ventas ^
ms-reportes

echo Esperando a que los microservicios se registren en Eureka...
timeout /t 20 /nobreak >nul
echo.

:: ==========================================================
:: 4. LEVANTAR API GATEWAY
:: ==========================================================
echo [4/4] Levantando API Gateway...
docker compose up -d api-gateway

echo.
echo ==========================================================
echo          SISTEMA INICIADO CORRECTAMENTE
echo ==========================================================
echo.
echo Eureka  : http://localhost:8761
echo Gateway : http://localhost:8080
echo MySQL   : localhost:3308
echo.
echo Para revisar el estado ejecuta:
echo docker compose ps
echo.
echo Para detener el sistema utiliza:
echo detener-sistema.bat
echo.
pause