resource "aws_ecr_repository" "devicenator-api" {
  name = local.repository_name
}

resource "aws_ecr_lifecycle_policy" "ecr-lifecycle-policy" {
  repository = aws_ecr_repository.devicenator-api.name

  policy = <<EOF
  {
      "rules": [
          {
              "rulePriority": 1,
              "description": "Keep last 10 images",
              "selection": {
                  "tagStatus": "any",
                  "countType": "imageCountMoreThan",
                  "countNumber": 10
              },
              "action": {
                  "type": "expire"
              }
          }
      ]
  }
  EOF
}
