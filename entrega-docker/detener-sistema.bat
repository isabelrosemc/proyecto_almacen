@echo off
cd /d %~dp0
echo Deteniendo sistema...
docker compose down
echo.
echo Sistema detenido.
echo.
pause