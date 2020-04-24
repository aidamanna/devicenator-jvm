# Devicenator API

## Infrastructure deploy in local environment
Steps to deploy the infrastructure for `devicenator-api`
1. `$ cd infrastructure`
2. `$ ./apply.sh`

## Travis configuration for building and deploying code
Set the environment variables:
*`AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` with the values for the AWS Travis user
* `AWS_DEFAULT_REGION`
* `AWS_ACCOUNT_ID`
