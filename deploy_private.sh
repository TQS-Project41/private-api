#!/bin/bash

printf "[*] MOVING FILES TO DEPLOYMENT ENVIRONMENT...\n"

if [ -d "$DIR" ]; then
	rm -rf deploy_env
fi

mkdir deploy_env
mv -v ./* ./deploy_env/

cd deploy_env
./prod.sh -a