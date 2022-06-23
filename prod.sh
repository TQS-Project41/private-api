#!/bin/bash

down_flg=0
build_flg=0
jar_flg=0
help_flg=0
up_flg=0
volume_flg=0
sleeps="10"
time_per_sleep=3

while getopts hjda flag
do
	case ${flag} in
		h) 
			help_flg=1
		;;
		d) 
			down_flg=1
		;;
		b)
			build_flg=1
		;;
		j)
			jar_flg=1
		;;
		u)
			up_flg=1
		;;	
		
		a)
			jar_flg=1
			down_flg=1
			build_flg=1
			up_flg=1
			volume_flg=1
		;;
	esac
done

if [[ "$help_flg" -eq 1 ]]; then
	printf "\nUSAGE: run.sh\n\tOPTIONS:\n\t\tno options : Only ups containers\n\t\t-d : Down containers\n\t\t-b : Build containers\n\t\t-j : Build Spring jar\n\t\t-a : All the options\n\n\n"
	exit 0
fi

printf "[*] STARTING DEPLOYMENT...\n"

if [[ "$jar_flg" -eq 1 ]]; then
	printf "[*] BUILDING SPRING...\n"
	(cd api; mvn clean package -DskipTests)
	ret=$?
	(($? != 0)) && { printf "[-] ERROR BUILDING SPRING \n"; exit 1; }
	printf "[+] DONE.\n"
fi

if [[ "$volume_flg" -eq 1 ]]; then
	printf "[+] CREATING VOLUMES (if they do not exist)...\n"
	docker volume create datafiles_private_prod
fi

printf "[*] DEPLOYING CONTAINERS...\n"

if [[ "$down_flg" -eq 1 ]]; then
	printf "\t[+] DOWNING CONTAINERS...\n"
	docker-compose -f docker-compose.prod.yml --env-file ./deployment/.env down
	printf "[+] DONE.\n"
fi

if [[ "$build_flg" -eq 1 ]]; then
	printf "\t[+] BUILDING CONTAINERS...\n"
	docker-compose -f docker-compose.prod.yml --env-file ./deployment/.env build
	ret=$?
	(($? != 0)) && { printf "[-] ERROR BUILDING CONTAINERS \n"; exit 1; }
	printf "\t[+] DONE.\n"
fi

if [[ "$up_flg" -eq 1 ]]; then
	printf "[+] RUNNING CONTAINERS...\n"
	docker-compose -f docker-compose.prod.yml --env-file ./deployment/.env up -d
fi
