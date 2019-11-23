#!/bin/bash
set -ev

kubectl apply -f infrastructure/k8s
kubectl set image deployments/devicenator-deployment devicenator=aidamanna/devicenator:${SHA}

if ! kubectl rollout status deployment devicenator-deployment; then
    kubectl rollout undo deployment devicenator-deployment
    kubectl rollout status deployment devicenator-deployment
    exit 1
fi