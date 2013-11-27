all:
	mpijavac -cp .:lib/commons-math3-3.2.jar ./constant/*.java ./Interface/*.java ./util/*.java ./sequential/*.java ./parallel/*.java ./Test/*.java	
clean:
	find . -name *.class | xargs rm -rf >> a.out
	find -name *.class | xargs rm -rf >> a.out
	rm a.out
