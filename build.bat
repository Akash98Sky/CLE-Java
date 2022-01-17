@ECHO OFF
ECHO Cleaning up...
del src\models\*.class
del src\services\*.class
del src\Editor.class
del Main.class

ECHO Building Editor files...
javac src/models/*.java
javac src/services/*.java
javac src/Editor.java
javac Main.java

ECHO Done.