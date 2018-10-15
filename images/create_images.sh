#!/bin/bash          
VERSION="0.0.1-SNAPSHOT"
echo Selected version: $VERSION

cd ..
cd backend

cd dinner/dinner-core
docker build . -t foodful/dinner:$VERSION
cd ..
cd ..

cd auth/auth-core
docker build . -t foodful/auth:$VERSION
cd ..
cd ..

cd gateway
docker build . -t foodful/gateway:$VERSION
cd ..

cd user/user-core
docker build . -t foodful/user:$VERSION
cd ..
cd ..

cd ..
cd images

echo Saving dinner image
docker save foodful/dinner > foodful-dinner-$VERSION.tar

echo Saving auth image
docker save foodful/auth > foodful-auth-$VERSION.tar

echo Saving gateway image
docker save foodful/gateway > foodful-gateway-$VERSION.tar

echo Saving user image
docker save foodful/user > foodful-user-$VERSION.tar

echo Removing build artifacts
docker images -a | grep "foodful" | awk '{print $3}' | xargs docker rmi

echo Successfully created images with version $VERSION