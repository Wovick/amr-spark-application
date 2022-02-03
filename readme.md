
/spark/bin/spark-submit \
    --class part6practical.TestDeployApp \
    --master spark://(dockerID):7077 \
    --deploy-mode client \
    --verbose \
    --supervise \
    spark-essentials.jar /opt/spark-data/movies.json /opt/spark-data/goodMovies

aws s3 cp s3://lux-big/amr-spark-application.jar .

/usr/bin/spark-submit --class part6practical.TestDeployApp --supervise --verbose amr-spark-application.jar movies.json goodMovies


/usr/bin/spark-submit \
    --class TestAppMain \ 
    --supervise \
    --verbose \
    amr-spark-application.jar s3://lux-big/movies.json s3://lux-big/good_movies


/usr/bin/spark-submit \
--master yarn \
--class TestAppMain \
--deploy-mode cluster \
--verbose \
amr-spark-application.jar s3://lux-big/movies.json s3://lux-big/good_movies



/usr/bin/spark-submit \
--master yarn \
--class TestAppMain \
--deploy-mode client \
--verbose \
amr-spark-application.jar s3://lux-big/movies.json s3://lux-big/good_movies