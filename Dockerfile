FROM library/clojure
MAINTAINER greg@curvelogic.co.uk

COPY . /usr/src/app
WORKDIR /usr/src/app

EXPOSE 9000
CMD ["lein", "run"]
