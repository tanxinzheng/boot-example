#!/usr/bin/env bash
export RUN_AS_USER=admin
export BASH_ENV=/etc/profile
echo $PATH
rm -rf barrel-framework
rm -rf barrel-plateform
git clone https://gitee.com/tanxinzheng/barrel-framework.git
git clone https://gitee.com/tanxinzheng/barrel-platform.git

mvn install -f barrel-framework/pom.xml
mvn install -f barrel-plateform/pom.xml -Pstg

unzip -o barrel-plateform/barrel-auth/target/barrel-auth.zip -d /app/deploy
unzip -o barrel-plateform/barrel-gateway/target/barrel-gateway.zip -d /app/deploy
unzip -o barrel-plateform/barrel-service/barrel-module-authorization/target/barrel-system.zip -d /app/deploy

echo "Restart Barrel-Auth Start...."
sudo systemctl restart barrel-auth
echo "Restart Barrel-Auth End...."

echo "Restart Barrel-System Start...."
sudo systemctl restart barrel-system
echo "Restart Barrel-System End...."

echo "Restart Barrel-Gateway Start...."
sudo systemctl restart barrel-gateway
echo "Restart Barrel-Gateway End...."