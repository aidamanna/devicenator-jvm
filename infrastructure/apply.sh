#!/usr/bin/env bash

set -euxo pipefail

rm -rf ./.terraform
terraform init
terraform plan \
    -var "account_id=${AWS_ACCOUNT_ID}" \
    -var "postgres_user=${POSTGRES_USER}" \
    -var "postgres_password=${POSTGRES_PASSWORD}" \
    -out="devicenator-api.plan"
terraform apply "devicenator-api.plan"

rm ./devicenator-api.plan
rm -rf ./.terraform
