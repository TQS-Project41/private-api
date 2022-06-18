#!/bin/bash

printf "[*] MOVING FILES TO TESTING ENVIRONMENT...\n"

if [ -d "test_env" ]; then
	rm -rf test_env
fi

mkdir test_env
mv -v ./* ./test_env/

cd test_env
./dev.sh -a