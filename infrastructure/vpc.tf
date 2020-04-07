resource "aws_vpc" "devicenator-vpc" {
  cidr_block = "10.0.0.0/16"
  instance_tenancy = "default"
  enable_dns_support = true
  enable_dns_hostnames = true

  tags = {
    Name = "devicenator-vpc"
  }
}

resource "aws_internet_gateway" "devicenator-vpc-internet-gateway" {
  vpc_id = aws_vpc.devicenator-vpc.id

  tags = {
    Name = "devicenator-vpc-internet-gateway"
  }
}

resource "aws_subnet" "devicenator-vpc-subnet-public" {
  vpc_id = aws_vpc.devicenator-vpc.id
  cidr_block = "10.0.1.0/24"
  availability_zone = "eu-west-1a"
  map_public_ip_on_launch = true

  tags = {
    Name = "devicenator-vpc-subnet-public"
  }
}

resource "aws_route_table" "devicenator-vpc-route-table" {
  vpc_id = aws_vpc.devicenator-vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.devicenator-vpc-internet-gateway.id
  }

  tags = {
    Name = "devicenator-vpc-route-table"
  }
}

resource "aws_route_table_association" "route-table-subnet-association" {
  subnet_id = aws_subnet.devicenator-vpc-subnet-public.id
  route_table_id = aws_route_table.devicenator-vpc-route-table.id
}
