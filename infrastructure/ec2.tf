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
      "88.15.110.157/32"]
  }

  egress {
    description = "All outbound traffic"
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"]
  }

  tags = {
    Name = "devicenator-api-security-group"
  }
}

resource "aws_instance" "devicenator-api" {
  ami = "ami-04d5cc9b88f9d1d39"
  instance_type = "t2.micro"
  subnet_id = aws_subnet.devicenator-vpc-subnet-public.id
  vpc_security_group_ids = [aws_security_group.devicenator-api-security-group.id]
  key_name = "devicenator-api-key"
  user_data = <<EOF
              #!/bin/bash
              sudo yum update -y
              sudo yum install docker -y
              sudo service docker start
              sudo usermod -aG docker ec2-user
              EOF

  tags = {
    Name = "devicenator-api"
  }
}

resource "aws_eip" "devicenator-api-elastic-ip" {
  vpc = true
  instance = aws_instance.devicenator-api.id
}
