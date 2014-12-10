sudo docker build --rm -t ippontech/spark-jhipster .
sudo docker run -ti --name jhipster --link spark:spark ippontech/spark-jhipster
