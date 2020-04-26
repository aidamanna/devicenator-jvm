resource "aws_instance" "devicenator-api" {
  ami           = "ami-09266271a2521d06f"
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
}

//resource "aws_iam_role" "ec2-role" {
//  name = "ec2-role"
//  path = "/"
//  assume_role_policy = data.aws_iam_policy_document.ec2-assume-rol-policy-document.json
//}
//
//data "aws_iam_policy_document" "ec2-assume-rol-policy-document" {
//  statement {
//    actions = [
//      "sts:AssumeRole"]
//
//    principals {
//      type = "Service"
//      identifiers = [
//        "ec2.amazonaws.com"]
//    }
//  }
//}
//


//resource "aws_iam_role_policy_attachment" "ec2-role-attachment" {
//  role = aws_iam_role.ec2-role.name
//  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
//}





resource "aws_ecr_repository_policy" "foopolicy" {
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
