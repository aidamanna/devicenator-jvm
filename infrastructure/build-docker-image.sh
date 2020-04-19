#!/usr/bin/env bash

set -euxo pipefail

REPOSITORY="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
IMAGE_TAG="${REPOSITORY}/devicenator-api:devicenator-api-${SHA}"
ROLE_ARN="arn:aws:iam::${AWS_ACCOUNT_ID}:role/ecr-role"

ASSUMED_ROLE=$(
    aws sts assume-role \
        --role-arn ${ROLE_ARN} \
        --role-session-name build-docker-image
)

export AWS_SESSION_TOKEN=$(echo ${ASSUMED_ROLE} | jq -r '.Credentials.SessionToken')
export AWS_ACCESS_KEY_ID=$(echo ${ASSUMED_ROLE} | jq -r '.Credentials.AccessKeyId')
export AWS_SECRET_ACCESS_KEY=$(echo ${ASSUMED_ROLE} | jq -r '.Credentials.SecretAccessKey')

aws ecr get-login-password \
| docker login \
    --username AWS \
    --password-stdin ${REPOSITORY}

docker build -t ${IMAGE_TAG} .
docker push ${IMAGE_TAG}
