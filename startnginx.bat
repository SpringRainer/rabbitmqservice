@echo off

tasklist /nh>C:\Users\Lenovo\Desktop\giteeRepository\origin\1.txt

find /i "nginx.exe" C:\Users\Lenovo\Desktop\giteeRepository\origin\1.txt > C:\Users\Lenovo\Desktop\giteeRepository\origin\2.txt

for /f %%l in (C:\Users\Lenovo\Desktop\giteeRepository\origin\2.txt) do set change= %%l

del C:\Users\Lenovo\Desktop\giteeRepository\origin\1.txt

del C:\Users\Lenovo\Desktop\giteeRepository\origin\2.txt

echo the value is %change%

if "%change%" == " nginx.exe" (
taskkill /f /t /im "nginx.exe"
)

cd /d D:\nginx_1.7.11.3_Gryphon

nginx -t

set /p ss= could you choose one to determine how to execute(y/n):

if "%ss%" == "y"  (
echo the value you type in is %ss%
start nginx
) else if "%ss%" == "n" (echo the value you type in is %ss%
nginx -s stop
)

timeout /nobreak /t 2

exit



