.PHONY: run test help

help:
	@echo "make run   - executa a aplicacao com Docker"
	@echo "make test  - executa os testes JUnit com Docker"

run:
	docker compose up --build --abort-on-container-exit app

test:
	docker compose --profile test run --build --rm test
