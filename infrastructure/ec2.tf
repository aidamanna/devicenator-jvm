resource "aws_security_group" "devicenator-api-security-group" {
  name        = "devicenator-api-security-group"
  description = "Allow SSH inbound traffic and all outbound traffic"
  vpc_id      = aws_vpc.devicenator-vpc.id

  ingress {
    description = "SSH from Aida"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["192.168.1.37/32"]
  }

  egress {
    description = "All outbound traffic"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "devicenator-api-security-group"
  }
}
