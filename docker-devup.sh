#!/usr/bin/env bash

# Cleanup
docker-compose down
# Start-up
docker-compose pull
echo "Bringing up select infra containers"
docker-compose up -d

echo "
#########################################
############ Dev Infra UP! ##############
#########################################

 Useful Info:

 DB:
   127.0.0.1:5433
   User: autoclear
   Pass: lalala
   Database: autoclear
"

