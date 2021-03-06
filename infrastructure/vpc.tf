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

resource "aws_subnet" "devicenator-vpc-subnet-public-1" {
  vpc_id = aws_vpc.devicenator-vpc.id
  cidr_block = "10.0.1.0/24"
  availability_zone = local.availability_zones[0]
  map_public_ip_on_launch = true

  tags = {
    Name = "devicenator-vpc-subnet-public-1"
  }
}

resource "aws_subnet" "devicenator-vpc-subnet-public-2" {
  vpc_id = aws_vpc.devicenator-vpc.id
  cidr_block = "10.0.2.0/24"
  availability_zone = local.availability_zones[1]
  map_public_ip_on_launch = true

  tags = {
    Name = "devicenator-vpc-subnet-public-2"
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

resource "aws_route_table_association" "route-table-subnet-1-association" {
  subnet_id = aws_subnet.devicenator-vpc-subnet-public-1.id
  route_table_id = aws_route_table.devicenator-vpc-route-table.id
}

resource "aws_route_table_association" "route-table-subnet-2-association" {
  subnet_id = aws_subnet.devicenator-vpc-subnet-public-2.id
  route_table_id = aws_route_table.devicenator-vpc-route-table.id
}
