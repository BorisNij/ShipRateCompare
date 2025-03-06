FROM --platform=$BUILDPLATFORM muonsoft/openapi-mock:0.3.9

USER root

RUN apk --no-cache add tzdata

ENV TZ=America/Toronto

USER openapi

CMD ["serve"]
