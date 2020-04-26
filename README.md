# Devicenator API

## Infrastructure deploy in local environment
Steps to deploy the infrastructure for `devicenator-api`
1. Configure the following environment variables
    * `AWS_ACCOUNT_ID`
    * `POSTGRES_USER` and `POSTGRES_PASSWORD`
2. `$ cd infrastructure`
3. `$ ./apply.sh`

## Travis configuration for building and deploying code
Set the environment variables:
*`AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` with the values for the AWS Travis user
* `AWS_DEFAULT_REGION`
* `AWS_ACCOUNT_ID`
* `POSTGRES_HOST` with the value for the DB endpoint in AWS
* `POSTGRES_USER`, `POSTGRES_PASSWORD`