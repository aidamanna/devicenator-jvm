echo $SHA

docker build -t aidamanna/devicenator:$SHA .
docker login --username $DOCKER_USER --password $DOCKER_PASSWORD
docker push aidamanna/devicenator:$SHA

kubectl apply -f infrastructure/k8s
kubectl set image deployments/deployment devicenator=aidamanna/devicenator:$SHA
