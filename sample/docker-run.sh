sudo docker build --rm -t ippontech/spark-jhipster .
sudo docker run -t -i --rm -p 8080:8080 --name jhipster --link spark:spark ippontech/spark-jhipster
