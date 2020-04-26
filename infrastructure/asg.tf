//resource "aws_autoscaling_group" "devicenator-api-ecs-autoscaling-group" {
//  name = "devicenator-api-ecs-autoscaling-group"
//  max_size = "3"
//  min_size = "1"
//  desired_capacity = "2"
//  vpc_zone_identifier = [aws_subnet.devicenator-vpc-subnet-public-1.id, aws_subnet.devicenator-vpc-subnet-public-2.id]
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
