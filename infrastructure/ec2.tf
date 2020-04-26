resource "aws_instance" "devicenator-api" {
  ami = "ami-09266271a2521d06f"
  instance_type = "t2.micro"
  subnet_id = aws_subnet.devicenator-vpc-subnet-public-1.id
  vpc_security_group_ids = [
    aws_security_group.devicenator-api-security-group.id,
    aws_security_group.ssh-security-group.id
  ]
  key_name = "devicenator-api-key"

  tags = {
    Name = "devicenator-api"
  }
}

resource "aws_security_group" "devicenator-api-security-group" {
  name = "devicenator-api-security-group"
  description = "Allow HTTP inbound traffic and all outbound traffic"
  vpc_id = aws_vpc.devicenator-vpc.id

  ingress {
    description = "HTTP"
    from_port = 8080
    to_port = 8080
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    description = "All outbound traffic"
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "devicenator-api-security-group"
  }
}

resource "aws_security_group" "ssh-security-group" {
  name        = "ssh-security-group"
  description = "Allow SSH inbound traffic"
  vpc_id      = aws_vpc.devicenator-vpc.id

  ingress {
    description = "SSH from Aida"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["83.44.44.44/32"]
  }

  tags = {
    Name = "ssh-security-group"
  }
}

resource "aws_ecr_repository_policy" "ecr-repository-policy" {
  repository = aws_ecr_repository.devicenator-api.name

  policy = <<EOF
{
    "Version": "2008-10-17",
    "Statement": [
        {
            "Sid": "new policy",
            "Effect": "Allow",
            "Principal": "*",
            "Action": [
                "ecr:GetDownloadUrlForLayer",
                "ecr:BatchGetImage",
                "ecr:BatchCheckLayerAvailability",
                "ecr:PutImage",
                "ecr:InitiateLayerUpload",
                "ecr:UploadLayerPart",
                "ecr:CompleteLayerUpload",
                "ecr:DescribeRepositories",
                "ecr:GetRepositoryPolicy",
                "ecr:ListImages",
                "ecr:DeleteRepository",
                "ecr:BatchDeleteImage",
                "ecr:SetRepositoryPolicy",
                "ecr:DeleteRepositoryPolicy"
            ]
        }
    ]
}
EOF
}
