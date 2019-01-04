@echo off
set arg1=%1
IF "%arg1%"=="" goto USAGE
IF "%arg1%"=="help" goto USAGE

GOTO SETUP

:USAGE
echo "usage is as below:" 
echo %~n0%~x0 dirty [to avoid clean build]
echo %~n0%~x0 clean [for clean build]
set /p DUMMY=Hit ENTER to continue...
@echo off
GOTO ENDIT

:SETUP
@echo off
set JAVA_HOME=C:\Progra~1\Java\jdk1.8.0_161
SET DEVELOPMENT_HOME=C:\my_eclipse_workspace
SET PROJECT_LIST=(proj_a proj_b proj_c)

IF "%arg1%"=="dirty" GOTO DIRTY
IF "%arg1%"=="clean" GOTO CLEAN

:DIRTY:
	for %%i in %PROJECT_LIST% do (
		cd %DEVELOPMENT_HOME%\%%i\
		call C:\apache-maven-3.5.4\bin\mvn install -DskipTests | findstr "FAILURE" ^
			 && (echo Build failed on %%i & goto ENDIT) ^
			 || (echo Build succeeded on %%i)
	)
GOTO ENDIT

:CLEAN
	for %%i in %PROJECT_LIST% do (
		cd %DEVELOPMENT_HOME%\%%i\
		call C:\apache-maven-3.5.4\bin\mvn install -DskipTests | findstr "FAILURE" ^
			 && (echo Build failed on %%i & goto ENDIT) ^
			 || (echo Build succeeded on %%i)
	)
:ENDIT
Pause


Thanks,
Santhosh Cheeran
(Contractor - Credit Risk Technology H)

