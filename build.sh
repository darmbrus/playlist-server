#!/usr/bin/env bas#!/bin/sh

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

docker build \
  --no-cache \
  -t playlist-server\
  $DIR/.