#!/bin/bash
set -ev

kubectl apply -f infrastructure/k8s
kubectl set image deployments/devicenator devicenator=aidamanna/devicenator:${SHA}

if ! kubectl rollout status deployment devicenator; then
    kubectl rollout undo deployment devicenator
    kubectl rollout status deployment devicenator
    exit 1
fi