#!/bin/sh
MY_TOKEN=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
USERNAME=niolikon

export CR_PAT=$MY_TOKEN
echo $CR_PAT | docker login ghcr.io -u USERNAME --password-stdin

docker build . -t ghcr.io/niolikon/alexandria-inventory:0.0.0-A02
docker push ghcr.io/niolikon/alexandria-inventory:0.0.0-A02
