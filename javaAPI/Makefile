run dev:
	open -a "Docker" && \
	docker compose up -d && \
	set -a && source .env && set +a && \
	./mvnw spring-boot:run

run:
	set -a && source .env && set +a && \
    ./mvnw spring-boot:run
clean:
	rm -r target