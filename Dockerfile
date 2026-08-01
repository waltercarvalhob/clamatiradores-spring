# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM maven:3-eclipse-temurin-26 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Os relatorios JasperReports (.jrxml) usam fontName="Arial"/"Times New Roman".
# A imagem -jre "crua" nao tem nenhuma fonte instalada (nem fontconfig), entao a
# geracao de PDF (iText) quebra em runtime so em producao - no Windows dev essas
# fontes ja existem no SO, por isso nunca apareceu localmente. fonts-liberation
# fornece "Liberation Sans"/"Liberation Serif" com metricas compativeis e o
# fontconfig do Debian mapeia Arial/Times New Roman pra elas automaticamente.
RUN apt-get update \
	&& apt-get install -y --no-install-recommends fontconfig fonts-liberation \
	&& rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar app.jar

# Render injeta a variavel PORT em runtime; application.yml ja le PORT com fallback.
EXPOSE 8080

# TieredStopAtLevel=1 (so JIT C1, pula o C2) + UseSerialGC: reduz bastante o tempo
# de boot em CPU limitada (ex.: plano gratuito do Render) - troca throughput de
# regime permanente (que esta app de baixo trafego nao precisa) por startup mais
# rapido, o que importa pra nao estourar o timeout de deploy.
ENTRYPOINT ["java", "-XX:TieredStopAtLevel=1", "-XX:+UseSerialGC", "-jar", "app.jar"]
