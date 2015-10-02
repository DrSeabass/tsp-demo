## Make a class

%.class : %.java
	javac $^

all: tspdemo

tspdemo: Tspdemo.class Instance.class Bounds.class Solution.class
	jar -cvfe Tspdemo.jar Tspdemo $^

randomWalk: referenceSols/RandomWalk.class Tspdemo.class Instance.class Bounds.class Solution.class
	jar -cvfe RandomWalk.jar referenceSols/RandomWalk $^

.PHONY: clean

clean:
	- rm *.class
	- rm *.jar
	- rm *\~
	- rm referenceSols/*.class
	- rm referenceSols/*.jar
	- rm referenceSols/*\~
