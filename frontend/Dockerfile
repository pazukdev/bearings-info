FROM node:lts-alpine
RUN mkdir /bearings-info-frontend
WORKDIR /bearings-info-frontend/
COPY package*.json ./
RUN npm install
COPY . .
ENTRYPOINT ["npm", "run", "serve", "--", "--port", "8091"]