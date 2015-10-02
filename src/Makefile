## Make a class

%.class : %.java
	javac $^

all: tspdemo randomWalk

tspdemo: tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe Tspdemo.jar tsp/Tspdemo $^

randomWalk: referenceSols/RandomWalk.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe RandomWalk.jar referenceSols/RandomWalk $^

.PHONY: clean

clean:
	- rm *.jar
	- rm tsp/*.class
	- rm tsp/*\~
	- rm referenceSols/*.class
	- rm referenceSols/*\~
