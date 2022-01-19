#!/bin/sh

echo Cleaning up...
rm src/models/*.class
rm src/services/*.class
rm src/Editor.class
rm Main.class

echo Building Editor files...
javac src/models/*.java
javac src/services/*.java
javac src/Editor.java
javac Main.java

echo Done.
