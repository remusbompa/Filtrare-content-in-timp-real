.PHONY: build clean run

build: tema

run:
	java -Xmx1G MainClass 

tema:
	javac *.java

clean:
	rm -rf *.class