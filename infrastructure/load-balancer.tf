//resource "aws_alb" "devicenator-api-ecs-load-balancer" {
//  name = "devicenator-api-ecs-load-balancer"
//  security_groups = [aws_security_group.devicenator-api-security-group.id]
//  subnets = [aws_subnet.devicenator-vpc-subnet-public-1.id, aws_subnet.devicenator-vpc-subnet-public-2.id]
//}
//
resource "aws_alb_target_group" "devicenator-api-target_group" {
  name = "devicenator-api-target_group"
  target_type = "instance"
  protocol = "HTTP"
  port = 80
  vpc_id = aws_vpc.devicenator-vpc.id

  health_check {
    protocol = "HTTP"
    path = "/actuator/health"
    matcher = "200"
    port = "traffic-port"
  }
}
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
