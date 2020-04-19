resource "aws_ecr_repository" "devicenator-api" {
  name = "devicenator-api"
}

resource "aws_iam_user" "travis-devicenator-api" {
  name = "travis-devicenator-api"
}

resource "aws_iam_access_key" "travis-devicenator-api" {
  user = aws_iam_user.travis-devicenator-api.name
}

resource "aws_iam_role" "ecr-role" {
  name = "ecr-role"
  assume_role_policy = data.aws_iam_policy_document.assume-rol-policy-document.json
}

data "aws_iam_policy_document" "assume-rol-policy-document" {
  statement {
    actions = [
      "sts:AssumeRole"]

    principals {
      identifiers = [
        "arn:aws:iam::${var.account_id}:user/travis-devicenator-api"]
      type = "AWS"
    }
  }
}

resource "aws_iam_role_policy_attachment" "ecr-policy-attachment" {
  role       = aws_iam_role.ecr-role.name
  policy_arn = aws_iam_policy.ecr-policy.arn
}

resource "aws_iam_policy" "ecr-policy" {
  name        = "ecr-policy"
  description = "Policy for accessing ECR"
  policy = data.aws_iam_policy_document.ecr-policy-document.json
}

data "aws_iam_policy_document" "ecr-policy-document" {
  statement {
    effect = "Allow"
    actions = [
      "ecr:*"
    ]
    resources = [
      "arn:aws:ecr:${var.region}:${var.account_id}:repository/devicenator-api"
    ]
  }
  statement {
    effect = "Allow"
    actions = [
      "ecr:GetAuthorizationToken"
    ]
    resources = [
      "*"
    ]
  }
}