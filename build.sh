#!/usr/bin/env bash

sbt clean test dist
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

docker build -t car-adverts-api .

