all:
	javac *.java
run: all
	java C4Game
clean:
	rm *.class
