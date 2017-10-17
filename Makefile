.PHONY: clean build

build:
	./gradlew clean assemble bootRepackage

clean:
	./gradlew clean

upload:
	./gradlew upload