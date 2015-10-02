## Make a class

%.class : %.java
	javac $^

all: tspdemo

tspdemo: Tspdemo.class Instance.class Bounds.class
	jar -cvfe Tspdemo.jar Tspdemo $^

.PHONY: clean

clean:
	- rm *.class
	- rm *.jar
	- rm *\~
