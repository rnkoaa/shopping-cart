.PHONY: clean build

build:
	./gradlew clean assemble bootRepackage

clean:
	./gradlew clean

upload:
	./gradlew upload

unit-test:
	@./gradlew clean test -Dspring.profiles.active=unit-test