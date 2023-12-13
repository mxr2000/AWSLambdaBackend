set -eo pipefail
ARTIFACT_BUCKET=$(cat bucket-name.txt)
TEMPLATE=template.yaml

aws cloudformation package \
  --template-file $TEMPLATE \
  --s3-bucket "$ARTIFACT_BUCKET" \
  --output-template-file out.yml

aws cloudformation deploy \
  --region us-east-1 \
  --template-file out.yml \
  --stack-name openapi-examples --capabilities CAPABILITY_NAMED_IAM
