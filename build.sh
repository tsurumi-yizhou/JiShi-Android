# build foss
git checkout foss
gradle assembleRelease
apksigner sign -ks $KEYSTORE --ks-key-alias $KEY_ALIAS --ks-pass pass:$KEYSTORE_PASSWORD --key-pass pass:$KEY_PASSWORD --out foss.apk app/build/outputs/apk/release/app-release-unsigned.apk
rm -rf foss.apk.idsig app/build

# build google
git checkout google
gradle assembleRelease
apksigner sign -ks $KEYSTORE --ks-key-alias $KEY_ALIAS --ks-pass pass:$KEYSTORE_PASSWORD --key-pass pass:$KEY_PASSWORD --out google.apk app/build/outputs/apk/release/app-release-unsigned.apk
rm -rf google.apk.idsig app/build

# build huawei
git checkout huawei
gradle assembleRelease
apksigner sign -ks $KEYSTORE --ks-key-alias $KEY_ALIAS --ks-pass pass:$KEYSTORE_PASSWORD --key-pass pass:$KEY_PASSWORD --out huawei.apk app/build/outputs/apk/release/app-release-unsigned.apk
rm -rf huawei.apk.idsig app/build

# update
echo "{\"timestamp\":"$(date +%s)"000,\"download\": [{\"name\": \"foss\",\"url\": \"https://cdn.yizhou.ac.cn/foss.apk\"},{\"name\": \"huawei\",\"url\": \"https://cdn.yizhou.ac.cn/huawei.apk\"},{\"name\": \"google\",\"url\": \"https://cdn.yizhou.ac.cn/google.apk\"}]}" > metadata.json
coscli cp foss.apk cos://jishi/foss.apk
coscli cp google.apk cos://jishi/google.apk
coscli cp huawei.apk cos://jishi/huawei.apk
coscli cp metadata.json cos://jishi/metadata.json

# clear
git checkout foss
rm coscli.log