#!/bin/bash
[[ $# -ne 1 ]] && { echo "Usage: $0 <navn>"; exit 1; }

folder=$1
git clone https://aurora/git/scm/aop/openshift-referanse-springboot-server.git $folder

cd $folder
rm -Rf .git
git init
git add --all
git commit -m "Initial Commit"
git remote set-url origin https://aurora/git/scm/aop/$folder.git
