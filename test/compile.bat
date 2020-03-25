@echo off

if [%1]==[] (
  echo [42;30mUsage: $ compile.bat [folder][42;0m
) else (
  echo Start
  for %%t in (%1\*.java) do (
    echo [42;30m------------ Run %%~nt ------------[0m
    java -jar ../out/artifacts/Compiler_jar/Compiler.jar %%t
    echo.
  )
  pause
)
