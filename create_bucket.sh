BUCKET_ID=$(dd if=/dev/random bs=8 count=1 2>/dev/null | od -An -tx1 | tr -d ' \t\n')
BUCKET_NAME=lambda-artifacts-$BUCKET_ID
echo "$BUCKET_NAME" > bucket-name.txt
aws s3api create-bucket --bucket "$BUCKET_NAME"