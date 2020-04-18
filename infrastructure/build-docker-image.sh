#!/usr/bin/env bash

set -euxo pipefail

REPOSITORY="832237568284.dkr.ecr.eu-west-1.amazonaws.com/devicenator-api"
AWS_REGION="eu-west-1"
ROLE_ARN="arn:aws:iam::832237568284:role/ecr-role"

ASSUMED_ROLE=$(
    aws sts assume-role \
        --role-arn ${ROLE_ARN} \
        --role-session-name devicenator-api-build-docker-image
)

export AWS_SESSION_TOKEN=$(echo ${ASSUMED_ROLE} | jq -r '.Credentials.SessionToken')
export AWS_ACCESS_KEY_ID=$(echo ${ASSUMED_ROLE} | jq -r '.Credentials.AccessKeyId')
export AWS_SECRET_ACCESS_KEY=$(echo ${ASSUMED_ROLE} | jq -r '.Credentials.SecretAccessKey')

eval $(aws ecr get-login-password --region ${AWS_REGION})

docker build -t ${REPOSITORY}:${SHA} .
docker push ${REPOSITORY}:${SHA}
