set -o nounset
jarspath=$1

mkdir -p build
mkdir -p libs
cp "$1"/{junit-4.12,hamcrest-core-1.3}.jar libs/
javac -d build -classpath src src/edu/uiowa/cs/*.java
javac -d build -classpath src:test:libs/junit-4.12.jar:libs/hamcrest-core-1.3.jar test/edu/uiowa/cs/*.java
java -classpath build:libs/junit-4.12.jar:libs/hamcrest-core-1.3.jar org.junit.runner.JUnitCore edu.uiowa.cs.AssemblerTest
