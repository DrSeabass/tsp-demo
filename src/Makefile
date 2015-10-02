## Make a class

%.class : %.java
	javac $^

all: tspdemo randomWalk dfsSimple

tspdemo: tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe Tspdemo.jar tsp/Tspdemo $^

randomWalk: referenceSols/RandomWalk.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe RandomWalk.jar referenceSols/RandomWalk $^

dfsSimple: referenceSols/DFSNoHeuristics.class referenceSols/State.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe DFSNoHeuristics.jar referenceSols/DFSNoHeuristics $^

.PHONY: clean

clean:
	- rm *.jar
	- rm tsp/*.class
	- rm tsp/*\~
	- rm referenceSols/*.class
	- rm referenceSols/*\~
