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
  availability_zone = local.availability_zone
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

resource "aws_security_group" "devicenator-api-security-group" {
  name = "devicenator-api-security-group"
  description = "Allow SSH inbound traffic and all outbound traffic"
  vpc_id = aws_vpc.devicenator-vpc.id

  ingress {
    description = "SSH from Aida"
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = [
      "88.15.110.157/32"
    ]
  }

  ingress {
    description = "HTTP"
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  egress {
    description = "All outbound traffic"
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  tags = {
    Name = "devicenator-api-security-group"
  }
}
