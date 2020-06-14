@echo off

if [%1]==[] (
  echo [42;30mUsage: $ run.bat [folder][42;0m
) else (
  echo Start
  for %%t in (%1\*.java) do (
    echo [42;30m------------ Run %%~nt ------------[0m
    java -jar ../out/artifacts/Compiler.jar %%t
    echo.
  )
  pause
)
