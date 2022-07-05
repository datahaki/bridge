#!/bin/bash

set -e

xvfb-run --auto-servernum mvn install

