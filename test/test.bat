@echo off
echo Start

<<<<<<< HEAD
for %%G in (hw2-testcases\priv\*.java) do (
  echo ---- Run %%~nG ----
  java -jar ..\out\artifacts\Compiler_jar\Compiler.jar %%~fG
=======
REM for %%t in (hw2-test\Public\*.java) do (
REM   echo [42;30m------------ Run %%~nt ------------[0m
REM   java -jar ../out/artifacts/Compiler_jar/Compiler.jar %%t
REM   echo.
REM )


for %%t in (hw3-test\Private\*.java) do (
  echo [42;30m------------ Run %%~nt ------------[0m
  java -jar ../out/artifacts/Compiler_jar/Compiler.jar %%t
  echo [42;30m---------- Spiglet %%~nt ----------[0m
  java -jar ../tools/spp.jar < %%~pnt.spg
  java -jar ../tools/pgi.jar < %%~pnt.spg > %%~pnt.txt
  fc %%~pnt.out %%~pnt.txt
>>>>>>> 9c71cfa... feat(src/spiglet/*): implement register allocation
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