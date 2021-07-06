cd back-end/
docker build -t jete .
docker run --name jeteback -d -p 8080:8080 jete
cd ../front-end/reactjs
docker build -t jetefront .
docker run -it --rm -v "$(pwd):/src"  -v /node_modules -p 3000:3000 -e CHOKIDAR_USEPOLLING=true jetefront