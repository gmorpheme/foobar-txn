FROM library/clojure
MAINTAINER greg@curvelogic.co.uk

COPY . /usr/src/app
WORKDIR /usr/src/app

# Drake workflow uses shell for running commands
ENV SHELL /bin/bash
CMD ["lein", "run"]
