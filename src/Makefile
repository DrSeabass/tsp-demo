## Make a class

%.class : %.java
	javac $^

all: tspdemo

tspdemo: Tspdemo.class Instance.class Bounds.class Solution.class
	jar -cvfe Tspdemo.jar Tspdemo $^

randomWalk: RandomWalk.class Tspdemo.class Instance.class Bounds.class Solution.class
	jar -cvfe RandomWalk.jar RandomWalk $^

.PHONY: clean

clean:
	- rm *.class
	- rm *.jar
	- rm *\~
