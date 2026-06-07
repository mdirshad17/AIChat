#!/bin/bash
export $(grep -v '^#' ~/.env | xargs)

sudo systemctl restart app