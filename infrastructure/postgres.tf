resource "aws_db_instance" "devicenator-db" {
  engine = "postgres"
  engine_version = "11.5"
  identifier = "devicenator-db"
  username = var.postgres_user
  password = var.postgres_password
  instance_class = "db.t2.micro"
  storage_type = "gp2"
  allocated_storage = 20
  db_subnet_group_name = aws_db_subnet_group.devicenator-db-subnet-group.name
  publicly_accessible = true
  availability_zone = local.availability_zones[1]
  port = 5432
  name = "devicenator"
  parameter_group_name = "default.postgres11"
  vpc_security_group_ids = [aws_security_group.devicenator-db-security-group.id]
}

resource "aws_db_subnet_group" "devicenator-db-subnet-group" {
  name = "devicenator-db-subnet-group"
  subnet_ids = [
    aws_subnet.devicenator-vpc-subnet-public-1.id,
    aws_subnet.devicenator-vpc-subnet-public-2.id
  ]

  tags = {
    Name = "devicenator-db-subnet-group"
  }
}

resource "aws_security_group" "devicenator-db-security-group" {
  name        = "devicenator-db-security-group"
  description = "Devicenator db security group"
  vpc_id      = aws_vpc.devicenator-vpc.id

  ingress {
    description = "Inbound traffic for Postgres"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    security_groups = [aws_security_group.devicenator-api-security-group.id]
  }

  tags = {
    Name = "devicenator-db-security-group"
  }
}