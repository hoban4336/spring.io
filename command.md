## secret 생성

```
kubectl create secret docker-registry ghcr \
  --docker-server=ghcr.io \
  --docker-username=hoban4336 \
  --docker-password=<token> \
  --docker-email=hoban4336@gmail.com
```
