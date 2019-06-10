FROM node:lts-alpine
RUN mkdir /app
COPY package*.json /app/
WORKDIR /app/
COPY . /app/
RUN npm install
EXPOSE 8091