resource "aws_alb" "devicenator-api-load-balancer" {
  name = "devicenator-api-load-balancer"
  internal = false
  subnets = [aws_subnet.devicenator-vpc-subnet-public-1.id, aws_subnet.devicenator-vpc-subnet-public-2.id]
  security_groups = [aws_security_group.devicenator-api-security-group.id]

  tags = {
    Name = "devicenator-api-load-balancer"
  }
}


resource "aws_alb_listener" "alb-listener" {
  load_balancer_arn = aws_alb.devicenator-api-load-balancer.arn
  protocol = "HTTP"
  port = 8080

  default_action {
    target_group_arn = aws_alb_target_group.devicenator-api-target-group.arn
    type = "forward"
  }
}

resource "aws_alb_target_group" "devicenator-api-target-group" {
  name = "devicenator-api-target-group"
  target_type = "instance"
  protocol = "HTTP"
  port = 8080
  vpc_id = aws_vpc.devicenator-vpc.id

  health_check {
    protocol = "HTTP"
    port = "traffic-port"
    path = "/actuator/health"
    matcher = "200"
  }

  tags = {
    Name = "devicenator-api-target-group"
  }
}

resource "aws_alb_target_group_attachment" "devicenator-api-target-group-attachment" {
  target_group_arn = aws_alb_target_group.devicenator-api-target-group.arn
  target_id = aws_instance.devicenator-api.id
}

resource "aws_security_group" "devicenator-api-load-balancer-security-group" {
  name = "devicenator-api-load-balancer-security-group"
  description = "Allow HTTP 80 inbound traffic and all outbound traffic"
  vpc_id = aws_vpc.devicenator-vpc.id

  ingress {
    description = "HTTP 80"
    from_port = 80
    to_port = 80
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
    Name = "devicenator-api-load-balancer-security-group"
  }
}