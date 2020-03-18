@echo off
echo Start

for %%G in (hw2-testcases\priv\*.java) do (
  echo ---- Run %%~nG ----
  java -jar ..\out\artifacts\Compiler_jar\Compiler.jar %%~fG
  echo.
)

REM for %%G in (samples\*.spg) do (
REM   javac -d judge\tmp %%~pG%%~nG.java
REM   java -cp judge\tmp %%~nG > judge\goal.txt

REM   echo ---- sPiglet %%~nG ----
REM   java -jar judge\spp.jar < %%~fG
REM   java -jar judge\pgi.jar < %%~fG > judge\spiglet.txt
REM   fc judge\spiglet.txt judge\goal.txt

REM   echo ---- Kanga %%~nG ----
REM   java -jar judge\kgi.jar < %%~pG%%~nG.kg > judge\kanga.txt
REM   fc judge\kanga.txt judge\goal.txt
REM   echo.
REM )

pause

REM cd judge
REM del /Q spiglet.txt
REM del /Q kanga.txt
REM del /Q goal.txt
REM del /S /Q tmp
REM rmdir /Q tmp