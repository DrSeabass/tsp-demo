## Make a class

%.class : %.java
	javac $^

all: tspdemo randomWalk dfsSimple dfsCO hillClimb hillClimbGS

tspdemo: tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe Tspdemo.jar tsp/Tspdemo $^

randomWalk: referenceSols/RandomWalk.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe RandomWalk.jar referenceSols/RandomWalk $^

hillClimb: referenceSols/HillClimbing.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe HillClimb.jar referenceSols/HillClimbing $^

hillClimbGS: referenceSols/HillClimbingGreedyStart.class referenceSols/DFSChildOrdering.class referenceSols/HillClimbing.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class referenceSols/NearestCityState.class
	jar -cvfe HillClimbGreedyStart.jar referenceSols/HillClimbingGreedyStart $^

dfsSimple: referenceSols/DFSNoHeuristics.class referenceSols/State.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe DFSNoHeuristics.jar referenceSols/DFSNoHeuristics $^

dfsCO: referenceSols/DFSChildOrdering.class referenceSols/NearestCityState.class tsp/Tspdemo.class tsp/Instance.class tsp/Bounds.class tsp/Solution.class
	jar -cvfe DFSChildOrdering.jar referenceSols/DFSChildOrdering $^

.PHONY: clean

clean:
	- rm *.jar
	- rm tsp/*.class
	- rm tsp/*\~
	- rm referenceSols/*.class
	- rm referenceSols/*\~
