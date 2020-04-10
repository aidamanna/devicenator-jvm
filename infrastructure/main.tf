terraform {
  backend "s3" {
    encrypt = true
    bucket = "devicenator-terraform-state-bucket"
    region = "eu-west-1"
    key = "devicenator-api-terraform.tfstate"
    dynamodb_table = "devicentator-terraform-state-lock"
  }
}

provider "aws" {
  profile = "default"
  region = var.region
}
