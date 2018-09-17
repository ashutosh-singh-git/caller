#!/bin/sh

echo "Please enter mysql credentials -"
printf "Username: "
read -r user
printf "Password: "
read -r pass

echo
echo "Creating Database 'caller'";

mysqladmin -u$user -p$pass create caller
echo

echo "Compiling/Deploying Application";
echo

mvn spring-boot:run -Dspring-boot.run.arguments=--spring.datasource.username=$user,--spring.datasource.password=$pass
