#!/bin/bash

tput setaf 2

cd ./webapps/CusCrypt 2>/dev/null && echo Chemin OK || echo $(basename $(pwd)) | grep CusCrypt 1>/dev/null || echo Déplacez vous dans tomcat/webapps/CusCrypt

./comp.sh > /dev/null && echo Compilation OK

tput sgr0

../../bin/catalina.sh run 1>catalina.ok.log 2>catalina.err.log &

tput setaf 2
iceweasel "http://localhost:8080/CusCrypt/new.html" 1>/dev/null 2>/dev/null || chromium-browser "http://localhost:8080/CusCrypt/new.html" || echo Veuillez cliquer sur "http://localhost:8080/CusCrypt/new.html"

echo "Appuyer sur une touche pour continuer"
echo

read c

echo "pour vérifier la base de données pendant le déroulement de vos tests vous devez disposer de sqlite3"
echo "avec la commande : sqlite3 data/Database.db"
echo "puis SELECT * FROM users ;"
echo
echo "Appuyer sur une touche pour continuer"
echo
read c
echo
echo "Pour rappel notre projet se trouve être une \"api\" de chiffrement de mots de passes pour sites web principalement"
echo "Elle se compose de 2 classes <CryptIt> : qui gere le chiffrement de la base de mots de passes"
echo "et de <LectureEcriture> qui permet de sauvegarder dans differents fichiers les clefs RSA pour le chiffrement"
echo
echo "Appuyer sur une touche pour continuer"
echo
read c
echo
echo "pour tester notre programme nous vous proposons de créer differents utilisateurs \net d'observer l'évolution de la base de mots de passes avec la commande sqlite3 (disponibles sur les dépots debian) ci-dessus"
echo

tput sgr0
