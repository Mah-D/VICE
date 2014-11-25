
vpc:
	(cd compiler && make)

clean:
	(cd compiler && make clean)
	(cd apps && make clean)
	(cd bench && make clean)
	(cd test && make clean)
