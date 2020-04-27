data "aws_route53_zone" "devicenator" {
  name = "devicenator.com"
}

resource "aws_route53_record" "devicenator-api" {
  zone_id = data.aws_route53_zone.devicenator.zone_id
  name    = "api.devicenator.com"
  type    = "A"

  alias {
    name    = aws_alb.devicenator-api-load-balancer.dns_name
    zone_id = aws_alb.devicenator-api-load-balancer.zone_id
    evaluate_target_health = false
  }
}
