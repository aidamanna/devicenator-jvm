terraform {
  backend "s3" {
    encrypt = true
    bucket = "devicenator-state-bucket"
    region = "eu-west-1"
    key = "devicenator-api-terraform.tfstate"
    dynamodb_table = "devicentator-state-lock"
  }
}

provider "aws" {
  profile = "default"
  region = local.region
}
