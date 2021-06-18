# Certifications App Complete

# Start Dev Server

    gradlew bootRun -i --no-daemon

# Open SwaggerUI
Open your browser and visit:

    http://localhost:8080/swagger-ui/

# Build war file

    gradle bootJar -i --no-daemon --parallel

# Build docker image

    docker build -t marianferenc/java-academy-2021-backend:latest .

# Push docker image

    docker push marianferenc/java-academy-2021-backend:latest

# Run docker image

    docker run -p 8080:8080 -it marianferenc/java-academy-2021-backend:latest