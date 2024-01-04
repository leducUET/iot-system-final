@echo off

set CMD=%*
IF ["%CMD%"] == [""] set CMD=up

docker volume ls -q | findstr /r "^streamsheets-data $" || docker volume create streamsheets-data

docker-compose ^
	-f "%~dp0\docker-compose.yml" ^
	%CMD%
