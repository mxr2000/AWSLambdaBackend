model_path="org/example/model"

if ! openapi-generator generate \
       --global-property models \
       --global-property supportingFiles=build.gradle \
       --global-property modelTests=False \
       --global-property modelDocs=False \
       --generate-alias-as-model \
       --additional-properties modelPackage="org.example.model" \
       -i api.yaml \
       -g java \
       -o Generated > /dev/null; then
  exit 1
fi

echo "Generate Success"

mv Generated/src/main/java/"${model_path}"/* Functions/src/main/java/"${model_path}"

echo "Move to Target Folder"

rm -rf Generated

echo "Clean"