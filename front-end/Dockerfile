# Build the app in a node container, then copy the bundle to an lightweight nginx container.

# 22 was lts at the time this project was made (december 2024)
FROM node:22-alpine AS build

WORKDIR /src

# Install packages
# Doing this separately improves build caching
COPY package.json .
COPY package-lock.json .
RUN npm ci

# Copy other source files and build
COPY . .
RUN npm run build

FROM nginx:mainline-alpine AS final
COPY docker/default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /src/dist /usr/share/nginx/html
