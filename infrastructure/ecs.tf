# IAM ROLES

# ECS instance role

resource "aws_iam_role" "ecs-instance-role" {
  name = "ecs-instance-role"
  path = "/"
  assume_role_policy = data.aws_iam_policy_document.ecs-instance-assume-role-policy-document.json
}

data "aws_iam_policy_document" "ecs-instance-assume-role-policy-document" {
  statement {
    actions = [
      "sts:AssumeRole"
    ]
    principals {
      type = "Service"
      identifiers = [
        "ec2.amazonaws.com"
      ]
    }
  }
}

resource "aws_iam_role_policy_attachment" "ecs-instance-role-attachment" {
  role = aws_iam_role.ecs-instance-role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceforEC2Role"
}

# ECS service role

resource "aws_iam_role" "ecs-service-role" {
  name = "ecs-service-role"
  path = "/"
  assume_role_policy = data.aws_iam_policy_document.ecs-service-assume-rol-policy-document.json
}

data "aws_iam_policy_document" "ecs-service-assume-rol-policy-document" {
  statement {
    actions = [
      "sts:AssumeRole"]

    principals {
      type = "Service"
      identifiers = [
        "ecs.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy_attachment" "ecs-service-role-attachment" {
  role = aws_iam_role.ecs-service-role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceRole"
}

# ECS instance profile

resource "aws_iam_instance_profile" "ecs-instance-profile" {
  name = "ecs-instance-profile"
  path = "/"
  role = aws_iam_role.ecs-instance-role.name
}

//# ELASTIC COMPUTE INSTANCES
//
//# Autoscalling group
//
//resource "aws_autoscaling_group" "devicenator-api-ecs-autoscaling-group" {
//  name = "devicenator-api-ecs-autoscaling-group"
//  max_size = "1"
//  min_size = "1"
//  desired_capacity = "1"
//  vpc_zone_identifier = [aws_subnet.devicenator-vpc-subnet-public.id]
//  launch_configuration = aws_launch_configuration.devicenator-api-ecs-launch-configuration.name
//  health_check_type = "ELB"
//}
//
//# Launch configuration
//
//resource "aws_launch_configuration" "devicenator-api-ecs-launch-configuration" {
//  name_prefix = "devicenator-api-"
//  image_id = "ami-09266271a2521d06f"
//  instance_type = "t2.micro"
//  iam_instance_profile = aws_iam_instance_profile.ecs-instance-profile
//  security_groups = aws_security_group.devicenator-api-security-group
//  associate_public_ip_address = "true"
//  user_data = <<EOF
//              #!/bin/bash
//              echo ECS_CLUSTER=devicenator-api-cluster > /etc/ecs/ecs.config
//              EOF
//
//  lifecycle {
//    create_before_destroy = true
//  }
//}
//
//# Load balancer
//
//resource "aws_alb" "devicenator-api-ecs-load-balancer" {
//  name = "devicenator-api-ecs-load-balancer"
//  security_groups = [aws_security_group.devicenator-api-security-group.id]
//  subnets = [aws_subnet.devicenator-vpc-subnet-public.id]
//}
//
//resource "aws_alb_target_group" "devicenator-api-ecs-target_group" {
//  name = "devicenator-api-ecs-target_group"
//  port = 80
//  protocol = "HTTP"
//  vpc_id = aws_vpc.devicenator-vpc.id
//
//  health_check {
//    healthy_threshold = "5"
//    unhealthy_threshold = "2"
//    interval = "30"
//    matcher = "200"
//    path = "/"
//    port = "traffic-port"
//    protocol = "HTTP"
//    timeout = "5"
//  }
//}
//
//resource "aws_alb_listener" "alb-listener" {
//  load_balancer_arn = aws_alb.devicenator-api-ecs-load-balancer.arn
//  port = 80
//  protocol = "HTTP"
//
//  default_action {
//    target_group_arn = aws_alb_target_group.devicenator-api-ecs-target_group.arn
//    type = "forward"
//  }
//}
//
//# ELASTIC CONTAINER SERVICE
//
//# ECS cluster
//
//resource "aws_ecs_cluster" "devicenator-api-cluster" {
//  name = "devicenator-api-cluster"
//}
//
//# ECS service
//
//resource "aws_ecs_service" "devicenator-api-ecs-service" {
//  name = "devicenator-api-ecs-service"
//  iam_role = aws_iam_role.ecs-service-role.id
//  cluster = aws_ecs_cluster.devicenator-api-cluster.id
//  task_definition = aws_ecs_task_definition.devicenator-api-task-definition.arn
//  desired_count = 1
//
//  load_balancer {
//    target_group_arn = aws_alb.devicenator-api-ecs-load-balancer.arn
//    container_port = 80
//    container_name = "simple-app"
//  }
//}
//
//# ECS task definition
//
//resource "aws_ecs_task_definition" "devicenator-api-task-definition" {
//  family = "devicenator-api-task-definition"
//  container_definitions = "${file("./ecs/task-definition.json")}"
//}