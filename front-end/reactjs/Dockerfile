FROM node:13.12.0-alpine as build

WORKDIR ../reactjs

ENV PATH /node_modules/.bin$PATH

COPY package.json ./
COPY package-lock.json ./
RUN npm install

COPY . ./
RUN npm run build


CMD ["npm","start"]